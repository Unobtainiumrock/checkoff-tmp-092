import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;

/**
 * Created by Jenny Huang on 3/12/19.
 */
public class TestMyTrieSet {
    
    
<<<<<<< HEAD
    @Test
    public void addTest() {
        MyTrieSet t = new MyTrieSet();
        t.add("hello");
        t.add("hey");
        t.add("world");
        t.add("work");
        System.out.println(t);
    }
=======
    //Write your own test for add! Do this before running the other tests.
>>>>>>> 0ec5ff8e9de8d302f54afb70881fa224d87f6057

    // assumes add/contains work
    @Test
    public void basicClearTest() {
<<<<<<< HEAD
         MyTrieSet t = new MyTrieSet();
         for (int i = 0; i < 455; i++) {
             t.add("hi" + i);
             //make sure put is working via contains
             assertTrue(t.contains("hi" + i));
         }
         t.clear();
         for (int i = 0; i < 455; i++) {
             assertFalse(t.contains("hi" + i));
         }
=======
        // MyTrieSet t = new MyTrieSet();
        // for (int i = 0; i < 455; i++) {
        //     t.add("hi" + i);
        //     //make sure put is working via contains
        //     assertTrue(t.contains("hi" + i));
        // }
        // t.clear();
        // for (int i = 0; i < 455; i++) {
        //     assertFalse(t.contains("hi" + i));
        // }
>>>>>>> 0ec5ff8e9de8d302f54afb70881fa224d87f6057
    }

    // assumes add works
    @Test
    public void basicContainsTest() {
<<<<<<< HEAD
         MyTrieSet t = new MyTrieSet();
         assertFalse(t.contains("waterYouDoingHere"));
         t.add("waterYouDoingHere");
         assertTrue(t.contains("waterYouDoingHere"));
=======
        // MyTrieSet t = new MyTrieSet();
        // assertFalse(t.contains("waterYouDoingHere"));
        // t.add("waterYouDoingHere");
        // assertTrue(t.contains("waterYouDoingHere"));
>>>>>>> 0ec5ff8e9de8d302f54afb70881fa224d87f6057
    }

    // assumes add works
    @Test
    public void basicPrefixTest() {
<<<<<<< HEAD
         String[] saStrings = new String[]{"same", "sam", "sad", "sap"};
         String[] otherStrings = new String[]{"a", "awls", "hello"};

         MyTrieSet t = new MyTrieSet();
         for (String s: saStrings) {
             t.add(s);
         }
         for (String s: otherStrings) {
             t.add(s);
         }

         List<String> keys = t.keysWithPrefix("sa");
         for (String s: saStrings) {
             assertTrue(keys.contains(s));
         }
         for (String s: otherStrings) {
             assertFalse(keys.contains(s));
         }
=======
        // String[] saStrings = new String[]{"same", "sam", "sad", "sap"};
        // String[] otherStrings = new String[]{"a", "awls", "hello"};

        // MyTrieSet t = new MyTrieSet();
        // for (String s: saStrings) {
        //     t.add(s);
        // }
        // for (String s: otherStrings) {
        //     t.add(s);
        // }

        // List<String> keys = t.keysWithPrefix("sa");
        // for (String s: saStrings) {
        //     assertTrue(keys.contains(s));
        // }
        // for (String s: otherStrings) {
        //     assertFalse(keys.contains(s));
        // }
>>>>>>> 0ec5ff8e9de8d302f54afb70881fa224d87f6057
    }
}
