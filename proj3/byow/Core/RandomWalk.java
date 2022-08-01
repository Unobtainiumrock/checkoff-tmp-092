package byow.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWalk {
    private Random r;

    /**
     * Nice.
     */
    public RandomWalk() {
        this(69);
    }

    public RandomWalk(int seed) {
        this.r = new Random(seed);
    }

    public static List<Integer> up(List<Integer> orderedPair) {
        /**
         * Clone the orderedPair
         */
        List<Integer> res = new ArrayList<>();
        res.add(0, orderedPair.get(0));
        res.add(1, orderedPair.get(1));

        /**
         * Update
         */
        res.set(0, res.get(0) - 1);
        return res;
    }

    public static List<Integer> down(List<Integer> orderedPair) {
        /**
         * Clone the orderedPair
         */
        List<Integer> res = new ArrayList<>();
        res.add(0, orderedPair.get(0));
        res.add(1, orderedPair.get(1));

        res.set(0, res.get(0) + 1);
        return res;
    }

    public static List<Integer> left(List<Integer> orderedPair) {
        /**
         * Clone the orderedPair
         */
        List<Integer> res = new ArrayList<>();
        res.add(0, orderedPair.get(0));
        res.add(1, orderedPair.get(1));

        res.set(1, res.get(1) - 1);
        return res;
    }

    public static List<Integer> right(List<Integer> orderedPair) {
        /**
         * Clone the orderedPair
         */
        List<Integer> res = new ArrayList<>();
        res.add(0, orderedPair.get(0));
        res.add(1, orderedPair.get(1));

        res.set(1,res.get(1) + 1);

        return res;
    }

    public static  List<Integer> downRight(List<Integer> orderedPair) {
        /**
         * Clone the orderedPair
         */
        List<Integer> res = new ArrayList<>();
        res.add(0, orderedPair.get(0));
        res.add(1, orderedPair.get(1));

        return right(down(res));
    }

    public static List<Integer> downLeft(List<Integer> orderedPair) {
        /**
         * Clone the orderedPair
         */
        List<Integer> res = new ArrayList<>();
        res.add(0, orderedPair.get(0));
        res.add(1, orderedPair.get(1));

        return left(down(res));
    }

    private List<Integer> upRight(List<Integer> orderedPair) {
        /**
         * Clone the orderedPair
         */
        List<Integer> res = new ArrayList<>();
        res.add(0, orderedPair.get(0));
        res.add(1, orderedPair.get(1));
        return right(up(res));
    }

    private List<Integer> upLeft(List<Integer> orderedPair) {
        /**
         * Clone the orderedPair
         */
        List<Integer> res = new ArrayList<>();
        res.add(0, orderedPair.get(0));
        res.add(1, orderedPair.get(1));

        return left(up(res));
    }

    public List<Integer> randomMove(List<Integer> orderedPair) {
        List<List<Integer>> l = new ArrayList<>();
        l.add(up(orderedPair));
        l.add(down(orderedPair));
        l.add(left(orderedPair));
        l.add(right(orderedPair));
        l.add(downRight(orderedPair));
        l.add(downLeft(orderedPair));
        l.add(upRight(orderedPair));
        l.add(upLeft(orderedPair));

        int randomIndex = r.nextInt(8);

        return l.get(randomIndex);
    }
}
