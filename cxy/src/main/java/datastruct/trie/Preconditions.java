package datastruct.trie;

public class Preconditions {
    public static void checkState(boolean state, String message) {
        if (! state) {
            throw new IllegalStateException(message);
        }
    }
}
