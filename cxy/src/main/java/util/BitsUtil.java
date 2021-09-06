package util;

public class BitsUtil {
    /**
     * @return 最小的大于value的2次幂
     */
    public static int upper2N(int value) {
        value =  value - 1;
        value |= value >> 1;
        value |= value >> 2;
        value |= value >> 4;
        value |= value >> 8;
        value |= value >> 16;

        return value + 1;
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 33; i++) {
            System.out.println(i + ": " + upper2N(i));
        }
    }
}
