package strings;

/**
 * 对于一个给定的字符数组，原地反转
 */
public class ReverseString {
    public void reverseString(char[] s) {
        if (s == null || s.length == 0) {
            return;
        }
        int low = 0;
        int high = s.length - 1;
        char tmp;

        while (low < high) {
            tmp = s[low];
            s[low++] = s[high];
            s[high--] = tmp;
        }
    }

    public static void main(String[] args) {}

}
