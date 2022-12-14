package gitlet;
import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;

public interface Save extends Serializable {
    File CWD = new File(System.getProperty("user.dir"));
    File GITLET_DIR = Utils.join(CWD, ".gitlet");
    File STAGE_DIR = Utils.join(GITLET_DIR, ".staging");
    File BLOB_DIR = Utils.join(GITLET_DIR, ".blobs");
    File COMMIT_DIR = Utils.join(GITLET_DIR, ".commits");
    File BRANCH_DIR = Utils.join(GITLET_DIR, ".branches");
    File RMSTAGE_DIR = Utils.join(GITLET_DIR, ".unstaging");


//    CommitStore commitStore = new CommitStore();
//    StageStore stageStore = new StageStore();
//    BlobStore blobStore = new BlobStore();

    default void save(File file) {
        writeObject(file, this);
    };
}
