package strings;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字符的最小子串。
 *
 * 示例：
 *
 * 输入: S = "ADOBECODEBANC", T = "ABC"
 * 输出: "BANC"
 * 说明：
 *
 * 如果 S 中不存这样的子串，则返回空字符串 ""。
 * 如果 S 中存在这样的子串，我们保证它是唯一的答案。
 *
 * 使用滑动窗口:
 * 1. right不停的后移直到满足条件
 * 2. 使用left后移，直到不满足条件
 * 3. 重复(1), (2)，直到遍历结束
 */
public class MinWindowSubString {

    public static String subString(String s, String t) {
        Map<Character, Integer> need = new HashMap<>(t.length());
        for (int i = 0; i < t.length(); i++) {
            need.compute(t.charAt(i), (k, v) -> v == null ? 1 : 1 + v);
        }


        Map<Character, Integer> window = new HashMap<>(t.length());
        int startIndex = -1;
        int endIndex = -1;

        int valid = 0;
        int left = 0;
        int right = 0;
        while (right < s.length()) {
            Character c = s.charAt(right++);
            int count = window.compute(c, (k, v) -> v == null ? 1 : 1 + v);
            if (count == need.getOrDefault(c, 0)) {
                valid++;
            }

            while (valid == need.size()) {
                if (startIndex < 0 || right - left < endIndex - startIndex) {
                    endIndex = right;
                    startIndex = left;
                }
                c = s.charAt(left++);
                count = window.compute(c, (k, v) -> --v);
                if (need.containsKey(c) && count + 1 == need.get(c)) {
                    valid--;
                }
            }
        }

        return startIndex < 0 ? "" : s.substring(startIndex, endIndex);
    }

    public static void main(String[] args) {
        System.out.println(subString("ADOBECODEBANC", "ABC"));
        System.out.println(subString("AAAAADBBCCE", "AABBCC"));
    }
}
