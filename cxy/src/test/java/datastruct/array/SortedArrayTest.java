package datastruct.array;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SortedArrayTest {
    @Test
    public void testBasic() {
        SortedUniqArray<String> array = new SortedUniqArray<>(8);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
        assertFalse(array.isNotEmpty());
        assertFalse(array.isFull());

        array.insert("a");
        assertEquals(1, array.size());
        assertFalse(array.isEmpty());
        assertTrue(array.isNotEmpty());
        assertFalse(array.isFull());

        array.insert("a");
        assertEquals(1, array.size());
        assertFalse(array.isEmpty());
        assertTrue(array.isNotEmpty());
        assertFalse(array.isFull());


        for (int i = 0; i < 7; i++) {
            array.insert("a" + i);
        }
        assertEquals(8, array.size());
        assertFalse(array.isEmpty());
        assertTrue(array.isNotEmpty());
        assertTrue(array.isFull());

        try {
            array.insert("b");
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            // do nothing
        }
    }

    @Test
    public void testInsertAt() {
        SortedUniqArray<Integer> array = new SortedUniqArray<>(8);
        int[] values = {1, 8, 5, 1, 9, 2, 2, 0, 0, 8, 2};
        for (int v : values) {
            array.insert(v);
        }
        int[] expected = {0, 1, 2, 5, 8, 9};
        equals(expected, array);

        array.insertAt(3, 4);
        array.insertAt(3, 3);
        expected = new int[] {0, 1, 2, 3, 4, 5, 8, 9};
        equals(expected, array);
    }

    @Test
    public void testRemove() {
        SortedUniqArray<Integer> array = new SortedUniqArray<>(8);
        int[] values = {1, 8, 5, 1, 9, 2, 2, 0, 0, 8, 2, 3, 4};
        for (int v : values) {
            array.insert(v);
        }
        equals(new int[]{0, 1, 2, 3, 4, 5, 8, 9}, array);
        array.removeByIndex(1);
        equals(new int[]{0, 2, 3, 4, 5, 8, 9}, array);

        array.removeByIndex(1);
        equals(new int[]{0, 3, 4, 5, 8, 9}, array);

        array.removeByIndex(2);
        equals(new int[]{0, 3, 5, 8, 9}, array);

        array.removeByIndex(4);
        equals(new int[]{0, 3, 5, 8}, array);

        array.remove(new Integer(5));
        equals(new int[]{0, 3, 8}, array);
    }

    @Test
    public void testTruncate() {
        SortedUniqArray<Integer> array = new SortedUniqArray<>(8);
        int[] values = {1, 8, 5, 1, 9, 2, 2, 0, 0, 8, 2, 3, 4};
        for (int v : values) {
            array.insert(v);
        }
        equals(new int[]{0, 1, 2, 3, 4, 5, 8, 9}, array);

        array.truncate(4);
        equals(new int[]{0, 1, 2, 3}, array);

        array.truncate(3);
        equals(new int[]{0, 1, 2}, array);

        array.truncate(2);
        equals(new int[]{0, 1}, array);

        array.truncate(1);
        equals(new int[]{0}, array);

        array.truncate(0);
        assertTrue(array.isEmpty());
    }

    private void equals(int[] expected, SortedUniqArray<Integer> array) {
        assertEquals(expected.length, array.size());
        for (int i = 0; i < array.size(); i++) {
            assertEquals(expected[i], array.get(i).intValue());
        }
    }

}
