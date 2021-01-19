package list;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import static util.Preconditions.checkArgument;
import static util.Preconditions.checkState;

/**
 * 实现一个基于数组的list
 * @param <E>
 */
public class CArrayList<E> implements List<E> {
    // 默认大小
    public static final int DEFAULT_SIZE = 16;

    /**
     * 在使用数组的list中，如果每次remove都是compact，对于频繁删除的场景不太友好，
     * 因此在前期都不删除，只有当空间不够用的时候才compact
     */
    private static final Object DELETE_FLAG = new Object();

    // 持有数据的地方
    private Object[] data;
    private int size;
    // 下一个可存储的位置
    private int next;
    // 已经删除的元素个数
    private int deletedNum;

    public CArrayList() {
        this(DEFAULT_SIZE);
    }

    public CArrayList(int capacity) {
        checkArgument(capacity > 0);
        data = new Object[capacity];
        next = 0;
    }

    public CArrayList(Collection<E> other) {
        // Java Collection Framework中 toArray会新建一个array，是一个不共享的array
        data = other.toArray(new Object[other.size()]);
        next = data.length;
        size = data.length;
    }

    /**
     * 使用initValues初始化一个list
     * @param initValues
     */
    public CArrayList(E... initValues) {
        data = new Object[initValues.length];
        for (next = 0; next < initValues.length; next++) {
            data[next] = initValues[next];
        }
        size = initValues.length;
    }

    /**
     * @return 当前list持有的元素个数
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size > 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < next; i++) {
            Object v = data[i];
            if (Objects.equals(v, o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        ensureSize(1);
        data[next++] = e;
        return true;
    }

    private void ensureSize(int required) {
        if (remaining() > required) {
            return;
        } else if (remaining() + deletedNum > required) {
            compact();
            return;
        }

        int newSize = Integer.max(data.length + required, data.length * 2);
        checkState(newSize < Integer.MAX_VALUE - 10);

        Object[] newData = new Object[newSize];
        if (deletedNum == 0) {
            System.arraycopy(data, 0, newData, 0, next);
        } else {
            compact(data, newData, next);
            next = size;
            deletedNum = 0;
        }
    }

    private int remaining() {
        return data.length - next;
    }

    /**
     * 删除掉已经被删掉的位置
     */
    private void compact() {
        compact(data, data, next);
        next = size;
        deletedNum = 0;
    }

    /**
     * 将src中有效的值拷贝到dest中
     * @param src
     * @param dest
     */
    private void compact(Object[] src, Object[] dest, int maxIndex) {
        int index = 0;
        for (int i = 0; i < maxIndex; i++) {
            Object v = src[i];
            if (v != DELETE_FLAG) {
                dest[index++] = v;
            }
        }
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < next; i++) {
            if (Objects.equals(data[i], o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object v : c) {
            if (! contains(v)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        size = 0;
        next = 0;
        deletedNum = 0;
    }

    @Override
    public E get(int index) {
        if (deletedNum > 0) {
            compact();
        }
        checkIndex(index);
        return (E) data[index];
    }

    private void checkIndex(int index) {
        if (index > next) {
            throw new ArrayIndexOutOfBoundsException("max index is " + next + ", but required index " + index);
        }
    }

    /**
     * 把index处的元素替换为element
     * @param index
     * @param element
     * @return index处之前的元素
     */
    @Override
    public E set(int index, E element) {
        checkIndex(index);

        if (deletedNum > 0) {
            compact();
        }
        Object prev = data[index];
        data[index] = element;
        return (E) prev;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        Object prev = data[index];
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}
