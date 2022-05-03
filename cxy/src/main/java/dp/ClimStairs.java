package dp;

/**
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 *
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 *
 * 示例 1：
 *
 * 输入：n = 2
 * 输出：2
 * 解释：有两种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶
 * 2. 2 阶
 * 示例 2：
 *
 * 输入：n = 3
 * 输出：3
 * 解释：有三种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶 + 1 阶
 * 2. 1 阶 + 2 阶
 * 3. 2 阶 + 1 阶
 *
 * 提示：
 *
 * 1 <= n <= 45
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/climbing-stairs
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ClimStairs {

    /**
     * 最后一步是 1 阶: 转化成 f(n-1)
     * 最后一步是 2 阶：转化成 f(n-2)
     * 所以总的方法是: f(n-1) + f(n-2)
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        int[] f = new int[n+1];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i <= n; i++) {
            f[i] = f[i-1] + f[i-2];
        }
        return f[n];
    }

    /**
     * 不需要 O(n) 的空间复杂度
     * @param n
     * @return
     */
    public int optClimStairs(int n) {
        // f(n-1)
        int f_n_1 = 1;
        // f(n-2)
        int f_n_2 = 0;
        for (int i = 2; i <= n; i++) {
            int f_n = f_n_1 + f_n_2;
            f_n_2 = f_n_1;
            f_n_1 = f_n;
        }

        return f_n_1 + f_n_2;
    }
}
