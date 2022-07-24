package gitlet;

import java.util.HashSet;
import java.util.Map;

/**
 * @author WE
 */
public class RemoveStageStore extends HashSet<Map<String,
        String>> implements Save {
    /**
     *
     */
    private Repository repo;

    /**
     *
     * @param r r
     */
    public RemoveStageStore(Repository r) {
        this.repo = r;
    }
}
