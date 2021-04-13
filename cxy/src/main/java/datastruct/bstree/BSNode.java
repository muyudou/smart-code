package datastruct.bstree;

/**
 * 二叉搜索树的节点
 * @param <E>
 */
class BSNode<E extends Comparable<E>> {
    E val;
    BSNode<E> left;
    BSNode<E> right;

    public BSNode(E val) {
        this.val = val;
    }

    boolean isLeaf() {
        return left == null && right == null;
    }

}
