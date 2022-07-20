import org.w3c.dom.Node;

import java.util.*;

public class MyTrieSet extends HashMap<Character, HashMap> implements TrieSet61BL {

    private Node _root; //think about how to incorporate this
    private boolean _endFlag; //maybe implement this another way?

    HashMap<Character, HashMap> TrieSetMap = new HashMap();

//    public MyTrieSet() {
//        for (char c = 'A'; c <= 'Z'; c++) {
//            TrieSetMap.put(c, null);
//        }
//    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public boolean contains(String key) {
        // go through each character in key. Check if the value at that TSkey != null. If not, then move on to the next letter. If null, return false;
        MyTrieSet curr = this;
        for (int i = 0; i < key.length(); i++) {
            char keyC = key.charAt(i);
            if (curr.get(keyC) == null) {
                return false;
            } else {
                curr = (MyTrieSet) curr.get(keyC);
            }
        }
        if (!curr.containsKey('!')) {
            return false;
        }
        return true;
    }

    @Override
    public void add(String key) {
        //iterate over characters in KEY. Once find the TSKey that equals to the KEY's character, set that KEY's val
        //to be another TrieSetMap; continue to do so for the next character in KEY.
        MyTrieSet curr = this;
        for (int i = 0; i < key.length(); i++) { //example: add("hello") add("hellu")
            char keyC = key.charAt(i);
            if (!curr.containsKey(keyC)) {
                curr.put(keyC, new MyTrieSet());
                curr = (MyTrieSet) curr.get(keyC);
            } else {
                curr = (MyTrieSet) curr.get(keyC);
//                curr.put(keyC, new MyTrieSet());
            }
        }
        curr.put('!', new MyTrieSet());
    }

//    Step 1: Verify that the prefix actually exists in your trie! If the prefix is an empty string or that sequence of characters isn’t in there, you should return
//    an empty List.
//    Step 2: Once you’ve verified that the prefix exists in your trie, you can recursively assemble all of the Strings in your trie containing that prefix.
//    As you go, you should check if a given Node is a valid key, then call that recursive method on the String you have so far, concatenated with the character
//    at that node (do this for every valid child of your given Node)!
    @Override
    public List<String> keysWithPrefix(String prefix) {
        //"reenter" "redraw" "redeem", "correct" prefix = re Output: <reenter, redraw, redeem
        List<String> result = new ArrayList<String>();
        if (prefix == "") {
            return result;
        }
        MyTrieSet curr = this;
        String t = "";
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (curr.containsKey(c)) {
                t += c;
            }
            curr = (MyTrieSet) curr.get(c);
        }
        if (!t.equals(prefix)) {
            return result;
        }

        String[] pre = {prefix};
        String[] currWord = {prefix};
        String[] currChar = {Character.toString(prefix.charAt(0))};
        DFS(curr, result, pre, currWord, currChar);
        return result;
    }

    private void DFS(MyTrieSet current, List output, String[] pre, String[] currWord, String[] currChar) {
        Iterator<Character> iter = current.keySet().iterator(); //current = "
        while (iter.hasNext()) {
            currChar[0] = Character.toString(iter.next());
//            if (x == '!') {
//
//            }
            currWord[0] += currChar[0];
            DFS((MyTrieSet) current.get(currChar[0]), output, pre, currWord, currChar);
            currWord[0] = pre[0];
        }
        if (!currWord[0].equals(pre[0]) && !currChar[0].equals('!')) {
            output.add(currWord[0]);
        }
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
