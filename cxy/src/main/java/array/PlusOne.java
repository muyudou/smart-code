package array;

/**
 * 由数组表示的非负整数，将它的值+1
 */
public class PlusOne {
    public int[] plusOne(int[] digits) {
        if (digits == null) {
            return null;
        }

        if (digits.length == 0) {
            return digits;
        }
        if (digits.length == 1 && digits[0] < 9) {
            digits[0]++;
            return digits;
        }
        int length = digits.length - 1;
        int value = digits[length] + 1;
        if (value < 10) {
            digits[length] = value;
            return digits;
        }
        digits[length] = 0;

        int carrybit = 1;
        for (int i = length - 1; i >= 0; i--) {
            value = digits[i] +  carrybit;
            if (value < 10) {
                digits[i] = value;
                return digits;
            } else {
                digits[i] = 0;
                carrybit = 1;
            }
        }
        int[] result = new int[digits.length + 1];
        result[0] = carrybit;
        System.arraycopy(digits, 0, result, 1, digits.length);
        return result;
    }
}
