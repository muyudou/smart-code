package datastruct.btree;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PageTest {
//    @Test
//    public void testBasic() {
//        Page<Integer> page = new Page<>(2);
//        assertTrue(page.isLeaf());
//        assertFalse(page.isFull());
//        for (int i = 0; i < 3; i++) {
//            // page.insertToLeaf(i, i);
//        }
//        assertTrue(page.isFull());
//    }
//
//    @Test
//    public void testInsert() {
//        Page<Integer> page = new Page<>(10);
//        int[] eles = {1, 8, 5, 9, 2, 0};
//        for (int e : eles) {
//            page.insertToLeaf(e);
//        }
//        assertEquals(6, page.n);
//
//        int[] sorted = {0, 1, 2, 5, 8, 9};
//        for (int i = 0; i < page.n; i++) {
//            assertEquals(sorted[i], page.keys[i]);
//        }
//
//        page.insertToLeaf(4, 7);
//        sorted = new int[]{0, 1, 2, 5, 7, 8, 9};
//        for (int i = 0; i < page.n; i++) {
//            assertEquals(sorted[i], page.keys[i]);
//        }
//    }
//
//    @Test
//    public void testTruncate() {
//        Page<Integer> page = new Page<>(4);
//        int[] eles = {1, 8, 5, 9, 2, 0, 7};
//        for (int e : eles) {
//            page.insertToLeaf(e);
//        }
//        assertTrue(page.isFull());
//        Page<Integer>[] children = new Page[8];
//        for (int i = 0; i < 8; i++) {
//            children[i] = new Page<>(4);
//            page.setChild(i, children[i]);
//        }
//
//        page.truncate(3);
//        assertTrue(3 == page.n);
//        assertEquals(0, page.keys[0]);
//        assertEquals(1, page.keys[1]);
//        assertEquals(2, page.keys[2]);
//        for (int i = page.n; i < 7; i++) {
//            assertNull(page.keys[i]);
//        }
//
//        for (int i = 0; i < page.n; i++) {
//            assertTrue(page.children[i] ==  children[i]);
//        }
//
//        for (int i = page.n + 1; i < 8; i++) {
//            assertNull(page.children[i]);
//        }
//    }
//
//    @Test
//    public void testUpdate() {
//        Page<Integer> page = new Page<>(8);
//        for (int i = 0; i < 10; i++) {
//            page.keys[i] = i;
//        }
//        page.update(1, 100);
//        assertEquals(100, page.keys[1]);
//        page.update(2, 200);
//        assertEquals(200, page.keys[2]);
//    }
//
//    @Test
//    public void testSplit() {
//        Page<Integer> page = newPage(4, new int[]{1, 8, 5, 9, 2, 0, 7});
//        assertTrue(page.isFull());
//
//        Page<Integer> left = newPage(4, new int[] {-5, -4});
//        Page<Integer> right = newPage(4, new int[]{20, 25});
//        Page<Integer> parent = newPage(4, new int[]{-3, 16});
//        parent.setChild(0, left);
//        parent.setChild(1, page);
//        parent.setChild(2, right);
//
//        page.split(parent, 1);
//        assertEquals(3, page.n);
//        assertEquals(0, page.keys[0]);
//        assertEquals(1, page.keys[1]);
//        assertEquals(2, page.keys[2]);
//
//        assertEquals(3, parent.n);
//        assertEquals(-3, parent.keys[0]);
//        assertEquals(5, parent.keys[1]);
//        assertEquals(16, parent.keys[2]);
//
//        assertEquals(left, parent.children[0]);
//        assertEquals(page, parent.children[1]);
//        assertEquals(right, parent.children[3]);
//
//        Page<Integer> newOne = parent.children[2];
//        assertEquals(3, newOne.n);
//        assertEquals(7, newOne.keys[0]);
//        assertEquals(8, newOne.keys[1]);
//        assertEquals(9, newOne.keys[2]);
//    }
//
//    public Page<Integer> newPage(int t, int[] eles) {
//        Page<Integer> page = new Page<>(t);
//        for (int e : eles) {
//            page.insertToLeaf(e);
//        }
//        return page;
//    }
}
