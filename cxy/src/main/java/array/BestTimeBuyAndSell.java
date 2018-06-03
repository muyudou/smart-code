package array;

/**
 * 最佳买卖时机，数组中的元素表示某天的价格，根据连续n天的价格，计算最佳的买卖时机，以获取最大的收益。可以买卖多次，同一天只能买卖一次
 * 比如序列：7， 1， 5， 3， 4， 6
 *            ^   ^  ^       ^
 *            |   |  |       |
 *            买  卖  卖      卖
 *
 * 思路1：从后往前遍历，每找到一个连续的递降子序列，就可以执行一次买卖，比如上面的可以分成两个序列：[6， 4， 3], [5, 1]
 *
 * @author caoxiaoyong
 * @date   2018-06-03
 */
public class BestTimeBuyAndSell {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int n = prices.length;
        // 递降序列中最大的值
        int max = -1;
        int prev = -1;

        // 收益
        int profit = 0;
        for (int i = n -1; i >= 0; i--) {
            // 新一段递降序列
            if (prices[i] > prev) {
                profit += max - prev;
                max = prev = prices[i];
                continue;
            }

            prev = prices[i];
        }

        //              prices[x]...prices[0]构成的递降序列
        return profit + (max - prev);
    }
}
