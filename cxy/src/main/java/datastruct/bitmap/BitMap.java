package datastruct.bitmap;

import java.util.Arrays;

import static java.lang.Integer.min;
import static util.Preconditions.checkArgument;

/**
 * 用来练手的bit map, 主要是锻炼中自己的基础编码能力
 */
public class BitMap {
    static final int BIT_NUM_PER_VALUE = 64;
    static final int SHIFT_ADDRESS = 6;

    static final int DEFAULT_BIT_NUM = 64;

    private long[] bits;

    // true count
    private int count = 0;

    public BitMap() {
        this(DEFAULT_BIT_NUM);
    }

    public BitMap(int nbits) {
        checkArgument(nbits > 0);
        int n = (nbits + BIT_NUM_PER_VALUE - 1) / BIT_NUM_PER_VALUE;
        bits = new long[n];
    }

    /**
     * 全部的bits都置为false
     */
    public void clear() {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = 0;
        }
    }

    /**
     * @param bitIndex 把指定位置设置为false，如果超出当前最大值，则什么也不做
     */
    public void clear(int bitIndex) {
        int index = wordIndex(bitIndex);
        if (index >= bits.length) {
            return;
        }
        if ((bits[index] & (1L << bitIndex)) != 0) {
            count--;
        }
        // <<操作只有最后6位有效
        bits[index] &= ~(1L << bitIndex);
    }

    /**
     * @param bitIndex 要查询的位置
     * @return true if bitIndex is set, else false
     */
    public boolean get(int bitIndex) {
        int index = wordIndex(bitIndex);
        if (index >= bits.length) {
            return false;
        }
        return (bits[index] & (1L << bitIndex)) != 0;
    }

    /**
     * @param bitIndex 把该位置设为true
     */
    public void set(int bitIndex) {
        int index = wordIndex(bitIndex);
        ensureCapacity(index);
        if ((bits[index] & (1L << bitIndex)) == 0) {
            count++;
            bits[index] |= 1L << bitIndex;
        }
    }

    /**
     * @return 被设置为true的bit个数
     */
    public int trueCount() {
        return count;
    }

    /**
     * @param other this and other
     */
    public void and(BitMap other) {
        int size = min(bits.length, other.bits.length);
        for (int i = 0; i < size; i++) {
            bits[i] &= other.bits[i];
        }
    }

    /**
     * @param other this or other
     */
    public void or(BitMap other) {
        int size =  min(bits.length, other.bits.length);
        for (int i = 0; i < size; i++) {
            bits[i] |= other.bits[i];
        }
    }

    private void ensureCapacity(int requiredIndex) {
        if (bits.length < requiredIndex) {
            int newLength = power(requiredIndex + 1);
            bits = Arrays.copyOf(bits, newLength);
        }
    }

    private int wordIndex(int index) {
        return index >> SHIFT_ADDRESS;
    }


    private static int power(int value) {
        int n = value - 1;
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        return n + 1;
    }

    public static void main(String[] args) {
        BitMap bitMap = new BitMap();
        bitMap.set(512);
        BitMap bitMap2 = new BitMap();
        bitMap2.set(511);
        bitMap.or(bitMap2);
        System.out.println(bitMap.get(511));
        System.out.println(bitMap.get(512));
        System.out.println(bitMap.get(510));

        bitMap.clear();
        System.out.println(bitMap.get(511));
        System.out.println(bitMap.get(512));
        System.out.println(bitMap.get(510));
    }
}
