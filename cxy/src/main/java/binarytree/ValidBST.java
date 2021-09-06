package binarytree;

/**
 给定一个二叉树，判断其是否是一个有效的二叉搜索树。

 假设一个二叉搜索树具有如下特征：

 节点的左子树只包含小于当前节点的数。
 节点的右子树只包含大于当前节点的数。
 所有左子树和右子树自身必须也是二叉搜索树。

 链接：https://leetcode-cn.com/problems/validate-binary-search-tree
 */
public class ValidBST {

  public boolean isValidBST(TreeNode root) {
    return false;
  }

  public long isValidBST0(TreeNode node) {
    if (node.left == null && node.right == null) {
      return node.val;
    }

    if (node.left != null) {
      long max = isValidBST0(node.left);
      if (max == Long.MIN_VALUE || max > node.val) {
        return Long.MIN_VALUE;
      }
    }

    if (node.right != null) {
      long min = isValidBST0(node.right);
      if (min == Long.MIN_VALUE || min < node.val) {
        return Long.MIN_VALUE;
      }
    }

    return 0;
  }

  public int maxValue(TreeNode node) {
    if (node.left == null && node.right == null) {
      return node.val;
    }
    int max = node.val;
    if (node.left != null) {
      max = Integer.max(max, maxValue(node.left));
    }

    if (node.right != null) {
      max = Integer.min(max, maxValue(node.right));
    }

    return max;
  }

  public int minValue(TreeNode node) {
    if (node.left == null && node.right == null) {
      return node.val;
    }
    int min = node.val;
    if (node.left != null) {
      min = Integer.min(min, minValue(node.left));
    }

    if (node.right != null) {
      min = Integer.min(min, minValue(node.right));
    }

    return min;
  }
}
