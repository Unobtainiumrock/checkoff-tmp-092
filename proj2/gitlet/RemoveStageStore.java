package gitlet;

import java.util.HashSet;
import java.util.Map;

public class RemoveStageStore extends HashSet<Map<String, String>> implements Save {
    private Repository repo;

    public RemoveStageStore(Repository repo) {
        this.repo = repo;
    }
}
