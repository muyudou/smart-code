package datastruct.trie;

import java.util.ArrayDeque;
import java.util.Queue;

public class TrieST<V> {
    private static int R = 256;
    private Node root;

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    public V get(String key) {
        Node value = get(root, key, 0);
        if (value == null) return null;
        return (V) value.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        int c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    public void put(String key, V value) {
        root = put(root, key, value, 0);
    }

    private Node put(Node x, String key, V value, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = value;
            return x;
        }

        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, value, d + 1);
        return x;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        int cnt = 0;
        if (x.val != null) cnt++;
        for (char c = 0; c < R; c++) {
            cnt += size(x.next[c]);
        }
        return cnt;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new ArrayDeque<>();
        collect(get(root, pre, 0), pre, q);
        return q;
    }

    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) return;
        if (x.val != null) {
            q.add(pre);
        }
        for (char c = 0; c < R; c++) {
            collect(x.next[c], pre + c, q);
        }
    }
}
