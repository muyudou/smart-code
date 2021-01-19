package array;

import java.util.Arrays;

/**
 * 计算两个数组的交集
 * 思路1： 两层for循环,时间复杂度O(n^2)
 * 思路2： 排序，变成了合并两个有序数据，时间复杂度O(nlgn)
 */
public class ArrayIntersection {

    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            return nums1;
        }
        if (nums1.length == 0 || nums2.length == 0) {
            return new int[0];
        }

        Arrays.sort(nums1);
        Arrays.sort(nums2);
        return intersectionTwoSortedArray(nums1, nums2);
    }

    public int[] intersectionTwoSortedArray(int[] nums1, int[] nums2) {
        int size = Integer.min(nums1.length, nums2.length);
        int[] result = new int[size];
        int index = 0;
        int i = 0;
        int j = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                result[index++] = nums1[i];
                i++;
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else {
                j++;
            }
        }

        int[] newResult = new int[index];
        System.arraycopy(result, 0, newResult, 0, index);
        return newResult;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 2, 1};
        int[] nums2 = {2, 2};
        ArrayIntersection intersection = new ArrayIntersection();
        System.out.println(Arrays.toString(intersection.intersect(nums1, nums2)));
    }


}
