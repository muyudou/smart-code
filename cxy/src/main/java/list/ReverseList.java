package list;

/**
 * 发现最近来面试的候选者连单链表翻转都不会，今天一怒之下花了2分钟写了一个，结果发现了一个小坑: head节点的next要置空，要不然有环
 *
 * @date 2018-06-15
 */
public class ReverseList {
    public static <T> Node<T> reverse(Node<T> head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 处理过程中的中心节点
        Node<T> node = head;
        Node<T> prev = null;

        while (node != null) {
            Node<T> next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }
        return prev;
    }

    public static void main(String[] args) {
        Node<String> n5 = new Node<>("E", null);
        Node<String> n4 = new Node<>("D", n5);
        Node<String> n3 = new Node<>("C", n4);
        Node<String> n2 = new Node<>("B", n3);
        Node<String> n1 = new Node<>("A", n2);
        System.out.println(n1);
        System.out.println(reverse(n1));
    }
}
