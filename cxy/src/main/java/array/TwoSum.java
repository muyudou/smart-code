package array;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组和一个目标值，找出其中两个数的和等于目标值，假定恰好存在且只存在这样的两个数
 * 思路1： 两层循环可以搞定，时间复杂度o(n^2)
 * 思路2： 有没有O(nlogn)的解法
 * 思路3:  有没有O(n)的解法
 *
 * @date 2018-06-15
 */
public class TwoSum {
    /**
     * @param nums
     * @param target
     * @return 返回两个数的下标，意味着不能排序了
     */
    public int[] twoSum(int[] nums, int target) {
        int[] indexes = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    indexes[0] = i;
                    indexes[1] = j;
                    return indexes;
                }
            }
        }
        throw new IllegalArgumentException("no two numbers sum equals to target");
    }

    /**
     * 可以使用空间换时间，使用map保存已经遍历的数据跟target的差值，在遍历当前数值时，先查询是否包含在map中，如果有则返回
     * @param nums
     * @param target
     * @return
     */
    public int[] morespace(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            Integer index = map.get(nums[i]);
            if (index != null) {
                return new int[] {index, i};
            } else {
                map.put(target - nums[i], i);
            }
        }
        throw new IllegalArgumentException("no two numbers sum equals to target");
    }
}
