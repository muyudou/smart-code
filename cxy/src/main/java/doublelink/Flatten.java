package doublelink;

/**
 * 多级双向链表中，除了指向下一个节点和前一个节点指针之外，它还有一个子链表指针，可能指向单独的双向链表。这些子列表也可能会有一个或多个自己的子项，依此类推，生成多级数据结构，如下面的示例所示。
 *
 * 给你位于列表第一级的头节点，请你扁平化列表，使所有结点出现在单级双链表中。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：head = [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 * 输出：[1,2,3,7,8,11,12,9,10,4,5,6]
 * 解释：
 *
 * 输入的多级列表如下图所示：
 *
 * 链接：https://leetcode-cn.com/problems/flatten-a-multilevel-doubly-linked-list
 */
public class Flatten {

  public Node flatten(Node head) {
    tail(head);
    return head;
  }

  public Node tail(Node node) {
    Node prev = null;
    Node next;
    Node child;
    while (node != null) {
      next = node.next;
      if ((child = node.child) != null) {
        node.child = null;
        node.next = child;
        child.prev = node;

        Node tail = tail(child);
        tail.next = next;
        if (next != null) {
          next.prev = tail;
        }

        prev = tail;
      } else {
        prev = node;
      }

      node = next;
    }

    return prev;
  }

  static class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;

    public Node(int val) {
      this.val = val;
    }

    public Node(int val, Node prev, Node next, Node child) {
      this.val = val;
      this.prev = prev;
      this.next = next;
      this.child = child;
    }
  }

  public static void main(String[] args) {
    Node node1 = new Node(1);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    Node node4 = new Node(4);
    Node node5 = new Node(5);
    Node node6 = new Node(6);
    Node node7 = new Node(7);
    Node node8 = new Node(8);

    node1.next = node2;
    node2.prev = node1;
    node2.next = node7;
    node7.prev = node2;
    node7.next = node8;
    node8.prev = node7;

    node3.next = node4;
    node4.prev = node3;

    node5.next = node6;
    node6.prev = node5;

    node7.child = node3;
    node4.child = node5;

    Node head = new Flatten().flatten(node1);
    System.out.println(head);
  }
}
