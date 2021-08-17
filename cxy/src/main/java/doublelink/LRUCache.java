package doublelink;

import java.util.HashMap;
import java.util.Map;

/**
 * 运用你所掌握的数据结构，设计和实现一个 LRU (最近最少使用) 缓存机制 。
 * 实现 LRUCache 类：
 *
 * LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
 * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 * void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
 *
 * 进阶：你是否可以在O(1) 时间复杂度内完成这两种操作？
 *
 * 链接：https://leetcode-cn.com/problems/lru-cache
 *
 * 解题思路: linked hash map
 */
public class LRUCache {
  private Map<Integer, Node> cache;
  private int capacity;
  private LinkedList list;

  public LRUCache(int capacity) {
    this.capacity = capacity;
    cache = new HashMap<>((int) (capacity / 0.75));
    list = new LinkedList();
  }

  public int get(int key) {
    if (capacity == 0) {
      return -1;
    }

    Integer _key = key;
    Node value = cache.get(_key);
    if (value == null) {
      return -1;
    }

    list.moveToHead(value);
    return value.value;
  }

  public void put(int key, int value) {
    if (capacity == 0) {
      return;
    }

    Integer _key = key;
    Node oldValue = cache.get(_key);
    if (oldValue != null) {
      oldValue.value = value;
      list.moveToHead(oldValue);
    } else {
      if (cache.size() >= capacity) {
        evict();
      }
      Node node = new Node(_key, value);
      cache.put(_key, node);
      list.addHead(node);
    }
  }

  private void evict() {
    Node node = list.removeTail();
    cache.remove(node.key);
  }

  static class Node {
    Integer key;
    int value;

    Node prev;
    Node next;

    public Node() { }

    public Node(Integer key, int value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public String toString() {
      return key + ":" + value;
    }
  }

  /**
   * 有哨兵节点的双向链表
   */
  static class LinkedList {
    private Node head = new Node();
    private Node tail = new Node();

    {
      head.next = tail;
      tail.prev = head;
    }

    private void moveToHead(Node node) {
      if (head.next == node) {
        return;
      }

      remove(node);
      addHead(node);
    }

    private void addHead(Node node) {
      Node next = head.next;
      node.next = next;
      if (next != null) {
        next.prev = node;
      }
      head.next = node;
      node.prev = head;
    }

    private void remove(Node node) {
      Node prev = node.prev;
      Node next = node.next;
      prev.next = next;
      next.prev = prev;
      node.next = node.prev = null;
    }

    private Node removeTail() {
      if (head.next == tail) {
        return null;
      }

      Node node = tail.prev;
      Node prev = node.prev;
      prev.next = tail;
      tail.prev = prev;
      node.next = node.prev = null;
      return node;
    }
  }
}
