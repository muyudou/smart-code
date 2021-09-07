package datastruct.array;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 不可扩展的数组
 * @param <E>
 */
public class Array<E> implements Iterable<E> {
    int size;
    int capacity;
    Object[] data;

    public Array(int capacity) {
        this.capacity = capacity;
        data = new Object[capacity];
    }

    void checkState(boolean state, String msg) {
        if (!state) {
            throw new ArrayIndexOutOfBoundsException(msg);
        }
    }

    public E first() {
        checkState(size > 0, "datastruct.array is empty, can not get first element.");
        return (E) data[0];
    }

    public E last() {
        checkState(size > 0, "datastruct.array is empty, can not get last element.");
        return (E) data[size - 1];
    }

    public E get(int index) {
        checkState(index >= 0 && index < size, "index overflow, index: " + index + ", size: " + size);
        return (E) data[index];
    }

    public E set(int index, E e) {
        checkState(index >= 0 && index < size, "index overflow, index: " + index + ", size: " + size);
        E old = (E) data[index];
        data[index] = e;
        return old;
    }

    public int remainning() {
        return capacity - size;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isFull() {
        return size == capacity;
    }

    /**
     * 将e插入数组中
     * @param e
     */
    public E insert(E e) {
        checkState(size < capacity, "datastruct.array is full, cannot insert any more element.");
        data[size++] = e;
        return e;
    }

    /**
     * 将元素e插入到index位置，原来index及之后的元素后移一位
     * @param index
     * @param e
     */
    public void insertAt(int index, E e) {
        checkState(index >= 0 && index <= size && size < capacity,
                "datastruct.array is full or index is overflow. index: " + index + ", size: " + size);
        if (index != size) {
            System.arraycopy(data, index, data, index + 1, size - index);
        }
        data[index] = e;
        size++;
    }

    public E pop() {
        E old = (E) data[size - 1];
        data[size--] = null;
        return old;
    }

    /**
     * 将index位置的元素删除，同时index及之后的元素往前移动一位
     * @param index
     * @return
     */
    public E removeByIndex(int index) {
        checkState(index >= 0 && index < size,
                "datastruct.array is full or index is overflow. index: " + index + ", size: " + size);
        E old = (E) data[index];
        System.arraycopy(data, index+1, data, index, size - index - 1);
        data[--size] = null;
        return old;
    }

    public E removeLast() {
        return removeByIndex(size - 1);
    }

    public E removeFirst() {
        return removeByIndex(0);
    }

    public E remove(E e) {
        if (e == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) {
                    return removeByIndex(i);
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (e.equals(data[i])) {
                    return removeByIndex(i);
                }
            }
        }
        return null;
    }

    /**
     * 将from及之后的元素清理掉
     * @param from
     */
    public void truncate(int from) {
        checkState(from >= 0 && from < size, "datastruct.array is full or index is overflow. from: " + from + ", size: " + size);
        Arrays.fill(data, from, size, null);
        this.size = from;
    }

    public void moveTo(int fromInclude, int toExclude, Array<E> dst) {
        int count = toExclude - fromInclude;
        checkState(dst.remainning() >= count, "dst remainning: " + dst.remainning() + ", but requied: " + count);
        System.arraycopy(data, fromInclude, dst.data, dst.size, count);
        dst.size += count;

        System.arraycopy(data, toExclude, data, fromInclude, size - toExclude);
        Arrays.fill(data, size - count, size, null);
        size -= count;
    }

    /**
     * 不支持fast-failed
     * @return
     */
    public Iterator<E> iterator() {
        int size = this.size;
        return new Iterator<E>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                return (E) data[index++];
            }
        };
    }

    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (! it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }
}
