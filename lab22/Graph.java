
import java.util.*;
import java.util.stream.Collectors;
// Hello
public class Graph implements Iterable<Integer> {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    public static void main(String[] args) {
        Graph g1 = new Graph(5);
        g1.generateG1();
        g1.printDFS(0);
        g1.printDFS(2);
        g1.printDFS(3);
        g1.printDFS(4);

        g1.printPath(0, 3); // good
        g1.printPath(0, 4); // good
        g1.printPath(1, 3); // good
        g1.printPath(1, 4); // good
        g1.printPath(4, 0); // good

        Graph g2 = new Graph(5);
        g2.generateG2();
        g2.printTopologicalSort();

        // Tree case
        Graph g3 = new Graph(7);
        g3.addEdge(0, 1);
        g3.addEdge(0, 2);
        g3.addEdge(1, 3);
        g3.addEdge(1, 4);
        g3.addEdge(2, 5);
        g3.addEdge(2, 6);
        g3.printTopologicalSort();

        //A-H <=> 0-7 case
        Graph g4 = new Graph(8);
        g4.addEdge(0, 2);
        g4.addEdge(0, 3);
        g4.addEdge(1, 3);
        g4.addEdge(1, 4);
        g4.addEdge(2, 5);
        g4.addEdge(3, 5);
        g4.addEdge(4, 6);
        g4.addEdge(5, 7);
        g4.addEdge(6, 7);
        g4.printTopologicalSort();


    }

    /* Adds a directed Edge (V1, V2) to the graph. That is, adds an edge
       in ONE directions, from v1 to v2. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. That is, adds an edge
       in BOTH directions, from v1 to v2 and from v2 to v1. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        if (isAdjacent(v1, v2)) {
//            adjLists[v1].get(v2).weight = weight;
            adjLists[v1].forEach((edge) -> {
                if (edge.equals(new Edge(v1, v2, weight))) {
                    edge.weight = weight;
                }
            });
        } else {
            adjLists[v1].add(new Edge(v1, v2, weight));
        }
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        addEdge(v1, v2);
        addEdge(v2, v1);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        return neighbors(from).contains(to);
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        return adjLists[v].stream()
                .map((edgeLL) -> edgeLL.to)
                .collect(Collectors.toList());
    }

    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        int res = 0;
        for (int i = 0; i < adjLists.length; i++) {
            for (int j = 0; j < adjLists[i].size(); j++) {
                if (adjLists[i].get(j).to == v) {
                    res++;
                }
            }
        }
        return res;
    }

    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /* Returns the collected result of performing a depth-first search on this
       graph's vertices starting from V. */
    public List<Integer> dfs(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);

        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }


    /* Returns true iff there exists a path from START to STOP. Assumes both
       START and STOP are in this graph. If START == STOP, returns true. */
    public boolean pathExists(int start, int stop) {
        return dfs(start).contains(stop);
    }


    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {
        LinkedList<Integer> p = new LinkedList<>();
        Iterator<Integer> iter = new DFSIterator(start);

        if (start == stop) {
            p.add(start);
            return p;
        }

        // Build path until hitting either stop, or a vertex with no neighbors.
        while (iter.hasNext()) {
            p.add(iter.next());

            if (p.peekLast() == stop) {
                return p;
            }

            // Backtrack
            if (neighbors(p.peekLast()).size() == 0) {
                int curr = p.removeLast();

                while (!isAdjacent(curr, stop)) {
                    if (p.size() != 0) {
                        curr = p.removeLast();
                    } else {
                        return new LinkedList<>();
                    }

                }
                p.add(curr);
            }
        }
        return p;
    }

    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private void generateG1() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG2() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG3() {
        addUndirectedEdge(0, 2);
        addUndirectedEdge(0, 3);
        addUndirectedEdge(1, 4);
        addUndirectedEdge(1, 5);
        addUndirectedEdge(2, 3);
        addUndirectedEdge(2, 6);
        addUndirectedEdge(4, 5);
    }

    private void generateG4() {
        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 2);
    }

    private void printDFS(int start) {
        System.out.println("DFS traversal starting at " + start);
        List<Integer> result = dfs(start);
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printPath(int start, int end) {
        System.out.println("Path from " + start + " to " + end);
        List<Integer> result = path(start, end);
        if (result.size() == 0) {
            System.out.println("No path from " + start + " to " + end);
            return;
        }
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printTopologicalSort() {
        System.out.println("Topological sort");
        List<Integer> result = topologicalSort();
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    private static class Edge {
        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Edge) {
                Edge other = (Edge) o;
                return other.to == this.to && other.from == this.from;
            }
            return false;
        }

    }

    /**
     * A class that iterates through the vertices of this graph,
     * starting with a given vertex. Does not necessarily iterate
     * through all vertices in the graph: if the iteration starts
     * at a vertex v, and there is no path from v to a vertex w,
     * then the iteration will not include w.
     */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        public DFSIterator(Integer start) {
            fringe = new Stack<>();
            visited = new HashSet<>();
            fringe.push(start);
        }

        public boolean hasNext() {
            if (!fringe.isEmpty()) {
                int i = fringe.pop();
                while (visited.contains(i)) {
                    if (fringe.isEmpty()) {
                        return false;
                    }
                    i = fringe.pop();
                }
                fringe.push(i);
                return true;
            }
            return false;
        }

        public Integer next() {
            int curr = fringe.pop();
            ArrayList<Integer> lst = new ArrayList<>();
            for (int i : neighbors(curr)) {
                lst.add(i);
            }
            lst.sort((Integer i1, Integer i2) -> -(i1 - i2));
            for (Integer e : lst) {
                fringe.push(e);
            }
            visited.add(curr);
            return curr;
        }

        //ignore this method
        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }

    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private ArrayList<Integer> currentInDegree;

        TopologicalIterator() {
            fringe = new Stack<Integer>();
            currentInDegree = new ArrayList<>();

            for (int i = 0; i < vertexCount; i++) {
                currentInDegree.add(i, inDegree(i));
                if (currentInDegree.get(i) == 0) {
                    fringe.push(i);
                }
            }

        }

        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        public Integer next() {
            int curr = fringe.pop();
            for (int i : neighbors(curr)) {

                int updateMe = currentInDegree.get(i);
                currentInDegree.set(i, updateMe - 1);

                int el = currentInDegree.get(i);

                if (el == 0) {
                    fringe.push(i);
                }
            }
            return curr;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public List<Integer> shortestPath(int start, int stop) {
        Iterator<Integer> iter = new Dijkstras(start, stop);
        int curr = 0;
        while (iter.hasNext()) {
            curr = iter.next();
        }

        return Dijkstras.result(curr);
    }

    public Edge getEdge(int from, int to) {

        //return adjLists[from].get(adjLists[from].indexOf(to));
        return adjLists[from].stream()
                .filter((edge) -> edge.to == to)
                .collect(Collectors.toList()).get(0);
    }

    private class Dijkstras implements Iterator<Integer> {
        private PriorityQueue<Node> fringe;
        private int[] distTo;
        private static int[] edgeTo;
        private Set<Integer> visited;
        private int stop;


        //constructor
        Dijkstras(Integer start, Integer stop) {
            this.stop = stop;
            this.visited = new HashSet<>();
            this.fringe = new PriorityQueue<>(Comparator.comparing((node) -> node.getDistToSource()));

            fringe.add(new Node(start, 0));
            this.distTo = new int[vertexCount];
            this.edgeTo = new int[vertexCount];
            distTo[start] = 0;

            for (int i = 1; i < vertexCount; i++) {
                distTo[i] = Integer.MAX_VALUE;
                fringe.add(new Node(i, distTo[i]));
            }

        }


        public static LinkedList<Integer> result(int curr) {
            LinkedList<Integer> result = new LinkedList<>();
            while (curr != 0) {
                result.addFirst(curr);
                curr = edgeTo[curr];
            }
            result.addFirst(curr);
            return result;
        }

        @Override
        public boolean hasNext() {
            return !fringe.isEmpty() && !visited.contains(stop);
        }

        @Override
        public Integer next() {
            int poppedOff = fringe.poll().nodeVal; //poppedOff = k

            if (visited.contains(poppedOff)) {
                poppedOff = fringe.poll().nodeVal;
            }

            visited.add(poppedOff);

            for (int i : neighbors(poppedOff)) {
                if (!visited.contains(i)) {
                    int weight = getEdge(poppedOff, i).weight;
                    int length = distTo[poppedOff] + weight;
                    if (length < distTo[i]) {
                        distTo[i] = length;
                        edgeTo[i] = poppedOff;

                        Node update = new Node(i, distTo[i]);
                        fringe.add(update);
                    }
                }
            }
            return poppedOff;
        }

    }

    private class Node implements Comparable<Node> {

        private int nodeVal;
        private int distToSource;

        public Node() {
        }

        public Node(int nodeVal, int distToSource) {
            this.nodeVal = nodeVal;
            this.distToSource = distToSource;
        }

        public int getNodeVal() {
            return this.nodeVal;
        }

        public int getDistToSource() {
            return this.distToSource;
        }

//        @Override
//        public int compare(Node o1, Node o2) {
//            if (o1.distToSource > o2.distToSource) {
//                return 1;
//            }
//            if (o1.distToSource < o2.distToSource) {
//                return -1;
//            }
//            return 0;
//        }

        @Override
        public String toString() {
            return "Node val: " + this.nodeVal
                    + " Dist to src: " + this.distToSource;
        }


        @Override
        public int compareTo(Node node) {
            return Integer.compare(this.distToSource, node.getDistToSource());
        }
    }

}

