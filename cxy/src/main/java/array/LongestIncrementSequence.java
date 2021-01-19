package array;

/**
 * 求最长上升子序列
 * 比如给定序列: 1,5,3,4,6,9,7,8
 * 最长上升序列: 1,  3,4,6,  7,8
 * 使用动态规划"我从哪里来"
 */
public class LongestIncrementSequence {

    /**
     * 对于每个f(n)而言，找到每一个小于a[n]的x，如果f(n) = max{f(x)} + 1
     * @param nums
     * @return
     */
    public static int lis(int[] nums) {
        int[] maxes = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int max = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    max = Integer.max(max, maxes[j] + 1);
                }
            }
            maxes[i] = max;
        }

        int max = -1;
        for (int m : maxes) {
            if (m > max) {
                max = m;
            }
        }

        return max;
    }

    public static void main(String[] args) {
        int[] seq = {1,5,3,4,6,9,7,8};
        System.out.println(lis(seq));
    }
}
