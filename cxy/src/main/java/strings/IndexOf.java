package strings;

public class IndexOf {
    public int isSubString(String haystack, String needle) {
        if (haystack == needle) {
            return 0;
        }
        if (haystack == null || needle == null || haystack.length() < needle.length()) {
            return -1;
        }
        if (needle.length() == 0) {
            return 0;
        }

        int n = needle.length();
        for (int i = 0; i <= haystack.length() - n; i++) {
            int k = 0;
            for (; k < n; k++) {
                if (haystack.charAt(i + k) != needle.charAt(k)) {
                    break;
                }
            }
            if (k == n) {
                return i;
            }
        }

        return -1;
    }
}
