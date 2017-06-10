import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by michael on 6/9/17.
 */
public class StructureLoader {
    ArrayList<String> obs;
    ArrayList<String> states;
    Dictionary<String, Double> start_p;
    Dictionary<String, Dictionary<String, Double>> trans_p;
    Dictionary<String, Dictionary<String, Double>> emit_p;

    public void loadStructures(String fileName) {
        Parser p = new Parser();
        System.out.println("Parsing File...");
        p.parse(fileName);
        System.out.println("Loading Structures...");
        obs = new ArrayList<>(p.getWords());
        states = new ArrayList<>(p.getLabels());
        loadStart_p();
        loadTrans_p(p.getPosA());
        loadEmit_p(p.getPosA());
    }

    private void loadStart_p() {
        start_p = new Hashtable<>();
        double prob = 1.0/(double)states.size();
        for (String state : states) {
            start_p.put(state, prob);
        }
    }

    private void loadTrans_p(ArrayList<POS> posArray) {
        trans_p = new Hashtable<>();
        for (POS pos : posArray) {
            Dictionary<String, Counter> followers = pos.getFollowers();
            Enumeration<String> e = followers.keys();
            Dictionary<String, Double> innerDict = new Hashtable<>();
            while(e.hasMoreElements()) {
                String label = e.nextElement();
                Double prob = (double)followers.get(label).getCount()/(double)pos.getTotalFollowers();
                innerDict.put(label, prob);
            }
            trans_p.put(pos.getName(), innerDict);
        }
    }

    private void loadEmit_p(ArrayList<POS> posArray) {
        emit_p = new Hashtable<>();
        for (POS pos : posArray) {
            Dictionary<String, Counter> words = pos.getWords();
            Enumeration<String> e = words.keys();
            Dictionary<String, Double> innerDict = new Hashtable<>();
            while(e.hasMoreElements()) {
                String word = e.nextElement();
                Double prob = (double)words.get(word).getCount()/(double)pos.getTotalWords();
                innerDict.put(word, prob);
            }
            emit_p.put(pos.getName(), innerDict);
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
}
