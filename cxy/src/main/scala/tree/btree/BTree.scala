package tree.btree

class BTree[K <: Comparable[K], V](val t: Int) {
  private var root: Page[K, V] = null
  private var count: Int = 0

  /**
   *
   * @param key
   * @param value
   * @return old value if exist
   */
  def put(key: K, value: V): Option[V] = {
    if (root == null) {
      root = new Page[K, V](t)
    }
    if (root.isFull()) {
      root = root.split(null, 0)
    }
    putNotFull(root, key, value)
  }


  /**
   * 插入只发生在叶子节点上
   * @param node node is not full
   * @param key
   * @param value
   * @return
   */
  private def putNotFull(node: Page[K, V], key: K, value: V): Option[V] = {
    val (isExist, index) = node.keys.find(key)
    if (isExist) {
      return node.update(key, value, index)
    }
    if (node.isLeaf()) {
      node.put(key, value)
      count += 1
      return None
    }

    var target = node.children.get(index)
    if (target.isFull()) {
      // 可能 key 在 target 中，并且恰巧是中间值，如果直接分裂则 key 会被提升到 parent 中
      val c = target.keys.get(t-1).compareTo(key)
      if (c == 0) {
        return target.update(key, value, t-1)
      }

      target.split(node, index)
      // 分裂后的 left(就是 target), right, key 应该插入在 right 中
      if (c < 0) {
        target = node.children.get(index+1)
      }
    }

    putNotFull(target, key, value)
  }


  def get(key: K): Option[V] = {
    getInternal(root, key)
  }

  private def getInternal(node: Page[K, V], key: K): Option[V] = {
    if (node == null) {
      return None
    }

    val (isExist, index) = node.find(key)
    if (isExist) {
      return Some(node.values.get(index))
    }

    if (node.isLeaf()) {
      return None
    }

    getInternal(node.children.get(index), key)
  }

  def remove(key: K): Option[V] = {
    if (root == null) {
      None
    } else {
      val old = removeEnough(root, key)
      old.foreach(_ => count -= 1)
      old
    }
  }

  def removeEnough(node: Page[K, V], key: K): Option[V] = {
    val (isExist, index) = node.find(key)
    if (node.isLeaf()) {
      return if (isExist) node.removeByIndex(index) else None
    }

    if (isExist) {
      removeInInternalNode(node, key, index)
    } else {
      removeNotInInternalNode(node, key, index)
    }
  }

  def removeInInternalNode(node: Page[K, V], key: K, index: Int): Option[V] = {
    val left = node.children.get(index)
    // 1. 左子节点删除最大的
    if (left.size() >= t) {
      val (maxKey, maxValue) = findMax(left)
      removeEnough(left, maxKey)
      return node.update(maxKey, maxValue, index)
    }

    // 2. 右子节点删除最小的
    val right = node.children.get(index+1)
    if (right.size() >= t) {
      val (minKey, minValue) = findMin(right)
      removeEnough(right, minKey)
      return node.update(minKey, minValue, index)
    }
    // 把 left, key, right 合并到 left 上，在 left 上递归的删除
    node.mergeIndexToLeft(index)
    // node == root && root 空了，树的高度降低1
    if (root.size() == 0) {
      root = left
    }
    removeEnough(left, key)
  }

  def removeNotInInternalNode(node: Page[K, V], key: K, index: Int): Option[V] = {
    val target = node.children.get(index)
    if (target.size() >= t) {
      removeEnough(target, key)
    } else {
      // 需要从它的兄弟节点中借一个关键字
      val realTarget = target.borrowOrMergeFromSibling(node, index);
      if (root.size() == 0) {
        root = realTarget
      }
      removeEnough(realTarget, key)
    }
  }

  /**
   * @param node node.size() >= t
   * @return
   */
  def findMax(node: Page[K, V]): (K, V) = {
    if (node.isLeaf()) {
      return (node.keys.last(), node.values.last())
    }
    findMax(node.children.last())
  }

  def findMin(node: Page[K, V]): (K, V) = {
    if (node.isLeaf()) {
      return (node.keys.first(), node.values.first())
    }
    findMin(node.children.first())
  }

  def size: Int = count


  override def toString: String = super.toString
}

object BTree {
  def main(args: Array[String]): Unit = {
    val btree = new BTree[String, String](3)

    btree.put("0", "0")
    btree.put("1", "1")
    btree.put("2", "2")
    btree.put("3", "3")
    btree.put("4", "4")
    btree.put("5", "5")
    btree.put("6", "6")
    btree.put("7", "7")
    btree.put("8", "8")
    btree.put("9", "9")

    println(btree.put("1", "ONE"))
    println(btree.put("8", "EIGHT"))
    println(btree.put("3", "THREE"))
    println(btree.put("2", "TWO"))


    Array("3", "6", "9", "1", "5", "2", "7", "4", "8").foreach(key => {
      println(s"After remove: ${key}")
      btree.remove(key)
      println((0 until 10).map(_.toString).map(k => btree.get(k)).mkString(","))
    })

    println(btree.size)
    btree.remove("0")
    println(btree.size)
  }
}
