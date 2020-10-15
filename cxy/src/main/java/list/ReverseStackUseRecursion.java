package list;

/**
 * 仅使用递归和栈操作来反转一个栈
 */
public class ReverseStackUseRecursion {

    public static void main(String[] args) {
        Stack<Integer> stack = new AStack<>(10);
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        reverse(stack);

        while (stack.isNotEmpty()) {
            System.out.println(stack.pop());
        }
    }

    /**
     *
     */
    public static <E> void reverse(Stack<E> stack) {
        if (stack.isEmpty()) {
            return;
        }

        E e = stack.pop();
        reverse(stack);
        stack.push(e);
    }
}
