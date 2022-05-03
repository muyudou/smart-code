package dp;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
 *
 * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
 *
 *
 * 示例 1:
 *
 * 输入: numRows = 5
 * 输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
 *
 * 输入: numRows = 1
 * 输出: [[1]]
 *
 * 提示:
 *
 * 1 <= numRows <= 30
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/pascals-triangle
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class YanghuiTriangle {

    public List<List<Integer>> generate(int numRows) {
        Integer one = 1;
        List<List<Integer>> result = new ArrayList<>(numRows);
        List<Integer> list = new ArrayList<>(1);
        list.add(one);
        result.add(list);
        List<Integer> lastRow = list;
        for (int i = 2; i <= numRows; i++) {
            list = new ArrayList<>(i);
            // 两边都是1，省去越界判断
            list.add(one);
            for (int k = 1; k < i - 1; k++) {
                list.add(lastRow.get(k-1) + lastRow.get(k));
            }
            list.add(one);
            result.add(list);
            lastRow = list;
        }
        return result;
    }
}
