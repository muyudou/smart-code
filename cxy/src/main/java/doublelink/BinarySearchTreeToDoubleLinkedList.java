package doublelink;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 将二叉搜索树转成有序的双向循环链表
 * 要求：不能创建新的节点，原地转换
 */
public class BinarySearchTreeToDoubleLinkedList {
    /**
     * 1. 使用非递归的方式来实现，需要依赖栈来作为中间存储
     * 一路向左遍历，直到遇到left == null，如果此时 head == null，说明这是最左边的节点，使用head保存,
     * 否则话说明前面已经有节点了，进行链表操作
     *
     * @param root
     * @return
     */
    public static Node treeToDoublyList(Node root) {
        Node head = null;
        Node node = root;
        Node prev = null;
        Deque<Node> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null && node.left != null) {
                stack.push(node);
                node = node.left;
            }
            if (node == null) {
                node = stack.pop();
            }

            if (head == null) {
                head = node;
            } else {
                prev.right = node;
                node.left = prev;
            }
            prev = node;
            node = node.right;
        }
        head.left = prev;
        prev.right = head;
        return head;
    }

    public static void traverse(Node root) {
        if (root.left != null) {
            traverse(root.left);
        }
        System.out.println(root.val);
        if (root.right != null) {
            traverse(root.right);
        }
    }

    public static void traverse2(Node root) {
        Deque<Node> stack = new LinkedList<>();
        Node node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null && node.left != null) {
                stack.push(node);
                node = node.left;
            }
            if (node == null) {
                node = stack.pop();
            }
            System.out.println(node.val);
            node = node.right;
        }
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);

        node4.left = node2; node4.right = node5;
        node2.left = node1; node2.right = node3;
        traverse2(node4);

        treeToDoublyList(node4);
    }
}
