package datastruct.array;

public abstract class Index {
    public final int idx;

    Index(int idx) {
        this.idx = idx;
    }

    public static Index exist(int index) {
        return new Find(index);
    }

    public static Index not(int index) {
        return new NotFind(index);
    }

    public int index() {
        return idx;
    }

    public abstract boolean isExist();

    @Override
    public String toString() {
        return isExist() + ": " + idx;
    }

    private static class Find extends Index {
        public Find(int index) {
          super(index);
        }

        @Override
        public boolean isExist() {
            return true;
        }
    }

    private static class NotFind extends Index {
        public NotFind(int index) {
            super(index);
        }

        @Override
        public boolean isExist() {
            return false;
        }
    }
}
