package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static gitlet.Utils.*;


public class Commit implements Save {
    private String message;
    private String timestamp;
    private String parentHashID; // Generated by a commit self-hashing.
    private Set<String> fileHashes;
    private String hashID;


    public Commit (String message, String parentID, Set<String> fileHashes) {
        this.message = message;
        this.parentHashID = parentID;
        this.fileHashes = fileHashes;
        this.hashID = this.generateHashID();
        String pattern = "HH:mm:ss Z, MM-dd-yyyy";
        SimpleDateFormat metaTime = new SimpleDateFormat(pattern);
        this.timestamp = metaTime.format(new Date());
    }

    public Commit() {
        this.message = "initial commit";
        this.timestamp = "00:00:00 UTC, Thursday, 1 January 1970";
        this.parentHashID = null;
        this.fileHashes = new HashSet<>();
        this.hashID = sha1(serialize(this), this.message);
    }

    private String generateHashID() {
        return sha1(this.message, parentHashID, this.fileHashes);
    }

//    public void save() {
//        File file = join(COMMIT_DIR, this.hashID);
//        writeObject(file,this);
//    }

    public Set<String> getFileHashes() {
        return this.fileHashes;
    }

    public String getMessage() {
        return this.message;
    }

    public String getTimestamp() {
        return this.timestamp;
    }


    public String getHashID() {
        return this.hashID;
    }

    public String getParentID() {
        return this.parentHashID;
    }

    @Override
    public String toString() {
        String res = "";

        res += "Parent Hash ID " +  this.parentHashID + "\n";
        res += "Time Stamp: " + this.timestamp + "\n";
        res += "Commit Message: " + this.message + "\n";
        res += "File hashes: ";
        for (String fileHash : fileHashes) {
            res += fileHash + "\n";
        }
        return res;
    }

}
