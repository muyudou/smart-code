package strings;

public class ValidAnagram {
    /**
     * 判断 s 和 t 的字符完全一样，只是顺序不同
     * 假设只有小写字母
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram(String s, String t) {
        if (s == t) {
            return true;
        }
        if (s == null || t == null) {
            return false;
        }

        int n;
        if ((n = s.length()) != t.length()) {
            return false;
        }

        int[] chars = new int[127];
        for (int i = 0; i < n; i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);
            if (c1 != c2) {
                chars[c1]++;
                chars[c2]--;
            }
        }

        for (int i = 'a'; i <= 'z'; i++) {
            if (chars[i] != 0) {
                return false;
            }
        }
        return true;
    }
}
