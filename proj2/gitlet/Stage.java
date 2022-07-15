package gitlet;

import java.io.*;
import java.util.*;

import static gitlet.Utils.*;

/**
 * The stage is a focal point between staged files in the directory and commit objects.
 * It will instantiate new commits, link the new commits to their parent commits by hashID,
 * and then populate the commits with the files staged.
 */
public class Stage extends Store<String, String> {
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");
    public static final File BLOB_DIR = Utils.join(GITLET_DIR, ".blob");
    public static final File STAGE_DIR = Utils.join(GITLET_DIR, ".staging");
    HashMap<String, String> addStage;
    Map<String, byte[]> removeStage;
    Map<String, String> allPrevMap;


    public Stage() {
        super(new HashMap<>());
        this.addStage = new HashMap<>();
        this.removeStage = new HashMap<>();
    }

    public boolean canAdd(File file) {
        String k = file.getPath();
        byte[] content = serialize(file);
        String v = sha1(k, content);
        boolean existsinBlob = Utils.join(BLOB_DIR, k).exists();
        return !(this.addStage.containsKey(k) || existsinBlob);
    }

    public void add(File file) throws IOException {
        String k = file.getPath(); //make the key the path
        byte[] content = serialize(file);
        String v = sha1(k, content); //make the value the sha1 that sha together the file path & the serialized version of the file
//        byte[] v = serialize(file);
//        String k = sha1(v);
        this.addStage.put(k, v);
        System.out.println(STAGE_DIR.isFile()); //TODO: delete this line in the future
        writeObject(STAGE_DIR, this.addStage);

//        save(file, (HashMap<String, byte[]>) this.getMap());
    }

    public boolean canRemove(File file) {
        return true; //change this when ready to edit method
    }

    public void remove(File file) {

    }

    public boolean stageEmpty() {
        return this.addStage.isEmpty() && this.removeStage.isEmpty();
    }

//    private void save(File file, HashMap<String, byte[]> toSave) {
////        File path = join(STAGE_DIR, file.getName());
//        writeObject(STAGE_DIR, this);
//    }
}
