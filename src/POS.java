import java.util.*;

/**
 * Created by michael on 6/9/17.
 */
public class POS {
    String name;
    Dictionary<String, Counter> followers;
    Dictionary<String, Counter> words;
    int totalFollowers;
    int totalWords;

    public POS(String name) {
        this.name = name;
        followers = new Hashtable<>();
        words = new Hashtable<>();
        totalFollowers = 0;
        totalWords = 0;
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

    public int getTotalFollowers() {
        return totalFollowers;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public void addFollower(String POSname) {
        Counter value = followers.get(POSname);
        if (value == null) {
            followers.put(POSname, new Counter(1));
        } else {
            value.inc();
        }
        totalFollowers++;
    }

    public void addWord(String word) {
        Counter value = words.get(word);
        if (value == null) {
            words.put(word, new Counter(1));
        } else {
            value.inc();
        }
        totalWords++;
    }
}
