package array;

import java.util.Arrays;

/**
 * 删除有序数组中的重复元素，要求时间复杂度为O(1)
 * 思路1：由于数组是有序的，因此相同的元素是相邻的，因此可以使用两次遍历：
 * 1） 标记那些与前一个位置相同的元素，把它们设为一个特殊值
 * 2) 第二次遍历，从index 1处开始，把未标记的元素拷贝到一起
 *
 * 思路2: 压根不需要第一遍的标记，使用一个index来作为有效元素的存储，当遇到相同元素时忽略，
 * 遇到不同元素时，把它放到index处，index后移
 *
 * @author yuhongye
 * @date   2018-05-26
 */
public class RemoveDuplicatesSortedArray {

    /**
     * 删除重复的元素
     * @param nums
     * @return 去重后的数组长度
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        /**
         * 即时数组中存在MIN_VALUE也没关系，因为它必定在index 0处，
         * 而compact是在index 1处开始的
         */
        int flag = Integer.MIN_VALUE;

        // 第一次遍历，标记可以删除的元素
        int prev = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == prev) {
                nums[i] = flag;
            } else {
                prev = nums[i];
            }
        }

        int index = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != flag && i != ++index) {
                nums[index] = nums[i];
            }
        }
        return index + 1;
    }

    public static int removeDuplicate2(int[] nums) {
        if (nums == null || nums.length == 1) {
            return 0;
        }

        // index下一个有效位置
        int index = 1;
        int value = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != value) {
                value = nums[index++] = nums[i];
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] aray = {0,0,1,1,1,2,2,3,3,4};
        System.out.println(removeDuplicate2(aray));
        System.out.println(Arrays.toString(aray));
    }
}
