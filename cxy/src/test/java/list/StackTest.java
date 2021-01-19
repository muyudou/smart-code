package list;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StackTest {

    @Test
    public void testBasic() {
        AStack<Integer> stack = new AStack<>(1);
        Integer v = 1;
        stack.push(v);
        assertEquals(v, stack.peek());
        assertEquals(v, stack.peek());
        assertFalse(stack.isEmpty());
        assertEquals(v, stack.pop());
        assertTrue(stack.isEmpty());
    }
}
