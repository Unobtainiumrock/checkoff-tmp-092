//package gitlet;
//
//import java.io.*;
//import java.util.*;
//
//
//public class Sandbox {
//    public static final File CWD = new File(System.getProperty("user.dir"));
//    public static final File GITLET_DIR = gitlet.Utils.join(CWD, ".gitlet");
//    public static final File STAGING_DIR = gitlet.Utils.join(GITLET_DIR, ".staging");
//    public static final File COMMITS_DIR = gitlet.Utils.join(GITLET_DIR, ".");
//    public static final File BRANCH_DIR = gitlet.Utils.join(COMMITS_DIR, "branches");
//    public static final File BLOB_DIR = gitlet.Utils.join(GITLET_DIR, "blobs");
//    public static Object obj = new Object();
//
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        File[] files = CWD.listFiles();
//
////        Iterate over all of the files in the CWD and print their paths.
//        for (File f : files) {
//            System.out.println(f.toString());
//        }
//
//        System.out.println("\n\n");
//
//        // Grab the project directory.
//        File projectDir = gitlet.Utils.join(CWD, "gitlet");
//
//        // Grab the package
//        File pkg = projectDir.listFiles()[0];
//
//        // Grab a list of all the files within the gitlet package.
//        File[] projectFiles = pkg.listFiles();
//
//
//        // Iterate over and print all of the java files.
//        Arrays.asList(projectFiles).forEach((file) -> {
//            System.out.println(file + " " + file.exists());
//        });
//
//                GITLET_DIR.mkdir();
//
//        // Attempt to serialize all of them. (Do later)
//        //        Thing a = new Thing(2);
//        //
//        //        FileOutputStream fos = new FileOutputStream("test.ser");
//        //        ObjectOutputStream oos = new ObjectOutputStream(fos);
//        //        oos.writeObject(a);
//        //        oos.close();
//        //        fos.close();
//        //        System.out.println("Object has been serialized.");
//        //        FileInputStream fis = new FileInputStream("tes.ser");
//        //        ObjectInputStream ois = new ObjectInputStream(fis);
//        //        Thing b = (Thing) ois.readObject();
//        //        ois.close();
//        //        fis.close();
//        //        System.out.println("Object has been de-serialized");
//        //        System.out.println("a = " + a.i);
//        //        System.out.println("b = " + b.i);
//
//    }
//
//    private static class Thing implements Serializable {
//        int i;
//
//        public Thing(int i) {
//            this.i = i;
//        }
//    }
//}