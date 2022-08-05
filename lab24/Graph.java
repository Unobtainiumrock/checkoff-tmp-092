import com.sun.source.tree.Tree;
import com.sun.source.util.Trees;

import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.io.IOException;
import java.util.stream.Collectors;

/* A mutable and finite Graph object. Edge labels are stored via a HashMap
   where labels are mapped to a key calculated by the following. The graph is
   undirected (whenever an Edge is added, the dual Edge is also added). Vertices
   are numbered starting from 0. */
public class Graph {
    /* Maps vertices to a Set of its neighboring vertices.
     * V to {V, ...}
     * */
    private HashMap<Integer, Set<Integer>> neighbors = new HashMap<>();
    /* Maps vertices to a Set of its connected edges.
     * V to {E, ...}
     *
     *  */
    private HashMap<Integer, Set<Edge>> edges = new HashMap<>();
    /* A sorted set of all edges. */
    private TreeSet<Edge> allEdges = new TreeSet<>();

    /* Returns a randomly generated graph with VERTICES number of vertices and
       EDGES number of edges with max weight WEIGHT. */
    public static Graph randomGraph(int vertices, int edges, int weight) {
        Graph g = new Graph();
        Random rng = new Random();
        for (int i = 0; i < vertices; i += 1) {
            g.addVertex(i);
        }
        for (int i = 0; i < edges; i += 1) {
            Edge e = new Edge(rng.nextInt(vertices), rng.nextInt(vertices), rng.nextInt(weight));
            g.addEdge(e);
        }
        return g;
    }

    /* Returns a Graph object with integer edge weights as parsed from
       FILENAME. Talk about the setup of this file. */
    public static Graph loadFromText(String filename) {
        Charset cs = Charset.forName("US-ASCII");
        try (BufferedReader r = Files.newBufferedReader(Paths.get(filename), cs)) {
            Graph g = new Graph();
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(", ");
                if (fields.length == 3) {
                    int from = Integer.parseInt(fields[0]);
                    int to = Integer.parseInt(fields[1]);
                    int weight = Integer.parseInt(fields[2]);
                    g.addEdge(from, to, weight);
                } else if (fields.length == 1) {
                    g.addVertex(Integer.parseInt(fields[0]));
                } else {
                    throw new IllegalArgumentException("Bad input file!");
                }
            }
            return g;
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    public static void main(String[] args) {
//        Graph test = loadFromText("inputs/graphTestSomeDisjoint.in");
//        Graph test2 = loadFromText("inputs/tmp.in");
//        test2.prims(0);
    }

    public static void testArea() {
        Set<Integer> visited = new HashSet<>();


    }

    /* Returns the vertices that neighbor V.
     *
     * f(V) -> {V, ...}
     *
     * */
    public TreeSet<Integer> getNeighbors(int v) {
        return new TreeSet<Integer>(neighbors.get(v));
    }

    /* Returns all edges adjacent to V.
     *
     * f(V) -> {E, ....}
     *
     *  */
    public TreeSet<Edge> getEdges(int v) {
        return new TreeSet<Edge>(edges.get(v));
    }

    /* Returns a sorted list of all vertices. */
    public TreeSet<Integer> getAllVertices() {
        return new TreeSet<Integer>(neighbors.keySet());
    }

    /* Returns a sorted list of all edges. */
    public TreeSet<Edge> getAllEdges() {
        return new TreeSet<Edge>(allEdges);
    }

    /* Adds vertex V to the graph. */
    public void addVertex(Integer v) {
        if (neighbors.get(v) == null) {
            neighbors.put(v, new HashSet<Integer>());
            edges.put(v, new HashSet<Edge>());
        }
    }

    /* Adds Edge E to the graph. */
    public void addEdge(Edge e) {
        addEdgeHelper(e.getSource(), e.getDest(), e.getWeight());
    }

    /* Creates an Edge between V1 and V2 with no weight. */
    public void addEdge(int v1, int v2) {
        addEdgeHelper(v1, v2, 0);
    }

    /* Creates an Edge between V1 and V2 with weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        addEdgeHelper(v1, v2, weight);
    }

    /* Returns true if V1 and V2 are connected by an edge. */
    public boolean isNeighbor(int v1, int v2) {
        return neighbors.get(v1).contains(v2) && neighbors.get(v2).contains(v1);
    }

    /* Returns true if the graph contains V as a vertex. */
    public boolean containsVertex(int v) {
        return neighbors.get(v) != null;
    }

    /* Returns true if the graph contains the edge E. */
    public boolean containsEdge(Edge e) {
        return allEdges.contains(e);
    }

    public Graph kruskals() {
        UnionFind stuff = new UnionFind();
        Graph MSTResult = new Graph();
        int vertexCount = getAllVertices().size();
        int edgesAdded = 0;

        Iterator allVertices = getAllVertices().iterator();

        while (allVertices.hasNext()) { //fill up MST with vertices
            MSTResult.addVertex((Integer) allVertices.next());
        }

        Iterator allEdges = getAllEdges().iterator(); //to go through all the edges
        while (allEdges.hasNext() && edgesAdded < vertexCount) {
            Edge nextEdge = (Edge) allEdges.next();
            int u = nextEdge.getSource();
            int w = nextEdge.getDest();
            int weight = nextEdge.getWeight();
            if (stuff.find(u) != stuff.find(w)) {
                MSTResult.addEdge(new Edge(u, w, weight));
                stuff.union(u, w);
                edgesAdded++;
            }
        }
        return MSTResult;
    }

    /* Returns if this graph spans G. */
    public boolean spans(Graph g) {
        TreeSet<Integer> all = getAllVertices();
        if (all.size() != g.getAllVertices().size()) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> vertices = new ArrayDeque<>();
        Integer curr;

        vertices.add(all.first());
        while ((curr = vertices.poll()) != null) {
            if (!visited.contains(curr)) {
                visited.add(curr);
                for (int n : getNeighbors(curr)) {
                    vertices.add(n);
                }
            }
        }
        return visited.size() == g.getAllVertices().size();
    }

    /* Overrides objects equals method. */
    public boolean equals(Object o) {
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) o;
        return neighbors.equals(other.neighbors) && edges.equals(other.edges);
    }

    /* A helper function that adds a new edge from V1 to V2 with WEIGHT as the
       label. */
    private void addEdgeHelper(int v1, int v2, int weight) {
        addVertex(v1);
        addVertex(v2);

        neighbors.get(v1).add(v2);
        neighbors.get(v2).add(v1);

        Edge e1 = new Edge(v1, v2, weight);
        Edge e2 = new Edge(v2, v1, weight);
        edges.get(v1).add(e1);
        edges.get(v2).add(e2);
        allEdges.add(e1);
    }

    /*
     * DFS
     * for each vertex, quick sort its neighbors
     * iterate the sorted neighbors
     * for each neighbor, recurse
     *
     * all while building the needed
     *  path.*
     *
     * */
    public Graph prims(int start) {
        Graph mst = new Graph();
        Set<Integer> visited = new HashSet<>();
        recursiveHelper(start, mst, visited);
//        System.out.println(mst.spans(this));
        return mst;
    }

    private void recursiveHelper(int v, Graph mst, Set<Integer> visited) {
        visited.add(v);
        List<Edge> treeSetToSortedList = this.getEdges(v)
                .stream()
                .sorted()
                .filter((edge) -> !visited.contains(edge.getDest()))
                .collect(Collectors.toList());

        while (mst.getAllVertices().size() != this.getAllVertices().size()) {
            if (!treeSetToSortedList.isEmpty()) {
                Edge e = treeSetToSortedList.remove(0);
                mst.addEdge(e.getSource(), e.getDest(), e.getWeight());
                v = e.getDest();
                recursiveHelper(v, mst, visited);
            } else {
                break;
            }
        }
    }
}


