import org.w3c.dom.Node;

import java.util.*;

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
            adjLists[v1].get(v2).weight = weight;
        } else {
            adjLists[v1].add(new Edge(v1, v2, weight));
        }
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        addEdge(v1, v2, weight);
        addEdge(v2, v1, weight);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        for (int i = 0; i < adjLists[from].size(); i++) {
            if (adjLists[from].get(i).to == to) {
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < adjLists[v].size(); i++) {
            res.add(adjLists[v].get(i).to);
        }
        return res;
    }

    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        //i.e. the number of u to v paths that exist
        //check each of the elem in adjList that is not v, see which of their to is = v
        int res = 0;
        for (int i = 0; i < adjLists.length; i++) {
            for (int j = 0; j < adjLists[i].size(); j++) {
                if (adjLists[i].get(j).to == v) {
                    res += 1;
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

    /**
     *  A class that iterates through the vertices of this graph,
     *  starting with a given vertex. Does not necessarily iterate
     *  through all vertices in the graph: if the iteration starts
     *  at a vertex v, and there is no path from v to a vertex w,
     *  then the iteration will not include w.
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
        List<Integer> res = new ArrayList<>();

        if (!pathExists(start, stop)) {
            return res;
        }


        List<Integer> dfsList = dfs(start);

        for (Integer i : dfsList) {
            res.add(i);

            if (i == stop) {
                break;
            }
        }
        return res;
    }

    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;

        private ArrayList<Integer> currentInDegree;

        TopologicalIterator() {
            fringe = new Stack<Integer>();
            currentInDegree = new ArrayList<>();
            //currentInDegree[v] is initialized with the in-degree of each vertex v.
            for (int i = 0; i < vertexCount; i++) {
                currentInDegree.add(i, inDegree(i));
                if (currentInDegree.get(i) == 0) {
                    fringe.push(i);
                }
            }
        }

        public boolean hasNext() {
            if (!fringe.isEmpty()) {
                int i = fringe.pop();
                if (!neighbors(i).isEmpty()) {
                    return false;
                }
                fringe.push(i);
                return true;
            }
            return false;
        }


        public Integer next() {
            int curr = fringe.pop();
            // Iterate the neighbords
            // subtract 1 from each of their in-degrees
            for (int i : neighbors(curr)) {
                int toUpdate = currentInDegree.indexOf(i);
                int updateMe = currentInDegree.get(toUpdate);
                currentInDegree.set(toUpdate, updateMe - 1);

                int el = currentInDegree.get(toUpdate);
                if (el == 0) {
                    fringe.push(toUpdate);
                }
            }

            return curr;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }


    //Instantiate an array for distTo filled with infinity
    //Instantiate an array for EdgeTo filled with null
    //Instantiate an ArrayList or just a list called Visited, empty to start off with
    //Instantiate a PQ called fringe: new PriorityQueue<>(this.vertexCount, Comparator.comparingInt(v -> <length of the shortest path from start to v>))
    // i.e a minheap sorting

    //Constructor:
        //fill the distTo array with infinity
        //fill the PQ with node vals, associated priority value = node val's associated distTo value i.e.
        // PQ = [(node val, distTo val -- this is the priority val), (node val, distTo val), (node val, distTo val)...]



    public List<Integer> shortestPath(int start, int stop) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new Dijkstras(start, stop);
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class Dijkstras implements Iterator<Integer> {

        private PriorityQueue<Node> fringe;
        private int[] distTo;
        private int[] edgeTo;

        Dijkstras(Integer start, Integer stop) {
            fringe = new PriorityQueue<>(vertexCount, new Node()); //think about what this comparator should be.
            fringe.add(new Node(start, 0));
            int[] distTo = new int[vertexCount];
            int[] edgeTo = new int[vertexCount - 1];
            distTo[start] = 0;

            for (int i = 1; i < vertexCount; i++) {
                distTo[i] = Integer.MAX_VALUE;
                fringe.add(new Node(i, distTo[i]));
            }

        }

        @Override
        public boolean hasNext() {
            return false;
        }


        //Next:
        //while (!fringe.isEmpty())
        //int poppedOff = fringe.poll();
        //for (int i: neighbors(poppedOff))
        //int weight = getEdge(poppedOff, i).weight <-this gets the weight between poppedOff(which is the parent of "i" the neighbor) and i (the neighbor)
        //int length = distTo[poppedOff] + weight; <- this is the distance to i, our node of interest, by going from: source -> poppedOff, poppedOff -> i
        //if ( length < distTo[i])
        // distTo[i] = length; <-updating our distTo source to be this new length, which is closer
        // edgeTo[i] = poppedOff; <- since we now have a new path of reaching i, we update the edgeTo to be the parent it now takes a path from
        // if (fringe.contains(i)) <- this if loop is to remove the old nodeval-distTo val priority pair, then add in the new one. might not need this?
            //coz if we hit this condition means that an update was made, so we def want to remove the old one and add in the new one.
        //fringe.remove(i);
        //fringe.add(i, distTo[i]);
        @Override
        public Integer next() {
            int poppedOff = fringe.poll().nodeVal; //

            for (int i: neighbors(poppedOff)) {
                int weight = getEdge(poppedOff, i).weight;
                int length = distTo[poppedOff] + weight;
                if (length < distTo[i]) {
                    distTo[i] = length;
                    edgeTo[i] = poppedOff;

                    fringe.remove(i); //not remove(i) . it's remove the node with i as nodeVal
                    fringe.add(new Node(i, distTo[i]));

                }
            }

            return poppedOff;
        }

        private class Node implements Comparator<Node> {

            private int nodeVal;
            private int distToSource;

            public Node() {}

            public Node(int nodeVal, int distToSource) {
                this.nodeVal = nodeVal;
                this.distToSource = distToSource;
            }

            @Override
            public int compare(Node o1, Node o2) {
                if (o1.distToSource > o2.distToSource) {
                    return 1;
                }
                if (o1.distToSource < o2.distToSource) {
                    return -1;
                }
                return 0;
            }
        }
    }


    public Edge getEdge(int from, int to) {
        return adjLists[from].get(to);
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



    public static void main(String[] args) {
        Graph g1 = new Graph(5);
        g1.generateG1();
        g1.printDFS(0);
        g1.printDFS(2);
        g1.printDFS(3);
        g1.printDFS(4);

        g1.printPath(0, 3);
        g1.printPath(0, 4);
        g1.printPath(1, 3);
        g1.printPath(1, 4);
        g1.printPath(4, 0);

        Graph g2 = new Graph(5);
        g2.generateG2();
        g2.printTopologicalSort();
    }
}
