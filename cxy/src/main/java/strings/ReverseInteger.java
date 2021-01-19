package strings;

import util.Preconditions;

/**
 * 反转一个10进制数
 */
public class ReverseInteger {
    public static int reverse(int x) {
        long result = 0;
        while (x != 0) {
            result = result * 10 + (x % 10);
            x /= 10;
        }
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return 0;
        } else {
            return (int) result;
        }
    }

    public static void main(String[] args) {
        Preconditions.checkState(128 == reverse(821));
        Preconditions.checkState(-128 == reverse(-821));
        Preconditions.checkState(0 == reverse(-0));
    }
}
