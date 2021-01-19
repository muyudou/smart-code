package list;

/**
 * 队列, 先进先出
 * @param <T>
 */
public interface Queue<T> {
    T put(T t);

    /**
     * 取走队头元素并删除
     */
    T take();

    /**
     * 获取队头元素，不删除
     */
    T peek();

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    default boolean isNotEmpty() {
        return !isEmpty();
    }

}
