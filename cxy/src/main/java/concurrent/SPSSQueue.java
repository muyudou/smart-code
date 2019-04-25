package concurrent;

import util.BitsUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 单线程生产，单线程消费的队列。
 * 这个类的出现时因为在做调研时，需要读取大量的本地数据往远程服务发送，为了测试性能，不希望本地读取占用太多时间
 * 因此使用一个后台线程读取本地数据put到队列里，发送线程从队列中retrive：
 *  1. 当队列满的时候，后台线程停止put
 *  2. 当队列空的时候，发送线程hold，直到有新内容或者数据读取完毕
 *
 *  在当前的java.util.concurrent包里已经有线程的工具，这里自己实现了一个单线程生产、单线程消费的阻塞队列
 *  仅仅是用来练手，未仔细思考性能，不过正确性是没问题的
 *
 */
public class SPSSQueue implements Iterator<String>, AutoCloseable {

    private final List<String> buf;
    // 缓冲区的大小，必须是2^n
    private final int bufSize;
    // bufSize - 1, 用来加速%bufSize
    private final int mask;

    /**
     *  readIndex在有发送线程会改变它的值；
     *  writeIndex和isEnd只有后台线程会改变它的值；
     *  因此可以使用基本类型
     *
     *  但是每个线程都会读取这3个值，因此需要设置为volatile
     */
    // 下一个可读取位置, 发送线程用来获取
    private volatile int readIndex = 0;

    // 下一个可写入位置, 写入线程用来写入
    private volatile int writeIndex = 0;

    // 是否读取完毕, 阻塞队列理论上是不会停止的，这里希望它能自动结束
    private volatile boolean isEnd = false;

    private final String localFile;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    public SPSSQueue(int bufSize, String localFile) throws FileNotFoundException {
        this.bufSize = BitsUtil.upper2N(bufSize);
        this.mask = this.bufSize - 1;
        this.buf = new ArrayList<>(this.bufSize);
        this.localFile = localFile;
        executor.submit(this::readUntilEndInBack);
    }

    /**
     * 后台线程读取本地数据，直至读完
     */
    private void readUntilEndInBack() {
        try (BufferedReader reader = new BufferedReader(new FileReader(localFile))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // 如果当前缓冲区是满的，则自旋
                while ((writeIndex - readIndex) >= this.bufSize);
                // 至少空出一个位置可以放新数据
                buf.set(writeIndex & mask, line);
                // 只有这一个线程会改变，因此是安全的
                writeIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 只有这一个线程会改变，因此是安全的
            isEnd = true;
        }
    }

    @Override
    public void close() {
        executor.shutdown();
    }

    @Override
    public boolean hasNext() {
        /**
         * readIndex >= writeIndex，意味着队列是空的
         * 如果已经结束了，则writeIndex不会再变了
         */
        while(readIndex >= writeIndex && !isEnd);
        // 走到这里isEnd == true
        return readIndex < writeIndex;
    }

    @Override
    public String next() {
        String value = buf.get(readIndex & mask);
        // 只有这个线程会改变它的值
        readIndex++;
        return value;
    }
}
