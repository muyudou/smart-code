package datastruct.btree;

import datastruct.array.Array;
import datastruct.array.Index;
import datastruct.array.SortedUniqArray;
import util.Tuple;

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

  public K minKey() {
    return keys.first();
  }

  public K maxKey() {
    return keys.last();
  }

  public K get(int index) {
    return keys.get(index);
  }

  public Page<K> minChild() {
    return children == null ? null : children.first();
  }

  public Page<K> maxChild() {
    return children == null ? null : children.last();
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

  /********************** 修改部分 *******************/
  /**
   * 只更新 key 的值
   */
  public K update(int index, K key) {
    K old = keys.get(index);
    keys.set(index, key);
    return old;
  }

  /** 针对叶子节点的增删操作 */
  public K addAsLeaf(K key) {
    keys.insert(key);
    return key;
  }

  public K removeAsLeaf(K key) {
    return keys.remove(key);
  }

  /**
   * 只有分裂 root 时才会被调用
   * @param child
   */
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

  public Tuple<K, Page<K>> remove(int index) {
    K key = keys.removeByIndex(index);
    Page<K> child = children == null ? null : children.removeByIndex(index+1);
    return Tuple.of(key, child);
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

  public void merge(K middle, Page<K> right) {
    keys.insert(middle);
    right.keys.forEach(keys::insert);
    if (right.children != null) {
      right.children.forEach(this.children::insert);
    }
  }

  /**
   * 1. 如果子的左兄弟节点或者右兄弟节点至少有一个有 t 个元素，则从中借一个，以左兄弟为例：
   *  1. 将左兄弟节点的最大值上提值parent
   *  2. 将parent节点对应的关键字下降值 this
   *  3. 将左兄弟节点的最大child 作为 this 最小的child
   * 2. 如果都只有 t-1 个元素，则合并
   *
   * @param parent
   * @param index 自己作为子节点在parent.children中的下标
   * @return
   */
  public Page<K> borrowOrMergeFromSibling(Page<K> parent, int index) {
    // 1. 尝试从左兄弟借一个元素
    Page<K> left = null;
    if (index > 0 && (left = parent.children.get(index-1)).size() >= t) {
      K first = parent.get(index-1);
      K promot = left.keys.removeLast();
      parent.update(index-1, promot);
      this.keys.insertAt(0, first);
      // 同一层级，chilren如果不为空则都不为空
      if (this.children != null) {
        this.children.insertAt(0, left.children.removeLast());
      }
      return this;
    }

    // 2. 尝试从右兄弟借一个元素
    Page<K> right = null;
    if (index < parent.children.size() - 1 && (right = parent.children.get(index+1)).size() >= t) {
      K last = parent.get(index);
      K promot = right.keys.removeFirst();
      parent.update(index, promot);
      this.keys.insert(last);
      if (this.children != null) {
        this.children.insert(right.children.removeFirst());
      }
      return this;
    }

    // 3. 尝试与左兄弟合并, left, parent中下放一个, this
    if (left != null) {
      K middle = parent.keys.removeByIndex(index-1);
      // this 从 middle 的右节点删除
      parent.children.removeByIndex(index);
      left.merge(middle, this);
      return left;
    }

    if (right != null) {
      K middle = parent.keys.removeByIndex(index);
      parent.children.removeByIndex(index);
      this.merge(middle, right);
      // 替换一下
      parent.children.set(index, this);
      return this;
    }
    throw new RuntimeException("Something error.");
  }

  @Override
  public String toString() {
    return keys.toString();
  }

}
