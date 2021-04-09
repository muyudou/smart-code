package datastruct.btree;

import datastruct.array.FindIndex;
import util.Preconditions;

public class BTree<E extends Comparable<E>> {
    private Page<E> root;
    /**
     * 树的度
     */
    private int t;

    public BTree(int t) {
        this.t = t;
        root = new Page<>(t);
    }

    public boolean get(E key) {
        return get(root, key);
    }

    private boolean get(Page<E> node, E key) {
        FindIndex index = node.find(key);
        if (index.isFind) {
            return true;
        }

        Page<E> child = node.getChild(index.idx);
        return child == null ? false : get(child, key);
    }

    /**
     * 在插入的过程中每遇到一个节点已经满了，则先分裂，这样当字节分裂的时候可以保证它的父节点一定是not full的
     * @param e
     * @return
     */
    public BTree<E> insert(E e) {
        if (root.isFull()) {
            Page<E> newRoot = new Page<>(t);
            newRoot.setChild(0, root);
            split(newRoot, root, 0);
            root = newRoot;
        }
        return insertNotNull(root, e);
    }

    public void split(Page<E> parent, Page<E> node, int indexAtParent) {
        node.split(parent, indexAtParent);
    }

    /**
     * 插入只发生在叶子节点上, 在向下遍历的过程中如果一个节点已经满了，则先分裂
     * @param e
     * @return
     */
    private BTree<E> insertNotNull(Page<E> node, E e) {
        Preconditions.checkState(!node.isFull());
        if (node.isLeaf()) {
            node.insertToLeaf(e);
            return this;
        }

        // 如果e存在，则是它的下标，如果e不存在，则是它要插入的位置
        FindIndex index = node.find(e);

        if (index.isFind) {
            node.update(index.idx, e);
            return this;
        }

        int idx = index.idx;
        Page<E> child = node.getChild(idx);
        if (child.isFull()) {
            split(node, child, idx);
            int c = e.compareTo(node.get(idx));
            // 要插入的恰好是原来child的中值，被提升到node中了
            if (c == 0) {
                node.update(idx, e);
                return this;
            } else if (c > 0) {
                child = node.getChild(idx + 1);
            }
        }

        return insertNotNull(child, e);
    }

    /**
     * 删除一个元素，删除比插入更复杂：插入只发生在叶子节点，而删除可能发生在所有的节点。
     * 在插入的时候，从上到下遍历的过程中每当遇到一个满节点就把它分裂，
     * 在删除的时候，保证所有遍历到的节点都至少拥有t个元素，这样删除一个元素后依然可以满足t-1的要求，或者它需要从它的子节点中删除一个元素时，
     * 当子节点不满足t-1的时候，可以借一个元素给它的子节点。
     * 删除可以分成三种：
     * 1. 当前节点是叶子节点，并且它包含待删除的元素，直接把元素删掉就行了，已经有别的方式保证当前节点至少有t个元素了，可以安全删除
     * 2. 当前节点是内部节点，并且它包含待删除元素，这里又分成3种情况
     *    (a) 如果key的左子节点有至少t个元素，从左子节点为根的子树中删除最大元素，使用这个删除的元素替换本节点待删除元素
     *    (b) 同样的，如果key的右子节点至少有t个元素，从右子节点为根的子树中删除最小元素，使用这个删除的元素替换本节点待删除元素
     *    (c) 如果左、右子树都只有t-1个元素，可以把key直接删除，同时把右子节点合并到左子节点上
     * 3. 如果当前节点 x 是内部节点，并且key不在当前节点内，确定要转移的子节点 x.c。如果x.c只有t-1个元素，需要做如下处理:
     *    (a) 如果x.ci的一个相邻兄弟节点包含至少t个元素，则可以从x中下移一个节点到x.ci中，从x.ci的兄弟节点中上移一个到x中
     *    (b) 如果x.ci及所有相邻的兄弟节点都只包含t-1个元素，则选择其中一个和x.ci合并，同时将x中的一个元素下移作为x.ci的中间元素
     * 第(3)保证了步骤(1),(2)处理时，当前节点至少有t个元素
     * @param key
     * @return
     */
    public E remove(E key) {
        return remove(root, key);
    }

    public E remove(Page<E> node, E key) {
        FindIndex index = node.find(key);
        int idx = index.idx;
        if (index.isFind) {
            // 情况1: 叶节点，直接删除
            if (node.isLeaf()) {
                return node.remove(idx);
            }

            // 情况2：内部节点, 分成几种情况
            if (node.getChild(idx).size() >= t) {
                // 2.a
                return node.update(idx, removeMax(node.children.get(idx)));
            } else if (node.getChild(idx +1).size() >= t) {
                // 2.b
                return node.update(idx, removeMin(node.getChild(idx+1)));
            } else {
                // 2.c: 合并节点
                Page<E> left = node.getChild(idx);
                Page<E> right = node.getChild(idx + 1);
                E _key = node.remove(idx);
                left.merge(_key, right);
                return remove(left, key);
            }

        } else {
            // 叶子节点没有找到
            if (node.isLeaf()) {
                return null;
            }
            Page<E> child = node.getChild(idx);
            if (child.size() >= t) {
                return remove(child, key);
            }

            // 情况3: 需要保证child必须要有至少t个关键字
            Page<E> leftSibling = null;
            Page<E> rightSibling = null;
            if (idx > 0 && (leftSibling = child.getChild(idx - 1)).size() >= t) {
                E max = leftSibling.max();
                Page<E> maxPage = leftSibling.maxPage();
                leftSibling.remove(leftSibling.size() - 1);
                E downKey = node.update(idx - 1, max);
                child.insertToInternal(0, downKey, maxPage);
            } else if (idx < node.size() && (rightSibling = child.getChild(idx + 1)).size() >= t) {
                E min = rightSibling.min();
                Page<E> minPage = rightSibling.minPage();
                rightSibling.remove(0);
                E downKey = node.update(idx + 1, min);
                child.insertToInternal(child.size(), downKey, minPage);
            } else {
                // leftsibling和rightsibling至少有一个不为空
                if (leftSibling !=  null) {
                    E _key = node.remove(idx);
                    leftSibling.merge(_key, child);
                    return remove(leftSibling, key);
                } else {
                    Preconditions.checkNotNull(rightSibling);
                    E _key = node.remove(idx + 1);
                    child.merge(_key, rightSibling);
                    return remove(child, key);
                }
            }
            return remove(child, key);
        }
    }

    public E removeMax(Page<E> node) {
        return null;
    }

    public E removeMin(Page<E> node) {
        return null;
    }

    public static void main(String[] args) {
        BTree<Character> bTree = new BTree<>(3);
        String source = "caoxiyngrshzjqfbdelmkwvtpu";
        for (int i = 0; i < source.length(); i++) {
            Character c = source.charAt(i);
            bTree.insert(c);
        }
        System.out.println(bTree);
    }
}
