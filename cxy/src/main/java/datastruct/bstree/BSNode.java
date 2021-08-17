package datastruct.bstree;

/**
 * 二叉搜索树的节点
 * @param <E>
 */
class BSNode<E extends Comparable<E>> {
    E val;
    BSNode<E> parent;
    BSNode<E> left;
    BSNode<E> right;

    public BSNode(E val, BSNode<E> parent) {
        this.parent = parent;
        this.val = val;
    }

    boolean isLeaf() {
        return left == null && right == null;
    }

    boolean isFull() {
        return left != null && right != null;
    }

    void replaceChild(BSNode<E> old, BSNode<E> newChild) {
        if (old == left) {
            this.left = newChild;
        } else {
            this.right = newChild;
        }
    }

}
