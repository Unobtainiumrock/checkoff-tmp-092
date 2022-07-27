import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class MinHeapTest {
    private Random r;
    private List<Integer> l;
    private MinHeap<Integer> heap;

    @Test
    public void insertRemoveTest() {
        setStuff(123);

        System.out.println();
        System.out.println("Expect to remove items from the min heap in ascending order");
        System.out.println();

        for (Integer i : l) {
            Integer next = heap.removeMin();
            System.out.println("Expected: " + i + " " + "Actual: " + next);
            assertEquals(i, next);
        }

    }

    @Test
    public void containsTest() {
        setStuff(69);

        System.out.println();
        System.out.println("Expect contains to yield true for all elements that exist in heap");
        System.out.println();


        for (Integer i : l) {
            System.out.println("Heap contains: " + i + "?" + " Expected: true " + "Actual: " + heap.contains(i));
            assertTrue(heap.contains(i));
        }

        Integer a = heap.removeMin();
        Integer b = heap.removeMin();
        System.out.println("Remove two elements and expect them to no longer exist in the heap");
        System.out.println("Removing: " + a);
        System.out.println("Removing: " + b);
        System.out.println();
        System.out.println("Heap contains " + a + "? " + heap.contains(a));
        System.out.println("Heap contains " + b + "? " + heap.contains(b));

    }

//    @Test
//    public void briefBubbleDownTest() {
//        this.l = new ArrayList<>();
//        this.heap = new MinHeap<>();
//
//        for (int i = 0; i < 10; i++) {
//            System.out.println("Adding: " + i + " to the min heap.");
//            this.heap.insert(i);
//            this.l.add(i);
//        }
//
//        heap.add(6)
//
//
//        System.out.println();
//        System.out.println("Expect a large value to bubble waaay down ");
//        System.out.println();
//
//        for (Integer i : ) {
//
//        }

//    }

    private void setStuff(int seed) {
        this.r = new Random(123);
        this.l = new ArrayList<>();
        this.heap = new MinHeap<>();

        for (int i = 0; i < 10; i++) {
            Integer rand = r.nextInt(10);
            System.out.println("Adding: " + rand + " to the min heap.");
            while (this.heap.contains(rand)) {
                rand = r.nextInt(10);
            }
            this.heap.insert(rand);
            this.l.add(rand);
        }

        Collections.sort(this.l);
    }

}
