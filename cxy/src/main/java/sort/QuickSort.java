package sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 手写quick sort，在不看任何讲解的情况下，加深印象，保证quick sort的记忆优势
 * 1. 对于小数组，优先使用插入排序
 * 2. 遇到相等的元素停止，这样对于只有一个元素组成的数组时间复杂度依然是nlgn
 * 3. 枢纽元的选择，取start, mid和end中的平均值, 并保证它们3个有序
 *
 */
public class QuickSort {

    /**
     * 对数据[start, end]处的元素排序, 调用该方法后，[start, end]处的结果
     * @param array
     * @param start
     * @param end
     */
    public static void quicksort(int[] array, int start, int end) {
        int size = (end - start);
        if (array == null || size <= 0) {
            return;
        }
        // 3数取中值, 同时3者有序
        int pivot = getMedian(array, start, end);
        // 如果只有2个或3个元素，则已经是有序的了
        if (size < 3) {
            return;
        }

        int mid = start + size/2;
        swap(array, mid, end-1);
        // i, j都比初始位置小1
        int i = start;
        // 最后一个是大于等于pivot的，倒数第二个是枢纽元
        int j = end - 1;
        // 这种写法的好处是: 每次循环都会使i和j至少前进一个位置
        while (true) {
            while (array[++i] < pivot);
            while (array[--j] > pivot);
            // 当它们两个相遇或者错位的时候就是终止之时
            if (i < j) {
                swap(array, i, j);
            } else {
                break;
            }
        }

        // i指向的是第一个不小于枢纽元的元素, 交换后，i就是枢纽元正确的位置
        swap(array, i, end-1);
        quicksort(array, start, j);
        quicksort(array, i+1, end);

    }

    private static int getMedian(int[] array, int start, int end) {
        int mid = start + (end - start) / 2;
        if (array[start] > array[mid]) {
            swap(array, start, mid);
        }

        if (array[mid] > array[end]) {
            swap(array, mid, end);
        }

        if (array[start] > array[mid]) {
            swap(array, start, mid);
        }

        return array[mid];
    }

    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static void main(String[] args) {
        int[] array = {1, 8, 2, 1, 9, 2, 2, 0, 0, 8, 2};
        quicksort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));

        array = new int[] {1, 2, 3, 4, 5, 7, 8, 11, 23, 45, 67, 85};
        quicksort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));

        array = new int[] {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};
        quicksort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));

        Random random = new Random();
        array = new int[128];
        for (int i = 0; i < 128; i++) {
            array[i] = random.nextInt(1024);
        }
        quicksort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }
}
