package datastruct.btree;

import datastruct.array.Index;

/**
 * 算法导论从树的最小度来定义, t 就是树的最小度：
 * 1. root node 至少有一个 关键字
 * 2. 非 root node 至少有 t 个关键字， t+1 个子节点
 * 3. 非 root node 至多有 2t-1 个关键字，2t 个子节点
 *
 * 实现:
 * 1. 在插入的过程中，一旦遇到一个满的节点先分裂，这样能保证在真正执行插入的时候它的父节点不满
 * 2. 当 root node 满的时候，新建一个 root，new root 有一个关键字，两个子节点
 * 3. 中间节点会存储数据
 */
public class BTree<K extends Comparable<K>> {
  Page<K> root;
  // b-tree 的最小度
  int t;

  public BTree(int t) {
    this.t = t;
    root = new Page<>(t);
  }

  /**
   * 查询部分，利用的 b-tree 的性质，比较简单
   * Notice: 这里为了简单只是简单的返回元素是否存在
   */
  public boolean get(K key) {
    return get(root, key);
  }

  private boolean get(Page<K> node, K key) {
    if (node == null) {
      return false;
    }

    Index index = node.search(key);
    if (index.isExist()) {
      return true;
    }

    return get(node.getChild(index.index()), key);
  }

  /************************* 插入部分 ************************/
  public K insert(K key) {
    if (root.isFull()) {
      Page<K> newRoot = new Page<>(t);
      newRoot.addChild(root);
      root.split(newRoot, 0);
      this.root = newRoot;
    }
    return insertNotFull(root, key);
  }

  private K insertNotFull(Page<K> node, K key) {
    Index index = node.search(key);
    if (index.isExist()) {
      return node.update(index.index(), key);
    }

    if (node.isLeaf()) {
      return node.addAsLeaf(key);
    }

    // 第一个大于key的index
    int idx = index.index();
    Page<K> child = node.getChild(idx);
    if (child.isFull()) {
      child.split(node, idx);
      // 如果 key 恰巧是 child 的中值，现在被提升到 node 中了
      K m = node.get(idx);
      int c = key.compareTo(m);
      if (c == 0) {
        node.update(idx, key);
        return m;
      }
      // 现在的 child 只是原来的left部分，key 应该插入在 right 部分
      if (c > 0) {
        child = node.getChild(idx + 1);
      }
    }

    return insertNotFull(child, key);
  }

  /**
   * 删除可能发生在任意节点，在删除的过程中我们保证：每个待删除的节点都至少有t个元素，这样即使删除一个后仍然满足b-tree的性质
   * @param key
   * @return
   */
  public K remove(K key) {
    return removeEnough(root, key);
  }

  private K removeEnough(Page<K> node, K key) {
    Index index = node.search(key);
    if (index.isExist()) {
      if (node.isLeaf()) {
        return node.removeAsLeaf(key);
      } else {
        return removeInInternalNode(node, key, index.index());
      }
    } else {
      if (node.isLeaf()) {
        return null;
      } else {
        return removeNotInInternalNode(node, key, index.index());
      }
    }
  }

  /**
   * 如果 key 存在于内部节点上，分三种情况处理:
   * 1. 如果 key 的前一个子节点 y 中至少有 t 个关键字，则删除 key 在这 y 中的前继 k'，使用 k' 来代替 k
   * 2. 对称的，如果 key 的后一个字节点 z 中至少有 t 个关键字，则删除 key 在z 中的后继 k', 使用 k' 来代替 k
   * 3. 否则，y 和 z 都只有 t-1 个关键字，把 y, key, z 合并到 y 上，然后递归的在 y 上删除 key
   * @param node
   * @param key
   * @param index  key 在 node 中的下标
   * @return
   */
  private K removeInInternalNode(Page<K> node, K key, int index) {
    Page<K> left = node.getChild(index);
    // 找到key的前驱，删除它，使用它来替换key
    if (left.size() >= t) {
      K max = findMax(left);
      node.update(index, max);
      removeEnough(left, max);
      return key;
    }

    // 找到key的后继，删除它，使用它来替换key
    Page<K> right = node.getChild(index+1);
    if (right.size() >= t) {
      K min = findMin(right);
      node.update(index, min);
      removeEnough(right, min);
      return key;
    }

    // left 和 right 都只有 t-1 个元素，合并 left, key, right 到 left 中，并从 left 中删除 key
    left.merge(key, right);
    node.remove(index);
    if (root.size() == 0) {
      root = left;
    }
    return removeEnough(left, key);
  }

  private K findMax(Page<K> node) {
    if (node.isLeaf()) {
      return node.maxKey();
    } else {
      return findMax(node.maxChild());
    }
  }

  private K findMin(Page<K> node) {
    if (node.isLeaf()) {
      return node.minKey();
    } else {
      return findMin(node.minChild());
    }
  }

  /**
   * key 不存在于 node 中，key 可能存在 c = node.children[index+1] 中，但是
   * 我们要确保 c 中至少有 t 个元素，如果它没有可以
   * 按照如下两种形式来处理:
   * a. 如果它的相邻的兄弟节点有一个存在 t 个元素，则将 node 中的某个元素降至 c，将 c 相邻的的兄弟提升一个元素到 node 中
   * b. 如果它的相邻兄弟都只有 t-1 个元素，则将 c, x中的某个元素，c 的某个兄弟节点合并到c节点上
   * @param node
   * @param key
   * @param index 继续下降的子节点下标
   * @return
   */
  private K removeNotInInternalNode(Page<K> node, K key, int index) {
    Page<K> child = node.getChild(index);
    if (child.size() >= t) {
      return removeEnough(child, key);
    }

    Page<K> enoughNode = child.borrowOrMergeFromSibling(node, index);
    if (root.size() == 0) {
      this.root = enoughNode;
    }
    return removeEnough(enoughNode, key);
  }

  public void display() {
    System.out.print("[");
    display(root);
    System.out.println("]");
  }

  private void display(Page<K> node) {
    if (node == null) {
      return;
    }

    if (node.isLeaf()) {
      for (int i = 0; i < node.size(); i++) {
        System.out.print(node.get(i) + ", ");
      }
      return;
    }

    for (int i = 0; i < node.size(); i++) {
      display(node.getChild(i));
      System.out.print(node.get(i) + ", ");
    }
    display(node.maxChild());
  }

  public static void main(String[] args) {
    BTree<Character> btree = new BTree<>(2);
    String s = "caoxiyngrshjqflmkx";
    for (int i = 0; i < s.length(); i++) {
      btree.insert(s.charAt(i));
    }
    btree.display();

    for (char c = 'A'; c < 'z'; c++) {
      Character deleted = btree.remove(c);
      if (deleted != null) {
        System.out.print("Delete " + deleted + ": ");
        btree.display();
      }
    }

  }
}
