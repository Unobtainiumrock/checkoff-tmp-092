package gitlet;

import java.util.HashMap;

public class BranchStore extends HashMap<String, CommitStore> implements Save {
    private Repository repo;
    private String branchName;

    public BranchStore(Repository repo, String branchName) {
        this.repo = repo;
        this.branchName = branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchName() {
        return this.branchName;
    }

//    public Commit branch(String branchname, Commit commit) {
//        this.put(branchname, commit);
//        return commit;
//    }
}
