package binarytree;

/**
 * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
 *
 * 说明：
 *   所有节点的值都是唯一的。
 *   p、q 为不同节点且均存在于给定的二叉搜索树中。
 *
 * 链接: https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 *
 */
public class LowestCommonAncestor {
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (p.val < q.val) {
      return x(root, p, q);
    } else {
      return x(root, q, p);
    }
  }

  public TreeNode x(TreeNode root, TreeNode min, TreeNode max) {
    int rValue = root.val;
    int minValue = min.val;
    int maxValue = max.val;

    if (minValue == rValue || maxValue == rValue || (minValue < rValue && maxValue > rValue)) {
      return root;
    }

    // 两者在同一颗子树上
    TreeNode newRoot = minValue < rValue ? root.left : root.right;
    return x(newRoot, min, max);
  }
}
