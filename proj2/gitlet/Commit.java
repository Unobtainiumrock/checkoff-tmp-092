package gitlet;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import static gitlet.Utils.*;

/**
 * @author Grandpa Weekend
 */
public class Commit implements Save {
    /**
     *
     */
    private String message;
    /**
     *
     */
    private String timestamp;
    /**
     *
     */
    private String parentHashID;
    /**
     *
     */
    private Commit parent;
    /**
     *
     */
    private Set<Map<String, String>> fileHashes;
    /**
     *
     */
    private String hashID;
    /**
     *
     */
    private String shortenedHashID;
    /**
     *
     */
    private String branchMom;

    /**
     *
     * @param m m
     * @param p p
     * @param f f
     * @param b b
     * @param c c
     */
    public Commit(String m, String p, Set<Map<String, String>> f,
                   String b, Commit c) {
        String pattern = "EEE MMM dd HH:mm:ss yyyy Z";
        SimpleDateFormat metaTime = new SimpleDateFormat(pattern);
        this.message = m;
        this.parentHashID = p;
        this.parent = c;
        this.fileHashes = f;
        this.hashID = this.generateHashID();
        this.timestamp = metaTime.format(new Date());
        this.branchMom = b;
    }

    /**
     *
     */
    public Commit() {
        this.message = "initial commit";
        String pattern = "EEE MMM dd HH:mm:ss yyyy Z";
        SimpleDateFormat metaTime = new SimpleDateFormat(pattern);
        this.timestamp = metaTime.format(new Date(0));
        this.parentHashID = null;
        this.parent = null;
        this.fileHashes = new HashSet<>();
        this.hashID = sha1(serialize(this), this.message);
        this.shortenedHashID = this.hashID.substring(0, 8);
        this.branchMom = "main";
    }

    /**
     *
     * @return
     */
    private String generateHashID() {
        String hash = sha1(serialize(this), this.message);
        this.shortenedHashID = hash.substring(0, 8);
        return hash;
    }

    /**
     *
     * @return
     */
    public Set<Map<String, String>> getFileHashes() {
        return this.fileHashes;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return this.message;
    }

    /**
     *
     * @return
     */
    public String getTimestamp() {
        return this.timestamp;
    }

    /**
     *
     * @return
     */
    public Commit getParent() {
        return this.parent;
    }

    /**
     *
     * @return
     */
    public String getHashID() {
        return this.hashID;
    }

    /**
     *
     * @return
     */
    public String getShortenedHashID() {
        return this.shortenedHashID;
    }

    /**
     *
     * @return
     */
    public String getParentID() {
        return this.parentHashID;
    }

    /**
     *
     * @return
     */
    public String getBranchMom() {
        return this.branchMom;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String res = "";

        res += "Parent Hash ID " +  this.parentHashID + "\n";
        res += "Time Stamp: " + this.timestamp + "\n";
        res += "Commit Message: " + this.message + "\n";
        res += "Dual Keys: ";
        for (Map<String, String> fileHash : fileHashes) {
            Map.Entry<String, String> entry = fileHash.entrySet()
                    .iterator().next();
            String k1 = entry.getKey();
            String k2 = entry.getValue();
            res += "{ key1: " + k1 + "," + "key2: " + k2 + "}" + "\n";
        }
        return res;
    }

}
