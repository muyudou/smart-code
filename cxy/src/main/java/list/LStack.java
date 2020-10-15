package list;

import java.io.Serializable;

/**
 * stack在不断的push和pop中会不断的丢弃对象，新建对象
 * 通过使用一个gc collector，把丢弃的对象放到里面，当push时从里面去一个新的
 * 通过使用gc collector这样真的高效吗?
 */
public class LStack<E> implements Stack<E>, Serializable {
    /** 两个哨兵 */
    private Node<E> head;
    private Node<E> gc;
    private int size;

    public LStack() {
        head = new Node<>(null, null);
        gc = new Node<>(null, null);
    }

    public boolean isEmpty() {
        return head.next == null;
    }

    @Override
    public int size() {
        return size;
    }

    public void push(E e) {
        Node<E> node = getGCNodeOrCreate(e);
        node.next = head.next;
        head.next = node;
        size++;
    }

    public E pop() {
        if (isEmpty()) {
            throw new NullPointerException("stack is empty");
        }
        Node<E> node = head.next;
        head.next = node.next;

        gc(node);
        size--;

        return node.value;
    }

    public E peek() {
        if (isEmpty()) {
            throw new NullPointerException("stack is empty");
        }
        return head.next.value;
    }

    private Node<E> getGCNodeOrCreate(E e) {
        if (gc.next == null) {
            return new Node<>(e, null);
        } else {
            Node<E> node = gc.next;
            gc.next = node.next;
            node.value = e;
            return node;
        }
    }

    private void gc(Node<E> node) {
        node.next = gc.next;
        gc.next = node;
    }
}
