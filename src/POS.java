import java.util.*;

/**
 * Created by michael on 6/9/17.
 */
public class POS {
    String name;
    Dictionary<String, Counter> followers;
    Dictionary<String, Counter> words;

    public POS(String name) {
        this.name = name;
        followers = new Hashtable<>();
        words = new Hashtable<>();
    }

    public String getName() {
        return name;
    }

    public Dictionary<String, Counter> getFollowers() {
        return followers;
    }

    public Dictionary<String, Counter> getWords() {
        return words;
    }

    public void addFollower(String POSname) {
        Counter value = followers.get(POSname);
        if (value == null) {
            followers.put(POSname, new Counter(1));
        } else {
            value.inc();
        }
    }

    public void addWord(String word) {
        Counter value = words.get(word);
        if (value == null) {
            words.put(word, new Counter(1));
        } else {
            value.inc();
        }
    }

    public int getTotalFollowers() {
        int total = 0;
        Enumeration<Counter> e = followers.elements();
        while(e.hasMoreElements()) {
            total += e.nextElement().getCount();
        }
        return total;
    }

    public int getTotalWords() {
        int total = 0;
        Enumeration<Counter> e = words.elements();
        while(e.hasMoreElements()) {
            total += e.nextElement().getCount();
        }
        return total;
    }
}
