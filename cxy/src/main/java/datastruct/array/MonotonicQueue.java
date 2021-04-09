package datastruct.array;

import java.util.Comparator;

/**
 * 单调队列: 给定的区间内，队列的对头存储的是区间内的极值(最大值或最小值)
 * 实现：
 *  1. 维护一个固定大小的底层存储结构
 *  2. 队尾入列：添加元素发生在队尾，并且在添加时删除前面影响单调性的元素
 *  3. 队头删除：当队列满的时候删除队头元素
 *  4. 取队头元素：获取极值
 */
public class MonotonicQueue<E> {
    private int capacity;
    private Object[] data;
    private int size = 0;
    private Comparator<E> comparator;

    public MonotonicQueue(int capacity, Comparator<E> comparator) {
        this.capacity = capacity;
        this.data = new Object[capacity];
        this.comparator = comparator;
    }

    public void offer(E e) {
        while (size > 0 && comparator.compare(e, (E) data[size-1]) > 0) {
            size--;
        }
        if (size == capacity) {
            take();
        }
        data[size++] = e;
    }

    /**
     * 获取并删除队头元素
     * @return
     */
    public E take() {
        E old = (E) data[0];
        System.arraycopy(data, 1, data, 0, size - 1);
        size--;
        return old;
    }

    public E peek() {
        return (E) data[0];
    }

    public static void main(String[] args) {
        MonotonicQueue<Integer> queue = new MonotonicQueue<>(3, Comparator.<Integer>naturalOrder());
        int[] values = {3, 1, 5, 7, 4, 2, 1, 0, 4, 8, 9, 10, 11, 12, 11, 10, 9, 8};
        for (int v : values) {
            queue.offer(v);
            System.out.println("max: " + queue.peek());
        }
    }
}
