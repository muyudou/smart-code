package datastruct.array;

/**
 * 不可自动扩展的排序去重数组
 * @param <E>
 */
public class SortedUniqArray<E extends Comparable<E>> extends Array<E> {
    public SortedUniqArray(int capacity) {
        super(capacity);
    }

    /**
     * 将e插入数组中
     * @param e
     * @return 如果在当前数组中存在一个元素x.compareTo(e) == 0， 返回x, 同时使用e替换x;
     *         如果不存在这样的元素，返回null
     */
    @Override
    public E insert(E e) {
        Index index = binarySearch(e);
        if (index.isExist()) {
            E old = (E) data[index.idx];
            data[index.idx] = e;
            return old;
        } else {
            checkState(size < capacity, "datastruct.array is full, cannot insert any more element.");
            insertAt(index.idx, e);
            return null;
        }
    }

    @Override
    public void insertAt(int index, E e) {
        int prev = index - 1;
        int next = index;
        if (prev >= 0) {
            checkState(((E) data[prev]).compareTo(e) < 0, "顺序错乱");
        }
        if (next < size) {
            checkState(((E) data[next]).compareTo(e) > 0, "顺序错乱");
        }

        super.insertAt(index, e);
    }

    /**
     * 二分查找
     * @param e
     * @return 如果找到了返回的就是e所在的下标；如果没找到返回的是第一个大于e元素的下标，也就是e应该插入的位置
     */
    public Index binarySearch(E e) {
        int left = 0;
        int right = size - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int c = e.compareTo((E) data[mid]);
            if (c == 0) {
                return Index.exist(mid);
            } else if (c < 0){
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return Index.not(left);
    }
}
