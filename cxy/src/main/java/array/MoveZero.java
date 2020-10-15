package array;

/**
 * 将数组中的所有0移动到末尾，同时保持其他元素的相对顺序
 * 原地move
 */
public class MoveZero {

    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        boolean zero = false;
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zero = true;
            } else if (zero){
                nums[index++] = nums[i];
            } else {
                index++;
            }
        }

        for (int i = index; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    public static void moveZeros2(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[index++] = nums[i];
            }
        }

        for (int i = index; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
