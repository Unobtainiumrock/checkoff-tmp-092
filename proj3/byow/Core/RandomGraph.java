package byow.Core;

// List of things the random graph should have.
    // Vertices' weights: to be placed at random locations on our grid. (done)
    // Edges: (within the constraints of what make a graph)
    // Orientation: of edges
    // Edge weights:



// Density increases as number of edges approaches nuber of vertices squared.

// User provides seed


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This random graph will serve as our model in our MVC architecture.
 */
public class RandomGraph {
    private Random r;
    private int k;
    private int adjMatrix[][];
    private int size;
    private int seed;

    /**
     * Nice.
     */
    public RandomGraph() {
        this(69);
    }

    /**
     * Construct me. Ahh yes.
     *
     * @param seed Provided by the user via CLI
     */
    public RandomGraph(int seed) {
        this.seed = seed;
        this.r = new Random(this.seed);
        this.k = r.nextInt(24);
        this.adjMatrix = new int[k][k];
        this.size = k - 2;
        this.init();
        System.out.println("Fuck yeah");
    }

    /**
     * Used to randomly flip zeroes to indicate adjaceny.
     */
    private void init() {
        // Add edge buffers, so we don't have to do lots of crazy bounds checks

        // Top buffer
        for (int i = 0; i < k; i++) {
            this.adjMatrix[0][i] = -1;
        }

        // Bottom buffer
        for (int i = 0; i < k; i++) {
            this.adjMatrix[k - 1][i] = -1;
        }

        // Left buffer
        for (int i = 0; i < k; i++) {
            this.adjMatrix[i][0] = -1;
        }

        // Right buffer
        for (int i = 0; i < k; i++) {
            this.adjMatrix[i][k - 1] = -1;
        }

        /**
         * Randomly drop into the adjMatrix as a starting point to walk from.
         */
        int x, y;
        x = r.nextInt(this.size);
        y = r.nextInt(this.size);

        while (!validLocation(x, y)) {
            x = r.nextInt(this.size);
            y = r.nextInt(this.size);
        }

        this.adjMatrix[x][y] = 1;

        /**
         * Randomly walk.
         */
        RandomWalk rw = new RandomWalk(this.seed);

        //TODO play around with how many vertices we allow?
        for (int i = 1; i < this.size; i ++) {
//            int x = r.nextInt(this.size);
//            int y = r.nextInt(this.size);
//
//            while (adjMatrix[x][y] == 1 || x == y || this.adjMatrix[x][y] == -1) {
//                x = r.nextInt(this.size);
//                y = r.nextInt(this.size);
//            }
//
//            this.adjMatrix[x][y] = 1;
//            this.adjMatrix[y][x] = 1;
            List<Integer> orderedPair = new ArrayList<>();
            orderedPair.add(0, x);
            orderedPair.add(1, y);
            orderedPair = rw.randomMove(orderedPair);

            int newX = orderedPair.get(0);
            int newY = orderedPair.get(1);

            while (!(validLocation(newX, newY))) {
                orderedPair = rw.randomMove(orderedPair);
                newX = orderedPair.get(0);
                newY = orderedPair.get(1);
            }

            this.adjMatrix[newX][newY] = 1;
            this.adjMatrix[newY][newX] = 1;
        }
    }

    private boolean validLocation(int x, int y) {
        return !((x == y) || (this.adjMatrix[x][y] == -1) || adjMatrix[x][y] == 1);
    }


    public int getSize() {
        return this.size;
    }

    public int[][] getAdjMatrix() {
        return this.adjMatrix;
    }

}
