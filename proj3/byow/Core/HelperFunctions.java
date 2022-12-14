package byow.Core;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Nancy Pelosi
 * Helper functions for random generation.
 */
public class HelperFunctions {

    /**
     * random seed generator.
     */
    private Random generator;

    /**
     * helper function.
     *
     * @param r rrr
     */
    public HelperFunctions(Random r) {
        this.generator = r;
    }

    /**
     * Creates a pseudo-randomized number within the inclusive range.
     *
     * @param upper inclusive upper bound
     * @param lower inclusive lower bound
     * @return pseudo-randomized number
     */
    public int inclusiveRandom(int lower, int upper) {
        return this.generator.nextInt(upper - lower + 1) + lower;
    }

    /**
     * Creates a pseudo-randomized number within the exclusive range --upper and
     * lower are excluded.
     *
     * @param upper exclusive upper bound
     * @param lower exclusive lower bound
     * @return pseudo-randomized number
     */
    public int exclusiveRandom(int lower, int upper) {
        return this.generator.nextInt(upper - lower - 1) + lower + 1;
    }

    /**
     * Note: May not be used. This was carried over from a battleship
     * game I made a few years ago.
     * This code in the method was the ONLY thing that I ended up borrowing from
     * somewhere online. I made sure to understand how it exactly works before
     * adapting it to my own needs as an ArrayList. See link 1.
     * link 1:
     * https://stackoverflow.com/questions/6443176/how-can-i-generate-
     * a-random-number-within-a-range-but-exclude-some
     * The constraint to this approach is that "exlcude"
     * must be sorted ascendingly.
     *
     * @param upper   inclusive upper bound
     * @param lower   inclusive lower bound
     * @param exclude an array of integers to exclude from the randomization
     * @return pseudo-randomized number
     */
    public int specialRandom(int upper, int lower, List<Integer> exclude) {
        if (exclude.isEmpty()) {
            return exclusiveRandom(upper, lower);
        }

        int random = lower + this.generator.nextInt(upper
                - lower + 1 - exclude.size());

        Collections.sort(exclude);

        for (int i : exclude) {
            if (random < i) {
                break;
            }
            random++;
        }

        return random;
    }
}
