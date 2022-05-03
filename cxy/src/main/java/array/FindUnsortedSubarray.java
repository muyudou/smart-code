package array;

public class FindUnsortedSubarray {
    public static int findUnsortedSubarray(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        while (left < right) {
            if (nums[left] <= nums[left + 1]) {
                left++;
            } else {
                break;
            }
        }

        while (right > left) {
            if (nums[right] >= nums[right - 1]) {
                right--;
            } else {
                break;
            }
        }

        for (int i = left; i <= right; i++) {
            min = Integer.min(min, nums[i]);
            max = Integer.max(max, nums[i]);
        }

        for (int i = 0; i < left; i++) {
            if (nums[i] > min) {
                left = i;
                break;
            }
        }

        for (int i = nums.length - 1; i > right; i--) {
            if (nums[i] < max) {
                right = i;
                break;
            }
        }

        return left == right ? 0 : right - left + 1;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4};
        System.out.println(findUnsortedSubarray(array));
    }
}
