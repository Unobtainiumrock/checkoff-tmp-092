package gitlet;

import jdk.jshell.execution.Util;

import java.io.File;
import java.util.*;

import static gitlet.Utils.*;

/**
 * The stage is a focal point between staged files in the directory and commit objects.
 * It will instantiate new commits, link the new commits to their parent commits by hashID,
 * and then populate the commits with the files staged.
 */
public class Stage {
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");
    public static final File BLOB_DIR = Utils.join(GITLET_DIR, ".blob");
    public static final File STAGE_DIR = Utils.join(GITLET_DIR, ".staging");
    Map<String, byte[]> onStage;
    Map<String, byte[]> removeStage;


    public Stage() {
        this.onStage = new HashMap<>();
        this.removeStage = new HashMap<>();
    }

    public boolean canAdd(File file) {
        byte[] v = serialize(file);
        String fileID = sha1(v);
        boolean existsinBlob = Utils.join(BLOB_DIR, fileID).exists();
        return this.onStage.containsKey(fileID) && existsinBlob;

    }

    public void add(File file) {
        byte[] v = serialize(file);
        String k = sha1(v);
        this.onStage.put(k, v);
        File f = Utils.join(STAGE_DIR, k);
        Utils.writeObject(f, file);
    }

//    byte[] v = serialize(file);
//    String k = sha1(v);
//    File fileID = Utils.join(BLOB_DIR, k);
//        if (!fileID.exists()) {
//        Stage.add(file);
//    }

}
