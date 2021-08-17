package doublelink;

import java.util.HashMap;

/**
 * 请你实现一个数据结构支持以下操作：
 *
 * Inc(key) - 插入一个新的值为 1 的 key。或者使一个存在的 key 增加一，保证 key 不为空字符串。
 * Dec(key) - 如果这个 key 的值是 1，那么把他从数据结构中移除掉。否则使一个存在的 key 值减一。如果这个 key 不存在，这个函数不做任何事情。key 保证不为空字符串。
 * GetMaxKey() - 返回 key 中值最大的任意一个。如果没有元素存在，返回一个空字符串"" 。
 * GetMinKey() - 返回 key 中值最小的任意一个。如果没有元素存在，返回一个空字符串""。
 *
 * 挑战：
 * 你能够以 O(1) 的时间复杂度实现所有操作吗？
 *
 * 链接：https://leetcode-cn.com/problems/all-oone-data-structure
 *
 * ============================================================
 * 分析:
 * 1. inc 和 dec 的难点在于找到这key，如果是O(1)的话，可以利用 hash map
 * 2. get max 和 get min 可以维护一个有序链表，其中最大值和最小值分别在队头和队尾
 * 两者结合就是: hash map + double linked list --> Java中有一个现成的实现LinkedHashMap，不过我们用不上
 */
public class AllOne {
    private HashMap<String, Node> map;

    /**
     * 链表部分的操作，每次更新完成后和前后的节点进行比较：
     * 1. 如果比前面的节点大，就往前移动, 此时可能有连续若干个相同的前驱节点
     * 2. 如果比后面的节点小就往后移动, 此时可能有连续若干个相同的后继节点
     * 整个链表始终是有序的
     * todo: 这样的时间复杂度还是O(1)吗
     *
     */
    // 保存最大值
    private Node head;
    // 保存最小值
    private Node tail;

    /** Initialize your data structure here. */
    public AllOne() {
        map = new HashMap<>();
    }

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        Node value = map.get(key);
        if (value == null) {
            value = new Node(key, 1);
            map.put(key, value);
            addNode(value);
        } else {
            value.count++;
            moveForward(value);
        }
    }

    private void addNode(Node node) {
        if (tail != null) {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;

        if (head == null) {
            head = node;
        }
    }

    private void moveForward(Node node) {
        while (node.prev != null && node.prev.count < node.count) {
            Node prev = node.prev;
            Node next = node.next;

            prev.next = next;
            if (next != null) {
                next.prev = prev;
            }
            node.next = prev;
            node.prev = prev.prev;
            prev.prev = node;
            if (node.prev != null) {
                node.prev.next = node;
            }

            if (tail == node) {
                tail = prev;
            }
        }

        if (node.prev == null) {
            head = node;
        }
    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        Node value = map.get(key);
        if (value == null) {
            return;
        }
        int count = --value.count;
        if (count == 0) {
            map.remove(key);
            delete(value);
        } else {
            moveBack(value);
        }

    }

    private void delete(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        if (tail == node) {
            tail = prev;
        }
        if (head == node) {
            head = next;
        }

        if (prev != null) {
            prev.next = next;
        }
        if (next != null) {
            next.prev = prev;
        }
    }

    private void moveBack(Node node) {
        while (node.next != null && node.next.count > node.count) {
            Node next = node.next;
            Node prev = node.prev;

            if (prev != null) {
                prev.next = next;
            }
            next.prev = prev;
            node.prev = next;
            node.next = next.next;
            next.next = node;
            if (node.next != null) {
                node.next.prev = node;
            }

            if (head == node) {
                head = node.prev;
            }
        }

        if (node.next == null) {
            tail = node;
        }
    }

    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        return head == null ? "" : head.key;
    }

    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        return tail == null ? "" : tail.key;
    }

    public static void display(Node node) {
        System.out.print("[");
        while (node != null) {
            System.out.print(node + ", ");
            node = node.next;
        }
        System.out.println("]");
    }

    static class Node {
        String key;
        int count;
        Node next;
        Node prev;

        public Node(String key, int count) {
            this.key = key;
            this.count = count;
        }

        @Override
        public String toString() {
            return key + ": " + count;
        }
    }

    /**
     */
    public static void main(String[] args) {
        AllOne allOne = new AllOne();
        allOne.inc("hello");
        allOne.inc("goodbye");
        allOne.inc("hello");
        // hello
        System.out.println(allOne.getMaxKey());
        display(allOne.head);


        allOne.inc("leet");
        allOne.inc("code");
        allOne.inc("leet");
        allOne.dec("hello");
        allOne.inc("leet");
        allOne.inc("code");
        allOne.inc("code");
        // leet
        System.out.println(allOne.getMaxKey());
        display(allOne.head);

        allOne.dec("hello");
        display(allOne.head);
    }
}
