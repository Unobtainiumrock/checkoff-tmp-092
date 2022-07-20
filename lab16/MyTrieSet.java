import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        for (Character TSKey: TrieSetMap.keySet()) {
            TrieSetMap.put(TSKey, null);
        }
    }

    @Override
    public boolean contains(String key) {
        // go through each character in key. Check if the value at that TSkey != null. If not, then move on to the next letter. If null, return false;

        HashMap<Character, HashMap> goThrough = TrieSetMap;
        for (int i = 0; i < key.length(); i++) {
            char keyC = key.charAt(i);
            if (goThrough.get(keyC) == null) {
                return false;
            } else {
                goThrough = goThrough.get(keyC);
            }
        }
        return true;
    }

    @Override
    public void add(String key) {
        //iterate over characters in KEY. Once find the TSKey that equals to the KEY's character, set that KEY's val
        //to be another TrieSetMap; continue to do so for the next character in KEY.
        MyTrieSet curr = this;
        for (int i = 0; i < key.length(); i++) { //example: add("cat")
            char keyC = key.charAt(i);
            if (!curr.containsKey(keyC)) {
                curr.put(keyC, new MyTrieSet());
            } else {
                curr = (MyTrieSet) curr.get(keyC);
                curr.put(keyC, new MyTrieSet());
            }
        }
        curr.put('!', new MyTrieSet());
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        return null;
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
