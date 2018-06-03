package array;

/**
 * 一个int型数组，除了一个元素外，其余的都出现了两次
 *
 * 思路: 利用x^x=0的特性，把元素所有的数据都^一下，最后的结果就是那个元素
 * 这种方法也就在整数型的有用
 *
 * @author caoxiaoyong
 * @date   2018-06-03
 */
public class SingleNumber {
    public int singleNumber(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }
        int target = 0;
        for (int n : numbers) {
            target = target ^ n;
        }
        return target;
    }
}
