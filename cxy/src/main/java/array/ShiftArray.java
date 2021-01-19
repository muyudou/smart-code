package array;

import java.util.Arrays;

public class ShiftArray {
    /**
     * 如果n和k都是偶数，则需要特殊处理
     * @param a
     * @param k
     */
    public static void rightShift(int[] a, int k) {
        int n = a.length;
        int startIndex = 0;
        int count = 0;
        int prev = a[0];
        int nextIndex = 0;

        while (count++ < n) {
            nextIndex = (nextIndex + k) % n;
            int tmp = a[nextIndex];
            a[nextIndex] = prev;
            prev = tmp;

            // 回到原点了，只发生在n和k都是偶数的情况下
            if (startIndex == nextIndex) {
                prev = a[++startIndex];
                nextIndex = startIndex;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8};
        rightShift(a, 2);
        System.out.println(Arrays.toString(a));
    }
}
