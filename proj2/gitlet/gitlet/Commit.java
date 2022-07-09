package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.List;
import static gitlet.Utils.*;

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
    private Map<String, byte[]> blobMap; //typed to the interface, map, instead of hashmap, in case we need to change later
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    public Commit (String message, String timestamp, String parentHash, List<File> files) {
        this.message = message;
        this.timestamp = timestamp;
        this.parentHash = parentHash;
//        this.blobMap = readObject().getBlobMap();
//        this.populateBlobMap(files);

    }

    public Commit() {
        this.message = "initial commit";
        this.timestamp = "00:00:00 UTC, Thursday, 1 January 1970";
        this.parentHash = null;
        this.blobMap = new HashMap<>();
    }

    private Map<String, byte[]> populateBlobMap(List<File> files) {
        Map<String, byte[]> blobMap = new HashMap<>();

        files.forEach((file) -> {
            String key = sha1(serialize(file));
            byte[] val = serialize(file);

            if (!(blobMap.containsKey(key))) {
                blobMap.put(sha1(serialize(file)), serialize(file));
            }

        });
        return blobMap;
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

    public void setTimeStamp(String timeStamp) {
        this.timestamp = timestamp;
    }

    public String getparentHash() {
        return this.parentHash;
    }

    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

    public Map<String, byte[]> getBlobHash() {
        return this.blobMap;
    }

    public void setBlobHash(Map<String, byte[]> blobMap) {
        this.blobMap = blobMap;
    }

    /* TODO: fill in the rest of this class. */
}
