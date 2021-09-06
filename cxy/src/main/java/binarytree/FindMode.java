package binarytree;

/**
 * 给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
 *
 * 假定 BST 有如下定义：
 *   结点左子树中所含结点的值小于等于当前结点的值
 *   结点右子树中所含结点的值大于等于当前结点的值
 *   左子树和右子树都是二叉搜索树
 * 例如：
 * 给定 BST [1,null,2,2],
 *
 *    1
 *     \
 *      2
 *     /
 *    2
 * 返回[2].
 *
 * 提示：如果众数超过1个，不需考虑输出顺序
 *
 * 进阶：你可以不使用额外的空间吗？（假设由递归产生的隐式调用栈的开销不被计算在内）
 *
 * 链接：https://leetcode-cn.com/problems/find-mode-in-binary-search-tree
 */
public class FindMode {
  public int[] findMode(TreeNode root) {
    return null;
  }

  public int[] x(TreeNode root) {
    if (root.left == null || root.right == null) {
      return new int[] {root.val};
    }
    return null;


  }
}
