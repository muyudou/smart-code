package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 整个设计有两点值得注意:
 * 1. 环形队列通过增加一个count来判断是否为null
 *
 * 2. 同一把锁生成两个condition，一个是emptyCondition，一个是full condition
 *     当empty时,emptyCondition await, 当full时,full condition await
 *     当put完成时，emptyCondition signal, 当get完成时，fullCondition signal
 *
 * 实际上这种实现，只依赖了一把锁，因此锁争用是很严重的, 有没有可能把read lock和write lock分开
 * 这样可以把争用减少一半? 难点在于两把锁之间如何发信号。
 *
 * @param <E>
 */
public class ArrayBlockingQueue<E> {
    private Object[] data;
    private int count;
    private int readIndex;
    private int writeIndex;

    private ReentrantLock lock = new ReentrantLock();
    private Condition readCondition = lock.newCondition();
    private Condition writeCondition = lock.newCondition();

    public ArrayBlockingQueue(int capacity) {
        this.data = new Object[capacity];
    }

    public void put(E e) {
        lock.lock();
        try {
            //在并发编程中，大部分的if判断要编程while
            while (count == data.length) {
                writeCondition.await();
            }
            data[writeIndex] = e;
            writeIndex = next(writeIndex);
            count++;
            readCondition.signal();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            lock.unlock();
        }
    }

    public E get() {
        lock.lock();
        try {
            while (count == 0) {
                readCondition.await();
            }
            Object e = data[readIndex];
            // 这个地方要记得赋空值，要不然会内存泄露
            data[readIndex] = null;
            readIndex = next(readIndex);
            count--;
            writeCondition.signal();
            return (E) e;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    // 循环队列的下一个位置
    private int next(int index) {
        return index < data.length - 1 ? ++index : 0;
    }

    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(34);
        AtomicInteger base = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                int x = base.getAndIncrement() * 10000;
                while (true) {
                    queue.put("a-" + x++);
                    sleepMS(500);
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    System.out.println(queue.get());
                }
            }).start();
        }
    }

    public static void sleepMS(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException("休眠失败");
        }
    }

}
