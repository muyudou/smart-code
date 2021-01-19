package list;

/**
 * 题目： 使用两个栈来实现队列
 * 解题思路一： 栈1作为插入栈，栈2作为获取栈
 *  1) 插入时使用栈1
 *  2) 当获取元素时, 首先判断栈2是否为空, 如果不为空，则返回栈顶元素；如果为空，将栈1的元素使用pop推入到栈2，这样的话栈2就是当时的插入顺序
 *  空间复杂度: O(2N), 时间复杂度O(N)，会增加一次栈1到栈2的转换
 *  not thread safe
 *
 */
public class DoubleStackAsQueue<E> implements Queue<E> {
    private AStack<E> s1;
    private AStack<E> s2;

    public DoubleStackAsQueue(int capacity) {
        s1 = new AStack(capacity);
        s2 = new AStack<>(capacity);
    }

    @Override
    public E put(E e) {
        s1.push(e);
        return e;
    }

    @Override
    public E take() {
        prepareForTake();
        return s2.pop();
    }

    private void prepareForTake() {
        if (s2.isNotEmpty()) {
            return;
        }

        while (s1.isNotEmpty()) {
            s2.push(s1.pop());
        }
    }

    @Override
    public E peek() {
        prepareForTake();
        return s2.peek();
    }

    @Override
    public int size() {
        return s1.size() + s2.size();
    }
}
