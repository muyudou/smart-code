package strings;

public class FirstUniqChar {
    /**
     * 找到第一个不重复的字符，假设字符串只包含26个小写字母
     * @param s
     * @return
     */
    public int firstUniqChar(String s) {
        int[] repeatNumber = new int[26];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            repeatNumber[c - 'a']++;
        }

        for (int i = 0; i < s.length(); i++) {
            if (repeatNumber[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }
}
