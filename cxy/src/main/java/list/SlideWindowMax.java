package list;

import util.Preconditions;

import java.util.Arrays;

/**
 * 给定一个数组：4, 3, 5, 4, 3, 3, 6, 7
 * 给定一个窗口大小w: 3
 * 窗口从左至右滑动，每次滑动一个元素
 * 求出每个窗口的最大值组成的数据
 *
 * 比如上面的数组的第一个窗口是: [4, 3, 5], 4, 3, 3, 6, 7 --> 最大值5
 *              第二个窗口是: 4, [3, 5, 4], 3, 3, 6, 7 --> 最大值5
 *              第三个窗口是: 4, 3, [5, 4, 3], 3, 6, 7 --> 最大值5
 *              第四个窗口是: 4, 3, 5, [4, 3, 3], 6, 7 --> 最大值4
 *
 * 对于一个给定的长度为n的数组，窗口大小为w，结果大小为n-w+1个窗口大小
 *
 * 思路1： O(nw)时间复杂度的实现
 *
 * 思路2： 有没有O(n)的实现
 *
 */
public class SlideWindowMax {

    public static void main(String[] args) {
        int[] maxes = onw(new int[]{4, 3, 5, 4, 3, 3, 6, 7}, 3);
        System.out.println(Arrays.toString(maxes));
    }

    public static int[] onw(int[] array, int w) {
        Preconditions.checkArgument(array.length >= w);
        int n = array.length;
        int[] maxes = new int[n-w+1];

        for (int i = 0; i < n-w+1; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j < i+3; j++) {
                max = Integer.max(max, array[j]);
            }
            maxes[i] = max;
        }

        return maxes;
    }

    public static int[] on(int[] array, int w) {
        Preconditions.checkState(array.length >= w);
        int n = array.length-w+1;
        int[] maxes = new int[n];

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < w; i++) {
            max = Integer.max(max, array[i]);
        }
        maxes[0] = max;

        for (int i = w; i < n; i++) {

        }

        return null;
    }
}
