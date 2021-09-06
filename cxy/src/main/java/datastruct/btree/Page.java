package datastruct.btree;

import datastruct.array.Array;
import datastruct.array.Index;
import datastruct.array.SortedUniqArray;

public class Page<K extends Comparable<K>>  {
  private int t;
  private int m;

  private SortedUniqArray<K> keys;
  private Array<Page<K>> children;

  public Page(int t) {
    this.t = t;
    this.m = 2 * t;
    keys = new SortedUniqArray<>(m - 1);
  }

  public K get(int index) {
    return keys.get(index);
  }

  public K update(int index, K key) {
    K old = keys.get(index);
    keys.set(index, key);
    return old;
  }

  public K addAsLeaf(K key) {
    keys.insert(key);
    return key;
  }

  public Page<K> getChild(int index) {
    return children == null ? null : children.get(index);
  }

  public boolean isFull() {
    return keys.isFull();
  }

  public boolean isLeaf() {
    return children == null;
  }

  public int size() {
    return keys.size();
  }

  /********************** 插入部分 *******************/

  public void addChild(Page<K> child) {
    if (this.children == null) {
      this.children = new Array<>(m);
    }
    children.insert(child);
  }

  /**
   * 新增一个key的情况，则需要新增一个child, 且插入在key之后
   * @param key
   * @param index
   * @param child
   */
  private void insertAt(K key, int index, Page<K> child) {
    keys.insertAt(index, key);
    children.insertAt(index+1, child);
  }


  /**
   * 查找指定的 key
   * @param key
   * @return if key exist, return the index of key, 否则返回 key 应该在的子节点的下标
   */
  public Index search(K key) {
    return keys.binarySearch(key);
  }

  /**
   * 1. 中间值 m 提升到父节点中
   * 2. 前面 t-1 个关键字和 t 个子节点作为 left
   * 3. 后面 t-1 个关键字和 t 个子节点作为 right
   * 4. left 仍然是父节点的子节点，且位置不动
   * 5. right 作为父节点的新子节点，插入在 indexAtParent + 1 的位置
   * @param parent
   * @param indexAtParent
   */
  public void split(Page<K> parent, int indexAtParent) {
    int t = this.t;
    int m = this.m;

    // 新生成一个节点
    Page<K> right = new Page<>(t);
    keys.moveTo(t, m-1, right.keys);
    if (this.children != null) {
      right.children = new Array<>(m);
      children.moveTo(t, m, right.children);
    }

    // 中间值 m 上提到父节点中
    K middle = keys.get(t-1);

    // 本节点作为left
    Page<K> left = this;
    left.keys.truncate(t-1);

    parent.insertAt(middle, indexAtParent, right);
  }

  @Override
  public String toString() {
    return keys.toString();
  }

}
