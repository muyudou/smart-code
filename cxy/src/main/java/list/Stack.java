package list;

public interface Stack<E> {
    void push(E e);

    E pop();

    boolean isEmpty();

    int size();

    default boolean isNotEmpty() {
        return !isEmpty();
    }
}
