import org.w3c.dom.Node;

import java.util.*;

public class MyTrieSet extends HashMap<Character, HashMap> implements TrieSet61BL {
    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public boolean contains(String key) {
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
        MyTrieSet curr = this;
        for (int i = 0; i < key.length(); i++) { //example: add("hello") add("hellu")
            char keyC = key.charAt(i);
            if (!curr.containsKey(keyC)) {
                curr.put(keyC, new MyTrieSet());
            }
            curr = (MyTrieSet) curr.get(keyC);
        }
        curr.put('!', new MyTrieSet());
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> result = new ArrayList<>();

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
        DFS(curr, result, pre, currWord);
        return result;
    }

    private void DFS(MyTrieSet t, List<String> out, String[] pre, String[] currWord) {
        for (Character c : t.keySet()) {
            if (c.equals('!')) {
                out.add(currWord[0]);
                continue;
            }
            currWord[0] += Character.toString(c);
            DFS((MyTrieSet) t.get(c), out, pre, currWord);
            currWord[0] = pre[0];
        }
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
