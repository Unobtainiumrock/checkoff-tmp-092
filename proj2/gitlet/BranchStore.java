package gitlet;

import java.util.HashMap;

public class BranchStore extends HashMap<String, CommitStore> implements Save {
    private Repository repo;

    public BranchStore(Repository repo) {
        this.repo = repo;
    }

//    public Commit branch(String branchname, Commit commit) {
//        this.put(branchname, commit);
//        return commit;
//    }
}
