package array;

/**
 *给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 示例:
 *
 * 输入: [-2,1,-3,4,-1,2,1,-5,4]
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 * 进阶:
 *
 * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
 *
 * 链接：https://leetcode-cn.com/problems/maximum-subarray
 */
public class MaxSequenceSum {
    public static int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int preMax = 0;
        for (int i = 0; i < nums.length; i++) {
            if (preMax >= 0) {
                preMax += nums[i];
            } else {
                preMax = nums[i];
            }
            max = Integer.max(max, preMax);
        }

        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
        System.out.println(maxSubArray(new int[]{4,-1,2,1,-1,3}));
        System.out.println(maxSubArray(new int[]{-1, -2, -3, -4, -5}));
    }
}
