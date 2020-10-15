package array;

/**
 * 题目：在一个排序的数组中，value可能会重复很多次，对于给定的值，返回value在数组的start index和end index
 */
public class BinarySearch2 {

    /**
     * @param target
     * @return
     */
    public static String binarySearch(int[] array, int target) {
        if (array == null || array.length == 0 || target < array[0] || target > array[array.length - 1]) {
            return "null";
        }
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = (high - low)/2 + low;
            int value = array[mid];
            if (value < target) {
                low = mid + 1;
            } else if (value > target) {
                high = mid -1;
            } else {
                // 命中了
                int startIndex = binarySearchLeft(array, target, 0, mid);
                int endIndex = binarySearchRight(array, target, mid, array.length-1);
                return "[" + startIndex + "," + endIndex + "]";
            }
        }

        return "null";
    }

    /**
     *
     * 寻找最左边的target index
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchLeft(int[] array, int target, int low, int high) {
        // high是命中的
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid] == target) {
                high = mid - 1;
            } else {
                // mid已经是未命中了
                low = mid + 1;
            }
        }

        return high + 1;
    }

    public static int binarySearchRight(int[] array, int target, int low, int high) {
        // low是命中的
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid] == target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low - 1;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 6, 7, 8};
        System.out.println(binarySearch(array, 6));
        System.out.println(binarySearch(array, 5));
        System.out.println(binarySearch(array, 8));
        array = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        System.out.println(binarySearch(array, 1));

        array = new int[]{1, 2, 5, 6, 7, 8, 8, 8, 8, 10};
        System.out.println(binarySearch(array, 4));
    }
}
