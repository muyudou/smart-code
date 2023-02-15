package array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * 给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。
 *
 * 示例 1 :
 *
 * 输入:nums = [1,1,1], k = 2
 * 输出: 2 , [1,1] 与 [1,1] 为两种不同的情况。
 * 说明 :
 *
 * 数组的长度为 [1, 20,000]。
 * 数组中元素的范围是 [-1000, 1000] ，且整数 k 的范围是 [-1e7, 1e7]。
 */
public class ContinousSumK {
    /**
     * @param nums
     * @param k
     * @return
     */
    public static List<int[]> continousSumK(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return Collections.emptyList();
        }

        List<int[]> result = new ArrayList<>();

        int left = 0;
        int sum = 0;
        // [1, 2, 3, 2, 1, 4, 1, 1, 4, 1]
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            while (sum > k) {
                sum -= nums[left];
                left++;
            }

            if (sum == k) {
                int size = i - left + 1;
                int[] continous = new int[size];
                System.arraycopy(nums, left, continous, 0, size);
                result.add(continous);
                sum -= nums[left];
                left++;
            }
        }

        return result;
    }

    

    public static void main(String[] args) {
        int[] nums = {1, 1, 1};
        List<int[]> result = continousSumK(nums, 2);
        assertEquals(result.size(), 2);
        assertArrayEquals(new int[]{1, 1}, result.get(0));
        assertArrayEquals(new int[]{1, 1}, result.get(1));

        nums = new int[]{2, 2, 2};
        result = continousSumK(nums, 2);
        assertEquals(result.size(), 3);
        assertArrayEquals(new int[]{2}, result.get(0));
        assertArrayEquals(new int[]{2}, result.get(1));
        assertArrayEquals(new int[]{2}, result.get(2));

        nums = new int[]{2, 3, 4};
        result = continousSumK(nums, 6);
        assertEquals(0, result.size());

        nums = new int[]{1, 2, 3, 2, 1, 4, 1, 1, 4, 1};
        result = continousSumK(nums, 5);
        assertEquals(6, result.size());
        assertArrayEquals(new int[]{2, 3}, result.get(0));
        assertArrayEquals(new int[]{3, 2}, result.get(1));
        assertArrayEquals(new int[]{1, 4}, result.get(2));
        assertArrayEquals(new int[]{4, 1}, result.get(3));
        assertArrayEquals(new int[]{1, 4}, result.get(4));
        assertArrayEquals(new int[]{4, 1}, result.get(5));
    }
}
