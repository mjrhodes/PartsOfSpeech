import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by michael on 6/8/17.
 */
public class Parser {
    ArrayList<String> obs;
    ArrayList<String> states;
    Dictionary<String, Double> start_p;
    Dictionary<String, Dictionary<String, Double>> trans_p;
    Dictionary<String, Dictionary<String, Double>> emit_p;
    ArrayList<POS> posA;

    public Parser() {
        obs = new ArrayList<>();
        states = new ArrayList<>();
        start_p = new Hashtable<>();
        trans_p = new Hashtable<>();
        emit_p = new Hashtable<>();
        posA = new ArrayList<>();
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
            int index = POSindex(prevPOS);
            if (index == -1) {
                POS newPos = new POS(prevPOS);
                newPos.add(label);
                posA.add(newPos);
            } else {
                posA.get(index).add(label);
            }
            prevPOS = label;
        }
    }

    public ArrayList<String> getObs() {
        return obs;
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public Dictionary<String, Double> getStart_p() {
        return start_p;
    }

    public Dictionary<String, Dictionary<String, Double>> getTrans_p() {
        return trans_p;
    }

    public Dictionary<String, Dictionary<String, Double>> getEmit_p() {
        return emit_p;
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
