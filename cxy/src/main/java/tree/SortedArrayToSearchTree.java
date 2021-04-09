package tree;

/**
 * 给定一个有序数组，将它转成二叉平衡搜索树
 */
public class SortedArrayToSearchTree {

    public TreeNode sortedArrayToBST(int[] nums) {
        return toSearchTree(nums, 0, nums == null ? 0 : nums.length);
    }

    public static TreeNode toSearchTree(int[] array, int startInclude, int endExclude) {
        int size;
        // 1. 异常处理 和 递归终止条件
        if (array == null || (size = endExclude - startInclude) <= 0) {
            return null;
        }
        if (size == 1) {
            return new TreeNode(array[startInclude]);
        }

        // 2. 中间值作为root
        int mid = (startInclude + endExclude) / 2;
        TreeNode root = new TreeNode(array[mid]);

        // 3. 递归生成 left 和 right
        root.left = toSearchTree(array, startInclude, mid);
        root.right = toSearchTree(array, mid + 1, endExclude);

        return root;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8};
        TreeNode root = toSearchTree(array, 0, array.length);

        array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        root = toSearchTree(array, 0, array.length);
        System.out.println(root);
    }
}
