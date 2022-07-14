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
        return !(this.onStage.containsKey(fileID) || existsinBlob);
    }

    public void add(File file) throws IOException {
        byte[] v = serialize(file);
        String k = sha1(v);
        this.onStage.put(k, v); // A
        File f = Utils.join(STAGE_DIR, file.getName()); // directory/filename/
        f.mkdir();

        File f2 = Utils.join(f, k);
        Utils.writeObject(f2, k);

//        System.out.println(f.isDirectory());

//        System.out.println(f);
//        if (f.exists()) {
//            Utils.writeObject(f2, file);
//            return;
//        }

        // get path to directory
        // write file to directory path

//        System.out.println("f2: " + f2);
//        System.out.println("file:" + file);
//        System.out.println(file.isFile());
//        System.out.println(file.isDirectory());
//
//        System.out.println("f is a?" + f);
//        System.out.println("f file?: " + f.isFile());
//        System.out.println("f directory?: " + f.isDirectory());
//
//        System.out.println("f2 file?: " + f2.isFile());
//        System.out.println("f2 directory?: " + f2.isDirectory());
//        Utils.writeObject(f2, file);
        readWrite();
    }

    public void readWrite() throws IOException {
        Path path = Files.createFile(Path.of(CWD.getPath(), "tmp.txt"));
        Files.createFile(path);

//        BufferedInputStream tst = new BufferedInputStream(Files.newInputStream(thing.toAbsolutePath()));
//        String result = new String(tst.readAllBytes(), StandardCharsets.UTF_8);
//        tst.close();

//        System.out.println(result);
    }

}
