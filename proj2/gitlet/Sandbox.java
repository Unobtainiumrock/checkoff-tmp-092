package gitlet;

import java.io.*;
import java.util.*;


public class Sandbox {
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = gitlet.Utils.join(CWD, ".gitlet");
    public static final File STAGING_DIR = gitlet.Utils.join(GITLET_DIR, ".staging");
    public static final File COMMITS_DIR = gitlet.Utils.join(GITLET_DIR, ".");
    public static final File BRANCH_DIR = gitlet.Utils.join(COMMITS_DIR, "branches");
    public static final File BLOB_DIR = gitlet.Utils.join(GITLET_DIR, "blobs");
    public static Object obj = new Object();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File[] files = CWD.listFiles();

//        Iterate over all of the files in the CWD and print their paths.
        for (File f : files) {
            System.out.println(f.toString());
        }

        System.out.println("\n\n");

        // Grab the project directory.
        File projectDir = gitlet.Utils.join(CWD, "gitlet");
        System.out.println(projectDir);

        // Prints all files withing gitlet
        Arrays.stream(projectDir.listFiles()).toList().forEach(System.out::println);

//         Attempt to serialize all of them. (Do later)
        Thing a = new Thing(2);

        FileOutputStream fos = new FileOutputStream("test.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(a);
        oos.close();
        fos.close();
        System.out.println("Object has been serialized.");
        FileInputStream fis = new FileInputStream("test.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Thing b = (Thing) ois.readObject();
        ois.close();
        fis.close();
        System.out.println("Object has been de-serialized");
        System.out.println("a = " + a.i);
        System.out.println("b = " + b.i);

        // Test serializing a hashmap
        Map<String, byte[]> testMap = new HashMap<>();

        // Read file contents as a string?
//        FileOutputStream fos2 = new FileOutputStream("fos.txt"); // Destination file
//        ByteArrayOutputStream bos = new ByteArrayOutputStream(); // Stream to which we write

        File testText = Utils.join(CWD, "test.txt"); // Path to file to read contents from
        File fosTest = Utils.join(CWD, "fos.txt"); // Path of file to write to.
        byte[] testTextBytes = Utils.readContents(testText);

        Utils.writeContents(fosTest, testTextBytes); // buffered version of the stuff below.

//        bos.write(testTextBytes, 0, testTextBytes.length); // Gets byte[] of contents at test.txt and writes them to stream
//        bos.writeTo(fos2); // writes byte[] stream contents to fos.txt
//        bos.flush();
//        bos.close();

        // put that inside a hashmap
        // serialize hashmap
        // deserialize hashmap and attempt to get values, given a key

        // Map<String, byte[]>
        // writeObject

    }

    private static class Thing implements Serializable {
        int i;

        public Thing(int i) {
            this.i = i;
        }
    }
}