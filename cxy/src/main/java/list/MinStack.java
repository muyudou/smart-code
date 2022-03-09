package list;

/**
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * 实现 MinStack 类:
 *
 * MinStack() 初始化堆栈对象。
 * void push(int val) 将元素val推入堆栈。
 * void pop() 删除堆栈顶部的元素。
 * int top() 获取堆栈顶部的元素。
 * int getMin() 获取堆栈中的最小元素。
 *
 * 示例 1:
 *
 * 输入：
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 *
 * 输出：
 * [null,null,null,null,-3,null,0,-2]
 *
 * 解释：
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> 返回 -3.
 * minStack.pop();
 * minStack.top();      --> 返回 0.
 * minStack.getMin();   --> 返回 -2.
 *  
 *
 * 提示：
 *
 * -231 <= val <= 231 - 1
 * pop、top 和 getMin 操作总是在 非空栈 上调用
 * push, pop, top, and getMin最多被调用 3 * 104 次
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/min-stack
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 1. 设计思路
 * 使用O(n)的空间复杂度，保存每一步时的最小值
 *
 */
public class MinStack {
    private int point;

    private int[] data;

    /**
     * 保存每一步对应的最小值
     */
    private int[] mindata;

    public MinStack() {
        // 基于最多调用30000次
        data = new int[30000];
        mindata = new int[30000];
    }

    public void push(int val) {
        if (isEmpty()) {
            data[point] = val;
            mindata[point] = val;
        } else {
            int min = Integer.min(val, mindata[point - 1]);
            data[point] = val;
            mindata[point] = min;
        }
        point++;
    }

    public boolean isEmpty() {
        return point == 0;
    }

    public void pop() {
        point--;
    }

    public int top() {
        return data[point - 1];
    }

    public int getMin() {
        return mindata[point - 1];
    }

    /**
     * ["MinStack","push","push","push","getMin","pop","top","getMin"]
     * [[],[-2],[0],[-3],[],[],[],[]]
     * @param args
     */
    public static void main(String[] args) {
        MinStack stack = new MinStack();
        stack.push(-2);
        stack.push(0);
        stack.push(-3);
        System.out.println(stack.getMin());
        stack.pop();
        System.out.println(stack.top());
        System.out.println(stack.getMin());
    }

}
