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

  public static void main(String[] args) {
    BTree<Character> btree = new BTree<>(3);
    String s = "caoxiyngrshjqflmkxptuvw";
    for (int i = 0; i < s.length(); i++) {
      btree.insert(s.charAt(i));
    }

    boolean isExist = true;
    for (int i = 0; i < s.length(); i++) {
      isExist &= btree.get(s.charAt(i));
    }
    System.out.println(isExist);
    System.out.println(btree.get('b'));
    System.out.println(btree.get('d'));
    System.out.println(btree.get('e'));

  }
}
