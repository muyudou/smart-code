package list;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class LStackTest {

    @Test
    public void testBasic() {
        LStack<Integer> stack = new LStack<>();
        Integer v = 1;
        stack.push(v);
        assertEquals(v, stack.peek());
        assertEquals(v, stack.peek());
        assertFalse(stack.isEmpty());
        assertEquals(v, stack.pop());
        assertTrue(stack.isEmpty());

        Integer v2 = 2;
        stack.push(v2);
        stack.push(3);
        assertEquals((Integer)3, stack.pop());
        assertEquals(v2, stack.pop());
        assertTrue(stack.isEmpty());
    }
}
