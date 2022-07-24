package gitlet;
import java.util.LinkedHashMap;

/**
 * @author Grandpa Weekend
 */
public class CommitStore extends LinkedHashMap<String, Commit> implements Save {
    /**
     *
     */
    private Commit head;

    /**
     *
     */
    private Repository repo;

    /**
     *
     * @param r r
     */
    public CommitStore(Repository r) {
        Commit initial = new Commit();
        this.repo = r;
        this.head = initial;
        this.init(initial);
    }

    /**
     *
     * @param commit c
     */
    public CommitStore(Commit commit) {
        this.head = commit;
    }

    /**
     *
     * @param sha1 s
     * @param commit c
     * @return
     */
    public Commit add(String sha1, Commit commit) {
        this.put(sha1, commit);
        this.head = commit;
        return commit;
    }

    /**
     *
     * @return
     */
    public Commit getHead() {
        return this.head;
    }

    /**
     *
     * @param h h
     */
    public void setHead(Commit h) {
        this.head = h;
    }

    /**
     *
     * @param firstCommit f
     */
    private void init(Commit firstCommit) {
        if (!this.repo.getInitialized()) {
            String sha1 = firstCommit.getHashID();
            this.add(sha1, firstCommit);
            this.repo.setInitialized(true);
        } else {
            System.out.println("The commit store has already been"
                    +
                    " initialized\n"
                    +
                    "if you wish to add "
                    +
                    "a commit to the store, use add() instead.");
        }
    }
}
