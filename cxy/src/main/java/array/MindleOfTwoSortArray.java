package array;

/**
 * 两个有序数组，合并后的中位数
 * 时间复杂度: O(log(n + m)), n,m分别为数组的长度
 */
public class MindleOfTwoSortArray {

    public static int getMindleOfTwoSortArray(int[] a1, int[] a2) {
        int mindle1 = a1.length / 2;
        int mindle2 = a2.length / 2;

        if (a1[mindle1] == a2[mindle2]) {
            return a1[mindle1];
        } else if (a1[mindle1] < a2[mindle2]) {
            getMedian(a1, a2);
        } else {
            getMedian(a2, a1);
        }
        return 0;
    }

    public static int getMedian(int[] small, int[] big) {
        int bigBase = small.length;
        int smallPrev = small.length;
        int bigPrev = bigBase + big.length / 2;
        int smallMiddle = 0;
        int bigMiddle = 0;

        while (smallMiddle != smallPrev && bigMiddle != bigPrev) {

        }
        return 0;
    }
}
