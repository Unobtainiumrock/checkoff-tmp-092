package byow.Core;

import org.junit.Test;
import static org.junit.Assert.*;

public class RandomGraphTest {

    @Test
    public void constructorTests() {
        /**
         * Should handle if no seed is provided via constructor overloading
         */
        RandomGraph rg = new RandomGraph();

        assertTrue(rg.getSize() != 0);

    }

    @Test
    public void randomPlacementTests() {
        // No collisions
    }

    @Test
    public void vertexTests() {

    }

    @Test
    public void edgeTests() {

    }


}
