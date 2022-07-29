import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapPQTest {

    @Test
    public void test1() {
        PriorityQueue<String> pq = new MinHeapPQ<>();
        pq.insert("Hello", 1);
        pq.poll();
        pq.insert("World", 2);
        pq.poll();
        pq.insert("Wo", 3);
        pq.poll();
        pq.insert("ai", 4);
        pq.poll();
        pq.insert("ne", 5);
        pq.poll();
        pq.insert("Goodbye", 6);
        pq.poll();


        pq.insert("Hello", 2);
        pq.insert("World", 3);

        pq.changePriority("World", 1);
        assertTrue(pq.peek().equals("World"));

    }
}
