package binarytree;

/**
 * 给定一个有序数组，将它转成二叉平衡搜索树
 */
public class SortedArrayToSearchTree {

    public TreeNode sortedArrayToBST(int[] nums) {
        return toSearchTree(nums, 0, nums == null ? 0 : nums.length);
    }

    public static TreeNode toSearchTree(int[] array, int startInclude, int endExclude) {
      if (startInclude == endExclude) {
        return null;
      }

      if (startInclude + 1 == endExclude) {
          return new TreeNode(array[startInclude]);
      }

      int mid = (startInclude + endExclude) / 2;
      TreeNode parent = new TreeNode(array[mid]);
      parent.left = toSearchTree(array, startInclude, mid);
      parent.right = toSearchTree(array, mid + 1, endExclude);
      return parent;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8};
        TreeNode root = toSearchTree(array, 0, array.length);


        array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        root = toSearchTree(array, 0, array.length);
        System.out.println(root);
    }
}
