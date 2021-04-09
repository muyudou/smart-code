package datastruct.array;

public class FindIndex {
    public final int idx;
    public final boolean isFind;

    public FindIndex(int index, boolean isFind) {
        this.idx = index;
        this.isFind = isFind;
    }

    @Override
    public String toString() {
        return isFind + ": " + idx;
    }
}
