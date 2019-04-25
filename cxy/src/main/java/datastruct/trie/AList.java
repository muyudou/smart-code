package datastruct.trie;

import java.util.ArrayList;

public class AList<E> extends ArrayList<E> {
    public E first() {
        return this.get(0);
    }

    public E last() {
        return this.get(size() - 1);
    }

    public boolean isNotEmpty() {
        return ! this.isEmpty();
    }
}
