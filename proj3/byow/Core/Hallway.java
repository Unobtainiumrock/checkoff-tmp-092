package byow.Core;
import java.util.Random;


// Two possible orientations
// thickness of 1 - 2 and a buffer for walls on either side -- (3-4 total thickness occupied)
// length random
public class Hallway {
    /**
     * Responsible for determining whether to draw horizontally or vertical.
     */
    private Random r = new Random();
    private int length;

    /**
     *
     * @param length A random integer passed from some parent randomizer
     */
    public Hallway(int length) {
        this.length = length;
    }

    /**
     *
     * @param length A random integer passed from some parent randomizer.
     */
    public void vertical(int length) {
        System.out.println("Making vertical hallway of size: " + length);

    }

    public void horizontal(int length) {
        System.out.println("Making horizontal hallway: " + length);
    }

    private int alternate() {
        return this.r.nextInt(2);
    }

    public static void main(String[] args) {
        Hallway test = new Hallway(5);

        for (int i = 0; i < 10; i ++) {
            int flip = test.alternate();
           if (flip == 1) {
               test.vertical(1);
           } else {
               test.horizontal(1);
           }
        }
    }
}
