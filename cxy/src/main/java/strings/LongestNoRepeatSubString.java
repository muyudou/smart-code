package strings;

import java.util.Arrays;

public class LongestNoRepeatSubString {
    public static int distinctSubString(String s) {
        int[] indexMap = new int[127];
        Arrays.fill(indexMap, -1);

        int maxSubLength = 0;
        // 目前已知的不重复子串的起始位置
        int startIndex = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = indexMap[c];
            if (index >= 0) {
                maxSubLength = Integer.max(i - startIndex, maxSubLength);
                /**
                 * current和startIndex重复了，那么[startIndex + 1, current]都不存在重复, 因此把startIndex后移一位
                 */
                startIndex = index + 1;
            }
            indexMap[c] = i;
        }

        return maxSubLength;
    }

    public static void main(String[] args) {
        System.out.println(distinctSubString("abcdabcd"));
        System.out.println(distinctSubString("aaaaaaaa"));
        System.out.println(distinctSubString("abcdefahikjbkd"));
    }
}
