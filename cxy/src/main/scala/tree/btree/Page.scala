package tree.btree

import collection.SortedUniqArray
import datastruct.array

class Page[K <: Comparable[K], V](val t: Int) {
  val m = t * 2
  val keys = new SortedUniqArray[K](m - 1)
  val values = new array.Array[V](m - 1)
  val children = new array.Array[Page[K, V]](m)

  /** read only operation */
  def isFull(): Boolean = keys.size() == m - 1

  def isLeaf(): Boolean = children.isEmpty

  def isNonLeaf(): Boolean = !isLeaf()

  def middleKey(): K = keys.get(keys.size() / 2)

  def find(key: K): (Boolean, Int) = keys.find(key)

  def size(): Int = keys.size()

  /** 更改 key, value */
  def put(key: K, value: V): Option[V] = {
    val (isExist, index) = keys.find(key)
    if (isExist) {
      update(key, value, index)
    } else {
      insertAt(key, value, index)
      None
    }
  }

  def insertAt(key: K, value: V, index: Int): Unit = {
    keys.insertAt(index, key)
    values.insertAt(index, value)
  }

  def update(key: K, value: V, index: Int): Option[V] = {
    val old = values.get(index)
    keys.set(index, key)
    values.set(index, value)
    Some(old)
  }

  def removeByIndex(index: Int): Option[V] = {
    val old = values.get(index)
    keys.removeByIndex(index)
    values.removeByIndex(index)
    Some(old)
  }



  def split(parent: Page[K, V], indexAtParent: Int): Page[K, V] = {
    require(isFull(), "Page is not full, but split: " + keys.size())
    val right = new Page[K, V](t)
    this.keys.moveTo(t, m-1, right.keys)
    this.values.moveTo(t, m-1, right.values)
    if (this.children.isNotEmpty) {
      this.children.moveTo(t + 1, m, right.children)
    }
    val middleKey = keys.removeLast()
    val middleValue = values.removeLast()

    if (parent == null) {
      val root = new Page[K, V](t)
      root.insertAt(middleKey, middleValue, indexAtParent)
      root.children.insert(this)
      root.children.insert(right)
      root
    } else {
      parent.insertAt(middleKey, middleValue, indexAtParent)
      parent.children.insertAt(indexAtParent+1, right)
      parent
    }
  }

  /**
   * 将 left, key, right 合并到 left 上
   * @param index
   * @return
   */
  def mergeIndexToLeft(index: Int): Page[K, V] = {
    val left = children.get(index)
    val right = children.removeByIndex(index+1)
    require(left.size() == t-1 && right.size() == t-1, s"Left[${left.size()}] or Right[${right.size()} is enough")
    val middleKey = keys.removeByIndex(index)
    val middleValue = values.removeByIndex(index)
    left.merge(middleKey, middleValue, right)
    left
  }

  def merge(middleKey: K, middleValue: V, right: Page[K, V]): Unit = {
    insertAt(middleKey, middleValue, size())
    val rightSize = right.size()
    right.keys.moveTo(0, rightSize, this.keys)
    right.values.moveTo(0, rightSize, this.values)
    if (right.children.isNotEmpty) {
      right.children.moveTo(0, rightSize+1, this.children)
    }
  }


  /**
   * 如果它的左兄弟有 t 个关键字，则从左兄弟借一个。
   * 如果它的右兄弟有 t 个关键字，则从右兄弟借一个。
   * 否则和它的一个兄弟节点合并
   * @param parent
   * @param indexAtParent
   */
  def borrowOrMergeFromSibling(parent: Page[K, V], indexAtParent: Int): Page[K, V] = {
    var left: Page[K, V] = null
    // 1. 尝试从左兄弟借一个
    if (indexAtParent > 0) {
      left = parent.children.get(indexAtParent-1)
      if (left.size() >= t) {
        // 父节点的关键下移
        keys.insertAt(0, parent.keys.get(indexAtParent-1))
        values.insertAt(0, parent.values.get(indexAtParent-1))
        // 左兄弟的最大关键字上提
        parent.keys.set(indexAtParent-1, left.keys.removeLast())
        parent.values.set(indexAtParent-1, left.values.removeLast())
        // 左兄弟的最大 child 作为自己的第一个 child
        if (children.isNotEmpty) {
          children.insertAt(0, left.children.removeLast())
        }
        return this
      }
    }

    var right: Page[K, V] = null
    // 2. 尝试从右兄弟借一个
    if (indexAtParent < parent.children.size() - 1) {
      right = parent.children.get(indexAtParent+1)
      if (right.size() >= t) {
        // 父节点的关键字下移
        keys.insertAt(keys.size(), parent.keys.get(indexAtParent))
        values.insertAt(values.size(), parent.values.get(indexAtParent))
        // 右兄弟的最小关键字上提
        parent.keys.set(indexAtParent, right.keys.removeFirst())
        parent.values.set(indexAtParent, right.values.removeFirst())
        // 右兄弟最小的 child 作为自己的第一个 child
        if (children.isNotEmpty) {
          children.insertAt(children.size(), right.children.removeFirst())
        }
        return this
      }
    }

    // 将自己和父节点的一个关键字合并到左子树上
    if (left != null) {
      return parent.mergeIndexToLeft(indexAtParent-1)
    }

    if (right != null) {
      return parent.mergeIndexToLeft(indexAtParent)
    }
    throw new RuntimeException("Something error.")
  }
}
