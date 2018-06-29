package array;

/**
 * 有重复数据的有序数据，找到target第一出现的位置
 *
 * @author caoxiaoyong
 * @date   2018-06-28
 */
public class BinarySearch {
    /**
     * 找到target第一次出现的位置，否则返回-1
     * @param array
     * @param target
     * @return
     */
    public static int binarySearch(int[] array, int target) {
        // 特殊条件处理
        if (array == null || target < array[0] || target > array[array.length - 1]) {
            return -1;
        }
        int n = array.length;
        int left = 0;
        int right = n - 1;
        int index = -1;
        while (left <= right) {
            int mid = left + ((right - left) >> 2);
            if (target < array[mid]) {
                right = mid - 1;
            } else if (target > array[mid]) {
                left = mid + 1;
            } else {
                // 其实可以往前搜索一小段数据，如果没有找到的话，在使用二分查找
                index = mid;
                right = mid - 1;
            }
        }

        return index;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 6, 7, 8};
        System.out.println(binarySearch(array, 6));
        System.out.println(binarySearch(array, 5));
        System.out.println(binarySearch(array, 8));
        array = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        System.out.println(binarySearch(array, 1));
    }
}
