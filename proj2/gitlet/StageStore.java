package gitlet;

import java.io.*;
import java.util.*;
import static gitlet.Repository.*;

/**
 * The stage is a focal point between staged files in the directory and commit objects.
 * It will instantiate new commits, link the new commits to their parent commits by hashID,
 * and then populate the commits with the files staged.
 */
public class StageStore<E> extends HashSet<E> implements Save {
    private Set<E> removeStage;

    public StageStore() {
        super(); // The addStage is implicit. It is a HashSet that can be accessed using "this"
        this.removeStage = new HashSet<>();
    }

    private boolean canAdd(File file) {
        String k = file.getPath();
//        byte[] content = serialize(file);
//        String v = sha1(k, content);
        boolean existsinBlob = blobStore.containsKey(k);
        return !(this.contains(k) || existsinBlob);
    }

    public void add(File file) throws IOException {
//        String k = file.getPath(); //make the key the path
//        byte[] content = serialize(file);
//        String v = sha1(k, content); //make the value the sha1 that sha together the file path & the serialized version of the file
//        this.add(k, v);
//        System.out.println(STAGE_DIR.isFile()); //TODO: delete this line in the future
//        writeObject(STAGE_DIR, this);

//        save(file, (HashMap<String, byte[]>) this.getMap());
        if (canAdd(file)) {
            this.add(file);
        }
    }

    public boolean canRemove(File file) {
        return this.contains(file);
    }

    // Not needed, we extend if from HashSet
//    public void remove(File file) {
//
//    }

//    Not needed, also extended from HashSet
//    public boolean stageEmpty() {
//        return this.isEmpty() && this.removeStage.isEmpty();
//    }


//    private void save(File file, HashMap<String, byte[]> toSave) {
////        File path = join(STAGE_DIR, file.getName());
//        writeObject(STAGE_DIR, this);
//    }
}
