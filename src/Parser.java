import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by michael on 6/8/17.
 */
public class Parser {
    ArrayList<POS> posA;
    Set<String> words;
    Set<String> labels;

    public Parser() {
        posA = new ArrayList<>();
        words = new HashSet<>();
        labels = new HashSet<>();
    }

    public void parse(String fileName) {
        File file = new File(fileName);
        Scanner s = null;
        try {
            s = new Scanner(new BufferedInputStream(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String prevPOS = ".";
        while(s.hasNext()) {
            String element = s.next();
            String word = getWord(element).toLowerCase();
            String label = getLabel(element);

            words.add(word);
            labels.add(label);

            //Add word to part of speech stored in posA or add if not there
            int index = POSindex(label);
            if (index == -1) {
                POS newPos = new POS(label);
                newPos.addWord(word);
                posA.add(newPos);
            } else {
                posA.get(index).addWord(word);
            }

            //Add follower to part of speech stored in posA or add if not there
            index = POSindex(prevPOS);
            if (index == -1) {
                POS newPos = new POS(prevPOS);
                newPos.addFollower(label);
                posA.add(newPos);
            } else {
                posA.get(index).addFollower(label);

            }
            prevPOS = label;
        }
    }

    public ArrayList<POS> getPosA() {
        return posA;
    }

    public Set<String> getWords() {
        return words;
    }

    public Set<String> getLabels() {
        return labels;
    }

    private String getWord(String element) {
        //given word_label (cat_NN) strip off _label
        //return word (cat)
        int index = element.indexOf('_');
        String s = element.substring(0,index);
        return s;
    }

    private String getLabel(String element) {
        //given word_label (cat_NN) strip off word_
        //return label (NN)
        int index = element.indexOf('_');
        String s = element.substring(index+1);
        return s;
    }

    private int POSindex(String name) {
        for (int i = 0; i < posA.size(); i++) {
            if (posA.get(i).getName().equals(name)) return i;
        }
        return -1;
    }
}
