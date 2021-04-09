package datastruct.btree;

import datastruct.array.Array;
import datastruct.array.FindIndex;
import datastruct.array.SortedUniqArray;
import util.Preconditions;

import java.io.Serializable;

/**
 *
 * @param <E>
 */
public class Page<E extends Comparable<E>> implements Serializable {
    // 算法导论中定义的节点的最小度
    private final int t;

    SortedUniqArray<E> keys;
    Array<Page<E>> children;

    public Page(int t) {
        this.t = t;
        int m = 2 * t;
        this.keys = new SortedUniqArray<>(m - 1);
    }

    public int size() {
        return keys.size();
    }

    public boolean isFull() {
        return keys.isFull();
    }

    public boolean isLeaf() {
        return children == null;
    }

    public void setChild(int index, Page<E> child) {
        if (children == null) {
            children = new Array<>(2*t);
        }
        children.set(index, child);
    }

    public Page<E> getChild(int index) {
        return children == null ? null : children.get(index);
    }

    /**
     * 先插入了中间元素，然后把right插入到child中
     * n先更新后才执行的这个函数
     * @param index
     * @param child
     */
    public void insertChild(int index, Page<E> child) {
        if (children == null) {
            children = new Array<>(2*t);
        }
        children.insertAt(index, child);
    }

    /**
     * @param e
     * @return if e in page, return its index; else return the child tree index which its should be in
     */
    public FindIndex find(E e) {
        return keys.binarySearch(e);
    }

    public E get(int index) {
        return keys.get(index);
    }

    public E max() {
        return keys.last();
    }

    public E min() {
        return keys.first();
    }

    public Page<E> minPage() {
        return children.first();
    }

    public Page<E> maxPage() {
        return children.last();
    }

    public E update(int index, E e) {
        return keys.set(index, e);
    }

    /**
     * 将e插入到page中
     * @param e
     */
    public void insertToLeaf(E e) {
        Preconditions.checkState(isLeaf());
        keys.insert(e);
    }

    public void insertToInternal(int index, E e, Page<E> child) {
        keys.insertAt(index, e);
        insertChild(index+1, child);
    }

    void truncate(int from) {
        keys.truncate(from);
        if (children != null) {
            children.truncate(from+1);
        }
    }

    /**
     * 将一个满的节点分裂成三部分：t-1, median, t-1
     */
    public void split(Page<E> parent, int indexAtParent) {
        Preconditions.checkState(this.isFull());
        int m = keys.size();
        E median = keys.get(m);

        // 1. 分裂出一个右节点
        Page<E> right = new Page<>(t);
        this.keys.moveTo(m+1, keys.size(), right.keys);
        if (this.children != null) {
            right.children = new Array<>(2*t);
            this.children.moveTo(m+1, m+1 + t, right.children);
        }

        // 2. 本节点作为左节点，同时清空右半部分
        Page<E> left = this;
        left.truncate(m);

        // 3. 把中值放入到父节点中，把right作为父节点的子节点
        parent.insertToInternal(indexAtParent, median, right);
    }

    /**
     * split的反过程
     * @param key
     * @param right
     */
    public void merge(E key, Page<E> right) {
        keys.insert(key);
        right.keys.moveTo(0, right.size(), keys);
        right.children.moveTo(0, right.size() + 1, children);
    }

    /**
     * 删除某个元素及其右子树
     * @param index
     * @return
     */
    public E remove(int index) {
        if (children != null) {
            children.removeByIndex(index + 1);
        }
        return keys.removeByIndex(index);
    }

    @Override
    public String toString() {
        return keys.toString();
    }

}
