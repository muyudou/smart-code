package list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * 单链表
 */
public class SLinkedList<E> implements List<E> {
    private int size = 0;
    private Node<E> head;
    private Node<E> tail;

    public SLinkedList() {}

    public SLinkedList(Iterable<E> c) {
        for (E v : c) {
            Node<E> node = new Node<>(v, null);
            if (tail == null) {
                head = tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
            size++;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size > 0;
    }

    public boolean isNotEmpty() {
        return ! isEmpty();
    }


    @Override
    public boolean contains(Object o) {
        Node<E> node = head;
        while (node != null) {
            if (Objects.equals(node.value, o)) {
                return true;
            }
            node = node.next;
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
        Node<E> node = new Node<>(e, null);
        tail.next = node;
        tail = node;
        size++;
        return true;
    }

    /**
     * 删除第一个等于o的元素
     * @param o
     * @return
     */
    @Override
    public boolean remove(Object o) {
        Node<E> node = head;
        Node<E> prev = null;
        while (node != null) {
            if (! Objects.equals(node.value, o)) {
                prev = node;
                node = node.next;
            }
        }

        // 找到了第一个o
        if (node != null) {
            if (prev != null) {
                prev.next = node.next;
            } else {
                // 删掉的是第一个元素, 没有哨兵节点
                head = node.next;
            }

            // 删掉的是最后一个元素
            if (node.next == null) {
                tail = prev;
            }

            size--;
            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (! contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for (E v : c) {
            changed = add(v);
        }
        return changed;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index > size) {
            throw new IndexOutOfBoundsException("max index is " + size + ", but required " + index);
        }
        if (c.isEmpty()) {
            return false;
        }

        // 在结尾处逐个插入
        if (index == size) {
            for (E v : c) {
                add(v);
            }
            return true;
        }

        // head 有可能是null
        Node<E> node = head;
        int i = 0;
        while (i++ < index && node != null) {
            node = node.next;
        }

        Node<E> h = null;
        Node<E> t = null;
        for (E v : c) {
            Node<E> n = new Node<>(v, null);
            if (h == null) {
                h = n;
                t = n;
            } else {
                t.next = n;
                t = n;
            }
        }

        /**
         * node == null的几种情况:
         * 1. index == size，相当于在tail后面追加
         * 2. this原先是空的，index == 0
         */
        if (node == null) {
            if (head == null) {
                head = h;
                tail = t;
            } else if (index == size) {
                tail.next = h;
                tail = t;
            }
        } else {
            t.next = node.next;
            node.next = h;
        }

        size += c.size();
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object v : c) {
            changed = remove(v);
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        int i = 0;
        Node<E> node = head;
        while (i++ < index) {
            node = node.next;
        }
        return node.value;
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        int i = 0;
        Node<E> node = head;
        while (i++ < index) {
            node = node.next;
        }
        E tmp = node.value;
        node.value = element;
        return tmp;
    }

    private void checkIndex(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("max index is " + size + ", but required " + index);
        }
    }

    @Override
    public void add(int index, E element) {
        if (index > size) {
            throw new IndexOutOfBoundsException("max index is " + size + ", but required " + index);
        }
        if (size == 0) {
            head = tail = new Node<>(element, null);
            return;
        }

        if (index == size) {
            tail.next = new Node<>(element, null);
            tail = tail.next;
        }

        int i = 0;
        Node<E> prev = null;
        Node<E> node = head;
        while (i++ < index) {
            prev = node;
            node = node.next;
        }

        Node<E> n = new Node<>(element, node);
        if (prev == null) {
            head = n;
        } else {
            prev.next = n;
        }
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        int i = 0;
        Node<E> node = head;
        Node<E> prev = null;

        while (i++ < index) {
            prev = node;
            node = node.next;
        }

        // 删除的是head
        if (prev == null) {
            head = head.next;
        } else {
            prev.next = node.next;
        }

        size--;
        return node.value;
    }

    @Override
    public int indexOf(Object o) {
        Node<E> node = head;
        int index = -1;
        while (node != null) {
            index++;
            if (Objects.equals(node.value, o)) {
                return index;
            }
            node = node.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (size == 0 || Objects.equals(tail.value, o)) {
            return size - 1;
        }

        Node<E> node = head;
        int index = -1;
        int i = -1;
        while (node != null) {
            i++;
            if (Objects.equals(node.value, o)) {
                index = i;
            }
            node = node.next;
        }
        return index;
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
