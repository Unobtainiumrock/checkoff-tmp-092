package gitlet;

// TODO: any imports you need here

import jdk.jshell.execution.Util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Date; // TODO: You'll likely use this in this class

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    private String message;
    private String timestamp;
    private String parentHash;
    private transient String myHash;
    private Map<String, String> blobHash; //typed to the interface, map, instead of hashmap, in case we need to change later
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    public Commit (String message, String timestamp, String parentHash, String myHash, Map<String, String> blobHash) {
        this.message = message;
        this.timestamp = timestamp;
        this.parentHash = parentHash;
        this.blobHash = blobHash;
        this.myHash = myHash;

    }

    public Commit() {
        this.message = "initial commit";
        this.timestamp = "00:00:00 UTC, Thursday, 1 January 1970";
        this.parentHash = null;
        this.blobHash = new HashMap<>();
        this.myHash = Utils.sha1(Utils.serialize(this));
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getMyHash() {
        return this.myHash;
    }

    public void setMyHash(String myHash) {
        this.myHash = myHash;
    }

    public void setTimeStamp(String timeStamp) {
        this.timestamp = timestamp;
    }

    public String getparentHash() {
        return this.parentHash;
    }

    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

    public Map<String, String> getBlobHash() {
        return this.blobHash;
    }

    public void setBlobHash(Map<String, String> blobHash) {
        this.blobHash = blobHash;
    }

    /* TODO: fill in the rest of this class. */
}
