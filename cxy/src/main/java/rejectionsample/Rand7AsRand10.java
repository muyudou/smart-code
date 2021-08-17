package rejectionsample;

/**
 * 使用 rand7() 模拟 rand10()
 *
 * 进阶:
 * rand7()调用次数的期望值是多少
 * 你能否尽量少调用 rand7() ?
 *
 * 链接：https://leetcode-cn.com/problems/implement-rand10-using-rand7
 *
 * 这里需要利用两种思想
 * 1. 拒绝采样
 *   如果有了 rand10() 如何模拟 rand7()？
 *   我们知道在 rand10() 中 1-7出现的概率是相等的，因此如果 rand10() 返回的值大于7就拒绝，重新生成随机数。
 *   因此这个题目现在要转换成生成一个 rand_x()，其中 x > 10
 *
 * 2. 等概率随机数生成
 *   （rand_x() - 1) * y + rand_y() 可以等概率的生成 [1, x * y] 之间的随机值
 *
 */
public class Rand7AsRand10 {
    public static int rand7() {
        return 1;
    }

    public static int rand10() {
        int x = rand7();
        while (true) {
            int y = rand7();
            int r = (x - 1) * 7 + y;
            if (r <= 10) {
                return r;
            } else if (r <= 20) {
                return r - 10;
            } else if (r <= 30) {
                return r - 20;
            } else if (r <= 40) {
                return r - 30;
            }
            x = y;
        }
    }
}
