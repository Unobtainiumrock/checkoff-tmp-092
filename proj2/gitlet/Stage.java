package gitlet;

import java.util.HashSet;
import java.util.Map;

/**
 * @author grandpa weekend
 */
public abstract class Stage extends
        HashSet<Map<String, String>> implements Save {

    /**
     *
     */
    private Repository repo;

    /**
     *
     * @param r rr
     */
    public Stage(Repository r) {
        this.repo = r;
    }

    /**
     *
     * @return
     */
    abstract boolean add();

    /**
     *
     * @return
     */
    abstract boolean canAdd();
}
