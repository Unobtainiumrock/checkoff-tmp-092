import java.util.*;
import java.util.stream.Collectors;

//PriorityQueue<>(this.vertexCount,Comparator.comparingInt(v-><length of the shortest path from start to v>))


// 1)

// 2) Hint: At a certain point in Dijkstra’s algorithm, you have to change the value of nodes in the fringe.
// Java’s PriorityQueue does not support this operation directly, but we can add a new entry into the PriorityQueue
// that contains the updated value (and will always be dequeued before any previous entries). Then, by tracking which
// nodes have been visited already, you can simply ignore any copies after the first copy dequeued.

public class Sandbox {
    public static void main(String[] args) {
        Queue<Node> pq = new PriorityQueue<>(Comparator.comparing((node) -> {
            return node.getdistToSrc();
        }));

        Random r = new Random(1337);

        //        for (int i = 0; i < 10; i++) {
        //            pq.add(new Node(i, i + 1));
        //        }

        for (int i = 0; i < 10; i++) {
            pq.add(new Node(i, r.nextInt(10)));
        }

        System.out.println("Before sorting");

        //        while (!pq.isEmpty()) {
        //            System.out.println(pq.poll());
        //        }
        pq.forEach(System.out::println);

        System.out.println("Sorting..");

        pq.stream().sorted().collect(Collectors.toList()).forEach(System.out::println);

        Queue<Node> pq2 = new PriorityQueue<>(Comparator.comparing((node) -> {
            return node.getdistToSrc();
        }));

        System.out.println("Testing editing elements in the pq");

        for (int i = 0; i < 10; i++) {
            pq2.add(new Node(i, r.nextInt(10)));
        }

        pq2.forEach(System.out::println);

        Set<HashMap<Boolean, Node>> visited = new HashSet<>();

        // 1) create a new node
        // 2) add to fringe pq
        // 3) pop off
        // 4) use this popped off thing to go into the set

        // therefore, every time we are doing a poll(), we will have a O(1) lookup on the hashset to see if something has been visited
        // already, and if they have, then we can ignore that particular value poll()'d.

    }

    private static class Node implements Comparable<Node> {
        private int nodeVal;
        private int distToSrc;

        Node(int nodeVal, int distToSrc) {
            this.nodeVal = nodeVal;
            this.distToSrc = distToSrc;
        }

        public int getNodeVal() {
            return this.nodeVal;
        }

        public int getdistToSrc() {
            return this.distToSrc;
        }

        @Override
        public String toString() {
            return "Node val: " + this.nodeVal
                    + " Dist to src: " + this.distToSrc;
        }

        @Override
        public int compareTo(Node node) {
            return Integer.compare(this.distToSrc, node.getdistToSrc());
        }
    }
}

