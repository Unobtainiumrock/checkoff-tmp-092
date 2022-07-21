package gitlet;

import java.util.HashSet;
import java.util.Map;

public abstract class Stage extends HashSet<Map<String, String>> implements Save {
    private Repository repo;

    public Stage(Repository repo) {
        this.repo = repo;
    }

    abstract boolean add();

    abstract boolean canAdd();
}
