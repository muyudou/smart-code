package iterator;

/**
 * 给定一个rle的数组，支持next(n)方法，它会消耗掉n个元素，并返回最后一个value，如果剩余的元素<n，则返回-1
 * repeate number可能为0，也就是rle数组可能是: [3,4, 0,5, 8,10]
 */
public class RLEIterator {
    private int[] rle;
    private int idx;

    public RLEIterator(int[] rle) {
        if (rle == null || (rle.length & 1) != 0) {
            throw new IllegalArgumentException("must be odd");
        }
        this.rle = rle;
    }

    /**
     * 消耗掉n个元素
     * @param n
     * @return n序列的最后一个元素，如果剩余元素不足n个，则返回-1
     */
    public int next(int n) {
        if (idx == rle.length) {
            return -1;
        }

        int value = -1;
        while (n != 0 && idx < rle.length - 1) {
            int repeatNumber = rle[idx];
            if (repeatNumber < n) {
                n -= repeatNumber;
                idx += 2;
            } else if (repeatNumber > n){
                value = rle[idx + 1];
                rle[idx] -= n;
                break;
            } else {
                value = rle[idx + 1];
                idx += 2;
                break;
            }
        }

        return value;
    }
}
