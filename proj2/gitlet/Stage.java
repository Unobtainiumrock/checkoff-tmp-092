package gitlet;

import jdk.jshell.execution.Util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.nio.file.Paths;

import static gitlet.Utils.*;

/**
 * The stage is a focal point between staged files in the directory and commit objects.
 * It will instantiate new commits, link the new commits to their parent commits by hashID,
 * and then populate the commits with the files staged.
 */
public class Stage extends Store<String, byte[]> {
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");
    public static final File BLOB_DIR = Utils.join(GITLET_DIR, ".blob");
    public static final File STAGE_DIR = Utils.join(GITLET_DIR, ".staging");
    Map<String, byte[]> removeStage;


    public Stage() {
        super(new HashMap<>());
        this.removeStage = new HashMap<>();
    }

    public boolean canAdd(File file) {
        byte[] v = serialize(file);
        String fileID = sha1(v);
        boolean existsinBlob = Utils.join(BLOB_DIR, fileID).exists();
        return !(this.containsKey(fileID) || existsinBlob);
    }

    public void add(File file) throws IOException {
        byte[] v = serialize(file);
        String k = sha1(v);
        this.add(k, v);
        save(file, (HashMap<String, byte[]>) this.getMap());

    }

    private void save(File file, HashMap<String, byte[]> toSave) {
        File path = join(STAGE_DIR, file.getName());
        writeObject(path, toSave);
    }
}
