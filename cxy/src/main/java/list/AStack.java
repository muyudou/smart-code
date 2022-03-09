package list;

import java.io.Serializable;

public class AStack<E> implements Stack<E>, Serializable {
    private Object[] data;
    private int sp;

    public AStack() {
        this(16);
    }

    public AStack(int capacity) {
        data = new Object[capacity];
    }

    public void push(E e) {
        ensureSize();
        data[sp++] = e;
    }

    private void ensureSize() {
        if (sp >= data.length) {
            if (sp >= Integer.MAX_VALUE - 10) {
                throw new ArrayIndexOutOfBoundsException("栈溢出，超出最大可用值");
            }

            int size = Integer.min(sp * 2, Integer.MAX_VALUE - 10);
            Object[] newData = new Object[size];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
    }

    public E pop() {
        if (sp <= 0) {
            throw new ArrayIndexOutOfBoundsException("stack is empty.");
        }
        return (E) data[--sp];
    }

    /**
     * 获取栈顶元素但是不删除
     * @return
     */
    public E peek() {
        if (sp <= 0) {
            throw new ArrayIndexOutOfBoundsException("stack is empty.");
        }

        return (E) data[sp - 1];
    }

    public boolean isEmpty() {
        return sp == 0;
    }

    @Override
    public int size() {
        return sp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = sp; i > 0; i--) {
            sb.append(data[i - 1]).append(",");
        }
        return sb.append("]").toString();
    }
}
