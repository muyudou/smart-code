package strings;

/**
 * 判断一个字符串是否为回文串
 */
public class Palindrome {

    public static boolean isPalindrome(String s) {
        if (s == null || s.isEmpty() || s.length() == 1) {
            return true;
        }

        int low = 0;
        int high = s.length() - 1;

        while (low < high) {
            if (s.charAt(low++) != s.charAt(high--)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(""));
        System.out.println(isPalindrome("a"));
        System.out.println(isPalindrome("aa"));
        System.out.println(isPalindrome("aba"));
        System.out.println(isPalindrome("abac"));
    }
}
