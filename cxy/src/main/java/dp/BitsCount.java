package dp;

/**
 * 给你一个整数 n ，对于 0 <= i <= n 中的每个 i ，计算其二进制表示中 1 的个数 ，返回一个长度为 n + 1 的数组 ans 作为答案。
 *
 *
 * 示例 1：
 *
 * 输入：n = 2
 * 输出：[0,1,1]
 * 解释：
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 * 示例 2：
 *
 * 输入：n = 5
 * 输出：[0,1,1,2,1,2]
 * 解释：
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 * 3 --> 11
 * 4 --> 100
 * 5 --> 101
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/counting-bits
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class BitsCount {
    /**
     * 如果使用 dp 思路来解决的话就是一个找零问题，有1, 2, 4, 8, 16, 32, 64, 128, 256....，怎么把 n 给组合起来
     * f(n) = min {f(n-1), f(n-2), f(n-4) .... f(n-2^x)} + 1
     * @param n
     * @return
     */
    public int[] countBits(int n) {
        int[] bitsNumber = new int[n + 1];
        int power = 1;
        for (int k = 1; k <= n; k++) {
            if (k == power * 2) {
                power = power << 1;
            }
            bitsNumber[k] = bitsNumber[k - power] + 1;
        }

        return bitsNumber;
    }
}
