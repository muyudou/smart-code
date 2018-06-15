package list;

/**
 * 单链表的node
 */
public class Node<T> {
    T value;
    Node<T> next;

    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }

    // 如果node之间有环，则会陷入死循环, just for test
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(value.toString());
        Node<T> c = next;
        while (c != null) {
            sb.append("--->" + c.value.toString());
            c = c.next;
        }
        return sb.toString();
    }
}
