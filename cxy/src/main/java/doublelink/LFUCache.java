package doublelink;

import java.util.HashMap;
import java.util.Map;

/**
 * 请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。
 *
 * 实现 LFUCache 类：
 *
 * LFUCache(int capacity) - 用数据结构的容量capacity 初始化对象
 * int get(int key) - 如果键存在于缓存中，则获取键的值，否则返回 -1。
 * void put(int key, int value) - 如果键已存在，则变更其值；如果键不存在，请插入键值对。当缓存达到其容量时，则应该在插入新项之前，
 * 使最不经常使用的项无效。在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最近最久未使用 的键。
 * 注意「项的使用次数」就是自插入该项以来对其调用 get 和 put 函数的次数之和。使用次数会在对应项被移除后置为 0 。
 *
 * 为了确定最不常使用的键，可以为缓存中的每个键维护一个 使用计数器 。使用计数最小的键是最久未使用的键。
 *
 * 当一个键首次插入到缓存中时，它的使用计数器被设置为 1 (由于 put 操作)。对缓存中的键执行 get 或 put 操作，使用计数器的值将会递增。
 *
 *  
 *
 * 示例：
 *
 * 输入：
 * ["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
 * 输出：
 * [null, null, null, 1, null, -1, 3, null, -1, 3, 4]
 *
 * 解释：
 * // cnt(x) = 键 x 的使用计数
 * // cache=[] 将显示最后一次使用的顺序（最左边的元素是最近的）
 * LFUCache lFUCache = new LFUCache(2);
 * lFUCache.put(1, 1);   // cache=[1,_], cnt(1)=1
 * lFUCache.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
 * lFUCache.get(1);      // 返回 1
 *                       // cache=[1,2], cnt(2)=1, cnt(1)=2
 * lFUCache.put(3, 3);   // 去除键 2 ，因为 cnt(2)=1 ，使用计数最小
 *                       // cache=[3,1], cnt(3)=1, cnt(1)=2
 * lFUCache.get(2);      // 返回 -1（未找到）
 * lFUCache.get(3);      // 返回 3
 *                       // cache=[3,1], cnt(3)=2, cnt(1)=2
 * lFUCache.put(4, 4);   // 去除键 1 ，1 和 3 的 cnt 相同，但 1 最久未使用
 *                       // cache=[4,3], cnt(4)=1, cnt(3)=2
 * lFUCache.get(1);      // 返回 -1（未找到）
 * lFUCache.get(3);      // 返回 3
 *                       // cache=[3,4], cnt(4)=1, cnt(3)=3
 * lFUCache.get(4);      // 返回 4
 *                       // cache=[3,4], cnt(4)=2, cnt(3)=3
 *
 * 提示：
 *
 * 0 <= capacity, key, value <= 104
 * 最多调用 105 次 get 和 put 方法
 *
 * 进阶：你可以为这两种操作设计时间复杂度为 O(1) 的实现吗？
 *
 * 链接：https://leetcode-cn.com/problems/lfu-cache
 *
 * 这个题和 {@link AllOne} 几乎是一样的, 有一个小区别：当删除时，如果count一致，删除的是最近最久未使用的，
 * 这意味着所有的操作都尽量往前移动
 *
 */
public class LFUCache {
  private int capacity;
  private Map<Integer, Node> cache;
  private DoubleLinkedList min;

  public LFUCache(int capacity) {
    this.capacity = capacity;
    this.cache = new HashMap<>((int) (capacity / 0.75));
  }

  public void put(int key, int value) {
    if (capacity == 0) {
      return;
    }

    Integer _key = key;
    Node oldValue = cache.get(_key);
    if (oldValue != null) {
      oldValue.value = value;
      oldValue.count++;
      promotion(oldValue);
    } else {
      if (cache.size() >= capacity) {
        evict();
      }
      Node node = new Node(_key, value);
      addNewNode(node);
      cache.put(_key, node);
    }

  }

  public int get(int key) {
    Node value;
    if (capacity == 0 || (value = cache.get(key)) == null) {
      return -1;
    }

    value.count++;
    promotion(value);
    return value.value;
  }

  private void evict() {
    DoubleLinkedList list = min;
    while (list.head == null) {
      list = list.next;
    }
    this.min = list;
    Node node = list.tail;
    cache.remove(node.key);
    list.remove(node);
  }

  private void addNewNode(Node node) {
    DoubleLinkedList min = this.min;
    if (min != null && min.head != null && min.head.count == 1) {
      min.addHead(node);
    } else {
      DoubleLinkedList one = new DoubleLinkedList();
      one.head = one.tail = node;
      one.prev = null;
      if (min != null && min.head == null) {
        one.next = min.next;
      } else {
        one.next = min;
      }
      node.list = one;
      this.min = one;
    }
  }

  private void promotion(Node node) {
    DoubleLinkedList list = node.list;
    list.remove(node);

    DoubleLinkedList target = null;
    if (list.next == null || (list.next.head != null && list.next.head.count != node.count)) {
      target = new DoubleLinkedList();
    } else {
      target = list.next;
    }
    target.addHead(node);
    list.link(target);

    if (list.head == null) {
      if (list == min) {
        this.min = list.prev;
        if (min == null) {
          min = list.next;
        }
      }
      list.unlink();
    }
  }


  static class Node {
    Integer key;
    int value;
    int count;

    Node prev;
    Node next;

    /**
     * node 所属的 list
     */
    DoubleLinkedList list;

    public Node(Integer key, int value) {
      this.key = key;
      this.value = value;
      this.count = 1;
    }

    @Override
    public String toString() {
      return key + ":" + count;
    }
  }

  static class DoubleLinkedList {
    Node head;
    Node tail;

    /**
     * 数值比它小1个的链表
     */
    DoubleLinkedList prev;

    /**
     * 数值比它大一的链表
     */
    DoubleLinkedList next;

    void addHead(Node node) {
      node.list = this;
      Node head = this.head;
      if (head == null) {
        this.head = this.tail = node;
        return;
      }

      head.prev = node;
      node.next = head;
      this.head = node;
    }

    void remove(Node node) {
      Node next = node.next;
      Node prev = node.prev;

      if (prev != null) {
        prev.next = next;
      }

      if (next != null) {
        next.prev = prev;
      }

      if (this.head == node) {
        this.head = next;
      }
      if (this.tail == node) {
        this.tail = prev;
      }


      node.prev = node.next = null;
      node.list = null;
    }

    void link(DoubleLinkedList list) {
      DoubleLinkedList next = this.next;

      list.prev = this;
      this.next = list;
      list.next = next;
      if (next != null) {
        next.prev = list;
      }
    }

    void unlink() {
      if (this.prev != null) {
        this.prev.next = this.next;
      }
      if (this.next != null) {
        this.next.prev = this.prev;
      }

      this.next = this.prev = null;
    }
  }

  /**
   * ["LFUCache","put","put","get","put","get","get","put","get","get","get"]
   * [[2],[1,1],[2,2],[1],[3,3],[2],[3],[4,4],[1],[3],[4]]
   * @param args
   */
  public static void main(String[] args) {
    LFUCache cache = new LFUCache(2);
    cache.put(1, 1);
    cache.put(2, 2);
    System.out.println(cache.get(1));
    cache.put(3, 3);
    System.out.println(cache.get(2));
    System.out.println(cache.get(3));
    cache.put(4, 4);
    System.out.println(cache.get(1));
    System.out.println(cache.get(3));
    System.out.println(cache.get(4));
  }
}
