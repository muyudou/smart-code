package datastruct.bstree;

import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 * 二叉搜索树(凭记忆)：
 *  1. 根节点的值 大于等于 左子树的值
 *  2. 根节点的值 小于等于 左子树的值
 *
 *  Notice: 不允许存在重复的元素; 不允许null;
 * @param <E>
 */
public class BinarySearchTree<E extends Comparable<E>> {
    private int size;
    private BSNode<E> root;

    private Consumer<BSNode> deleteRoot = parent -> deleteTree();
    private Consumer<BSNode> deleteLeft = parent -> parent.left = null;
    private Consumer<BSNode> deleteRight = parent -> parent.right = null;

    /**
     * 判断是否包含值
     * @param e
     * @return
     */
    public boolean contains(E e) {
        BSNode<E> node = root;
        while (node != null) {
            int c = node.val.compareTo(e);
            if (c == 0) {
                return true;
            } else if (c > 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        return false;
    }

    /**
     *
     * @param e
     */
    public void insert(E e) {
        Objects.requireNonNull(e, "BinarySearchTree doest not allowed null value.");
        if (root == null) {
            root = new BSNode<>(e);
            size++;
            return;
        }

        BSNode<E> node = root;
        BSNode<E> parent = null;
        int c = 0;
        while (node != null) {
            c = node.val.compareTo(e);
            if (c == 0) {
                node.val = e;
                return;
            } else if (c > 0) {
                parent = node;
                node = node.left;
            } else {
                parent = node;
                node = node.right;
            }
        }

        node = new BSNode<>(e);
        if (c < 0) {
            parent.right = node;
        } else {
            parent.left = node;
        }
        size++;
    }


    /**
     * 删除e
     * @param e
     * @return removed element if exist, else null
     */
    public E remove(E e) {
        return remove(root, null, deleteRoot, e);
    }

    private E remove(BSNode<E> node, BSNode<E> parent, Consumer<BSNode> deleter, E e) {
        if (node == null) {
            return null;
        }

        int c = node.val.compareTo(e);
        if (c == 0) {
            E old = node.val;
            // 使用左子树的最大节点或者右子树的最小节点来代替
            // 使用一种简单的方式来随机的决定是删除左子树还是右子树
            boolean right = (node.left != null && node.right != null && (System.currentTimeMillis() & 1) == 0);
            if (node.isLeaf()) {
                deleter.accept(parent);
            } else if (node.left == null || right) {
                node.val = removeMin(node.right, node, deleteRight);
            } else {
                node.val = removeMax(node.left, node, deleteLeft);
            }
            size--;
            return old;
        } else if (c > 0) {
            return remove(node.left, node, deleteLeft, e);
        } else {
            return remove(node.right, node, deleteRight, e);
        }
    }

    private void deleteTree() {
        root = null;
    }

    private E removeMax(BSNode<E> node, BSNode<E> parent, Consumer<BSNode> deleter) {
        if (node.isLeaf()) {
            // 将 node 从 parent 中删除
            deleter.accept(parent);
            return node.val;
        }

        /** node 就是最大的值, 使用左子树中的最大值来作为node的代替 */
        if (node.right == null) {
            E old = node.val;
            E max = removeMax(node.left, node, deleteLeft);
            node.val = max;
            return old;
        }

        return removeMax(node.right, node, deleteRight);
    }

    private E removeMin(BSNode<E> node, BSNode<E> parent, Consumer<BSNode> deleter) {
        if (node.isLeaf()) {
            // 将 node 从parent 中删除
            deleter.accept(parent);
            return node.val;
        }

        /** node 就是最小值， 使用右子树的最小值来代替 */
        if (node.left == null) {
            E old = node.val;
            E min = removeMin(node.right, node, deleteRight);
            node.val = min;
            return old;
        }

        return removeMin(node.left, node, deleteLeft);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("[");
        traverse(root, sb);
        if (size > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("]");
        return sb.toString();
    }

    private void traverse(BSNode<E> node, StringBuilder sb) {
        if (node == null) {
            return;
        }

        traverse(node.left, sb);
        sb.append(node.val).append(", ");
        traverse(node.right, sb);
    }


    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        int[] values = {8, 8, 3, 6, 1, 0, 6, 6, 3, 7, 9, 2, 7, 4, 5, 9};
        for (int v : values) {
            tree.insert(v);
            System.out.println(tree);
        }
        System.out.println(tree.contains(10));
        System.out.println(tree.size);

        for (int v : values) {
            tree.remove(v);
            System.out.println("Delete " + v  + ": " + tree);
        }
    }

}
