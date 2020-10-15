package datastruct.btree;

import java.io.Serializable;

/**
 *
 * @param <E>
 */
public class Page<E extends Comparable<E>> implements Serializable {
    // 算法导论中定义的节点的最小度
    private int t;

    // key size
    private int n;

    private Object[] keys;
    private Object[] children;

    public Page(int t) {
        int m = 2 * t;
        this.keys = new Object[m - 1];
        this.children = new Object[m];
    }

    public boolean isFull() {
        return n == 2 * t - 1;
    }



}
