package byow.Core;
import java.util.Random;


/** @author Nancy Pelosi
 * Hallway generation, does vertical and horizontal
 */
public class Hallway {
    /**
     * Responsible for determining whether to draw
     * horizontally or vertical.
     */
    private Random r = new Random();

    /**
     * length of the hallway.
     */
    private int lengthh;

    /**
     *
     * @param length A random integer passed from some parent randomizer
     */
    public Hallway(int length) {
        this.lengthh = length;
    }

    /**
     *
     * @param length A random integer passed from some parent randomizer.
     */
    public void vertical(int length) {
        System.out.println("Making vertical hallway of size: " + length);

    }

    /**
     * generate horizontal hallways.
     * @param length length
     */
    public void horizontal(int length) {
        System.out.println("Making horizontal hallway: " + length);
    }

    /**
     * alternating.
     * @return
     */
    private int alternate() {
        return this.r.nextInt(2);
    }

    /**
     * the main method.
     * @param args argssss
     */
    public static void main(String[] args) {
        Hallway test = new Hallway(5);

        for (int i = 0; i < 10; i++) {
            int flip = test.alternate();
            if (flip == 1) {
                test.vertical(1);
            } else {
                test.horizontal(1);
            }
        }
    }
}
