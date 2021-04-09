package datastruct.trie;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static datastruct.trie.Preconditions.checkState;
import static java.lang.Math.max;

/**
 * 基于论文《An Efficient Implementation of the Trie Structures》
 */
public class DoubleArrayTrie {
    private final static int BUF_SIZE = 16384;
    private final static int UNIT_SIZE = 8; // size of int + int

    private static class Node {
        int code;
        int depth;
        int left;
        int right;
    };

    private int check[];
    private int base[];

    private boolean used[];
    private int size;
    private int allocSize;
    private List<String> keys;
    private int keySize;
    private int length[];
    private int value[];
    private int progress;
    private int nextCheckPos;

    // int (*progressfunc_) (size_t, size_t);

    // inline _resize expanded
    private int resize(int newSize) {
        int[] base2 = new int[newSize];
        int[] check2 = new int[newSize];
        boolean used2[] = new boolean[newSize];
        if (allocSize > 0) {
            System.arraycopy(base, 0, base2, 0, allocSize);
            System.arraycopy(check, 0, check2, 0, allocSize);
            System.arraycopy(used2, 0, used2, 0, allocSize);
        }

        base = base2;
        check = check2;
        used = used2;

        return allocSize = newSize;
    }

    private AList<Node> fetch(Node parent) {
        AList<Node> siblings = new AList<>();
        int prev = 0;

        for (int keyIdx = parent.left; keyIdx < parent.right; keyIdx++) {
            // key已经处理结束了
            if (getKeyLength(keyIdx) < parent.depth)
                continue;

            int cur = getDepthCodeOfKey(parent.depth, keyIdx);
            checkState(prev <= cur, "要求插入的key是有序的，但是prev > cur");

            //                    补0
            if (cur != prev || siblings.isEmpty()) {
                setKeyIdxAsPrevRight(siblings, keyIdx);

                Node tmp_node = new Node();
                tmp_node.depth = parent.depth + 1;
                tmp_node.code = cur;
                tmp_node.left = keyIdx;
                siblings.add(tmp_node);
            }

            prev = cur;
        }
        setKeyIdxAsPrevRight(siblings, parent.right);

        return siblings;
    }

    private int getKeyLength(int keyIdx) {
        return length != null ? length[keyIdx] : keys.get(keyIdx).length();
    }

    /**
     * 如果key length == depth, 添加一个0节点作为结束的标志
     * @param depth
     * @param keyIdx
     * @return
     */
    private int getDepthCodeOfKey(int depth, int keyIdx) {
        return getKeyLength(keyIdx) != depth ? keys.get(keyIdx).charAt(depth) + 1 : 0;
    }

    private void setKeyIdxAsPrevRight(AList<Node> siblings, int keyIdx) {
        if (siblings.isNotEmpty()) {
            siblings.last().right = keyIdx;
        }
    }

    private int insert(AList<Node> siblings) {
        int begin = 0;
        // 为什么+1?它要转移的下一个状态m=base[n]+code，所以m>=code+1; 为什么-1? 是因为while循环中的pos++吗?
        int pos = max(siblings.first().code + 1, nextCheckPos) - 1;
        int nonzero_num = 0;
        boolean first = true;

        if (allocSize <= pos)
            // resize只+1会不会太小了？不过这种代码不可能犯这么低性能的错误
            resize(pos + 1);

        // 找到可用的begin
        outer: while (true) {
            pos++;

            if (allocSize <= pos)
                resize(pos + 1);

            if (check[pos] != 0) {
                nonzero_num++;
                continue;
            } else if (first) {
                nextCheckPos = pos;
                first = false;
            }

            begin = pos - siblings.first().code;
            resizeProgress(begin + siblings.last().code);
            if (used[begin])
                continue;

            // 找到了可用的begin, 满足所有的都可以插入
            for (int i = 1; i < siblings.size(); i++)
                if (check[begin + siblings.get(i).code] != 0)
                    continue outer;

            break;
        }

        // -- Simple heuristics --
        // if the percentage of non-empty contents in check between the
        // index
        // 'next_check_pos' and 'check' is greater than some constant value
        // (e.g. 0.9),
        // new 'next_check_pos' index is written by 'check'.
        if (1.0 * nonzero_num / (pos - nextCheckPos + 1) >= 0.95)
            nextCheckPos = pos;

        used[begin] = true;
        size = max(size, begin + siblings.last().code + 1);

        for (int i = 0; i < siblings.size(); i++)
            check[begin + siblings.get(i).code] = begin;

        for (int i = 0; i < siblings.size(); i++) {
            AList<Node> childSiblings = fetch(siblings.get(i));

            if (childSiblings.isEmpty()) {
                base[begin + siblings.get(i).code] = (value != null) ? (-value[siblings.get(i).left] - 1) : (-siblings.get(i).left - 1);

                checkState(value == null || (-value[siblings.get(i).left] - 1) < 0, "状态出错");
                progress++;
            } else {
                int h = insert(childSiblings);
                base[begin + siblings.get(i).code] = h;
            }
        }
        return begin;
    }

    private void resizeProgress(int required) {
        if (allocSize <= required) {
            // progress can be zero
            double l = max(1.05, 1.0 * keySize / (progress + 1));
            resize((int) (allocSize * l));
        }
    }


    public DoubleArrayTrie() {
        check = null;
        base = null;
        used = null;
        size = 0;
        allocSize = 0;
    }

    // no deconstructor

    // set_result omitted
    // the search methods returns (the list of) the value(s) instead
    // of (the list of) the pair(s) of value(s) and length(s)

    // set_array omitted
    // datastruct.array omitted

    void clear() {
        // if (! no_delete_)
        check = null;
        base = null;
        used = null;
        allocSize = 0;
        size = 0;
        // no_delete_ = false;
    }

    public int getUnitSize() {
        return UNIT_SIZE;
    }

    public int getSize() {
        return size;
    }

    public int getTotalSize() {
        return size * UNIT_SIZE;
    }

    public int getNonzeroSize() {
        int result = 0;
        for (int i = 0; i < size; i++)
            if (check[i] != 0)
                result++;
        return result;
    }

    public void build(List<String> key) {
        build(key, null, null, key.size());
    }

    public void build(List<String> _key, int _length[], int _value[], int _keySize) {
        checkState(_key != null && _key.size() >= _keySize, "keys is null, or keys is less then _keySize");

        keys = _key;
        length = _length;
        keySize = _keySize;
        value = _value;
        progress = 0;

        resize(65536 * 32);

        base[0] = 1;
        nextCheckPos = 0;

        Node root_node = new Node();
        root_node.left = 0;
        root_node.right = keySize;
        root_node.depth = 0;

        AList<Node> siblings = fetch(root_node);
        insert(siblings);

        used = null;
        keys = null;
    }

    public void open(String fileName) throws IOException {
        File file = new File(fileName);
        size = (int) file.length() / UNIT_SIZE;
        check = new int[size];
        base = new int[size];

        DataInputStream is = null;
        try {
            is = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(file), BUF_SIZE));
            for (int i = 0; i < size; i++) {
                base[i] = is.readInt();
                check[i] = is.readInt();
            }
        } finally {
            if (is != null)
                is.close();
        }
    }

    public void save(String fileName) throws IOException {
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(new BufferedOutputStream(
                    new FileOutputStream(fileName)));
            for (int i = 0; i < size; i++) {
                out.writeInt(base[i]);
                out.writeInt(check[i]);
            }
            out.close();
        } finally {
            if (out != null)
                out.close();
        }
    }

    public int exactMatchSearch(String key) {
        return exactMatchSearch(key, 0, 0, 0);
    }

    public int exactMatchSearch(String key, int pos, int len, int nodePos) {
        if (len <= 0)
            len = key.length();
        if (nodePos <= 0)
            nodePos = 0;

        int result = -1;

        char[] keyChars = key.toCharArray();

        int b = base[nodePos];
        int p;

        for (int i = pos; i < len; i++) {
            p = b + (int) (keyChars[i]) + 1;
            if (b == check[p])
                b = base[p];
            else
                return result;
        }

        p = b;
        int n = base[p];
        if (b == check[p] && n < 0) {
            result = -n - 1;
        }
        return result;
    }

    public List<Integer> commonPrefixSearch(String key) {
        return commonPrefixSearch(key, 0, 0, 0);
    }

    public List<Integer> commonPrefixSearch(String key, int pos, int len,
                                            int nodePos) {
        if (len <= 0)
            len = key.length();
        if (nodePos <= 0)
            nodePos = 0;

        List<Integer> result = new ArrayList<Integer>();

        char[] keyChars = key.toCharArray();

        int b = base[nodePos];
        int n;
        int p;

        for (int i = pos; i < len; i++) {
            p = b;
            n = base[p];

            if (b == check[p] && n < 0) {
                result.add(-n - 1);
            }

            p = b + (int) (keyChars[i]) + 1;
            if (b == check[p])
                b = base[p];
            else
                return result;
        }

        p = b;
        n = base[p];

        if (b == check[p] && n < 0) {
            result.add(-n - 1);
        }

        return result;
    }

    // debug
    public void dump() {
        for (int i = 0; i < size; i++) {
            System.err.println("i: " + i + " [" + base[i] + ", " + check[i]
                    + "]");
        }
    }



    public static void main(String[] args) {
        DoubleArrayTrie dat = new DoubleArrayTrie();
        List<String> keys = Arrays.asList("baby", "babyinthecar", "aa", "aaa");
        keys.sort(Comparator.naturalOrder());
        dat.build(keys);
        System.out.println(dat.exactMatchSearch("aaa"));
        System.out.println(dat.exactMatchSearch("aa"));
        System.out.println(dat.exactMatchSearch("baby"));
        System.out.println(dat.exactMatchSearch("babyinthecar"));
        System.out.println(dat.exactMatchSearch("a"));
    }
}
