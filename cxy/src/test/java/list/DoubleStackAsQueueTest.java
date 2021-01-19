package list;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DoubleStackAsQueueTest {
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCorrect() {
        DoubleStackAsQueue<Integer> queue = new DoubleStackAsQueue<>(16);
        queue.put(1);
        assertEquals(1, queue.peek().intValue());
        assertEquals(1, queue.size());
        assertEquals(1, queue.take().intValue());
        assertEquals(0, queue.size());

        for (int i = 0; i < 10; i++) {
            queue.put(i);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(i, queue.take().intValue());
        }

        for (int i = 0; i < 10; i++) {
            queue.put(i);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(i, queue.take().intValue());
        }

        assertTrue(queue.isEmpty());
    }
}
