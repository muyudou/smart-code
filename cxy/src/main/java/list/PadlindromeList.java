package list;

/**
 * 请判断一个链表是否为回文链表。
 *
 * 示例 1:
 *
 * 输入: 1->2
 * 输出: false
 * 示例 2:
 *
 * 输入: 1->2->2->1
 * 输出: true
 * 进阶：
 * 你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
 *
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindrome-linked-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class PadlindromeList {

    /**
     * 通过快慢指针来判断： 快指针每次走两步，慢指针每次走一步，当快指针达到末尾的时候慢指针达到中间
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        // 空链表或者只有一个元素算回文
        if (head == null || head.next == null) {
            return true;
        }

        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        /**
         * 执行完之后, slow指针是关键位置：
         *  - 如果链表是 a b a   这种形式，则slow指向b
         *  - 如果链表是 a b c d 这种形式，则slow指向b
         * Notice: 无论哪种情况，slow都是后半部分链表的第一个位置
         */

        ListNode first = head;
        ListNode second = traverse(slow);
        while (second != null) {
            if (first.val != second.val) {
                return false;
            }
            first = first.next;
            second = second.next;
        }

        return true;
    }

    /**
     * 反转单链表
     * @param head
     * @return
     */
    public ListNode traverse(ListNode head) {
        ListNode node = head;
        ListNode prev = null;
        while (node != null) {
            ListNode next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }
        return prev;
    }


    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
}
