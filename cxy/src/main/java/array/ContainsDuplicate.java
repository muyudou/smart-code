package array;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个int型数组，判断里面是否包含重复的元素
 *
 * 思路1：最简单的利用set来保存已经见过的元素，空间复杂度o(n)，时间复杂度o(n)
 *
 * 思路2：两层循环，时间复杂度o(n^2)
 *
 * 思路3：排序，时间复杂度o(nlog(n))，但是会改变原来的数组，不可取
 *
 * 思路4：整型有哪些可利用的特性?
 *
 * @author caoxiaoyong
 * @date   2018-06-03
 */
public class ContainsDuplicate {
    /** 利用set, 空间复杂度o(n) */
    public boolean  containsDuplicate(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return false;
        }

        Set<Integer> see = new HashSet<>(nums.length);
        for (int n : nums) {
            if (see.contains(n)) {
                return true;
            }
            see.add(n);
        }
        return false;
    }
}
