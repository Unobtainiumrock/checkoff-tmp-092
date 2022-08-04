import java.util.Arrays;

public class UnionFind {

    private int[] numElem;

    private int[] parents;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        if (N < 0) {
            throw new IllegalArgumentException();
        }

        this.numElem = new int[N];
        Arrays.fill(numElem, 1);

        this.parents = new int[N];
        for (int i = 0; i < this.parents.length; i++) {
            this.parents[i] = i;
        }
    }

    public UnionFind() {

    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return this.numElem[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if (parents[v] == v) {
            int res = numElem[v];
            return res * -1;
        }
        return parents[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v >= parents.length) {
            throw new IllegalArgumentException();
        }
        if (parents[v] != v) {
            parents[v] = find(parents[v]);
            return find(parents[v]);
        } else {
            return v;
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int v1Root = find(v1);
        int v2Root = find(v2);

        if (v1 == v2 || v1Root == v2Root) {
            return;
        } else if (numElem[v2Root] >= numElem[v1Root]) {
            parents[v1Root] = v2Root;
            numElem[v2Root] += numElem[v1Root];
        } else {
            parents[v2Root] = v1Root;
            numElem[v1Root] += numElem[v2Root];
        }
    }
}
