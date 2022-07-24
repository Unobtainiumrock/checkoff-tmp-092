package gitlet;

import java.util.HashMap;

/**
 * @author Granpa Weekend
 */
public class BranchStore extends HashMap<String,
        CommitStore> implements Save {
    /**
     *
     */
    private String branchName;

    /**
     *
     * @param b b
     */
    public BranchStore(String b) {
        this.branchName = b;
    }

    /**
     * @param b b
     */
    public void setBranchName(String b) {
        this.branchName = b;
    }

    /**
     *
     * @return
     */
    public String getBranchName() {
        return this.branchName;
    }
}
