package stack;

import list.AStack;

/**
 * 按照从大到小对栈进行排序, 可以额外申请另一个栈和局部变量，不能申请更多的空间。
 *
 * 思路：
 * 1. 栈用stack表示，申请一个help栈
 * 2. 只要help栈是按照从小到大排序，然后再把help栈压入到stack中，则stack排序完成
 */
public class SortStackUseOneStack {

    public static void sort(AStack<Integer> stack) {
        AStack<Integer> help = new AStack<>(stack.size());
        while (stack.isNotEmpty()) {
            Integer current = stack.pop();
            while (help.isNotEmpty() && current > help.peek()) {
                stack.push(help.pop());
            }
            // 此时 help 中的元素都大于等于 current
            help.push(current);
        }

        while (help.isNotEmpty()) {
            stack.push(help.pop());
        }
    }

    public static void main(String[] args) {
        AStack<Integer> stack = new AStack<>();
        for (int value : new int[] {1, 8, 5, 1, 9, 2, 2, 0, 0, 8, 2}) {
            stack.push(value);
        }
        sort(stack);
        System.out.println(stack.toString());
    }

}
