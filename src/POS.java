import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by michael on 6/9/17.
 */
public class POS {
    String name;
    Dictionary<String, Counter> next_p;

    public POS(String name) {
        this.name = name;
        next_p = new Hashtable<>();
    }

    public String getName() {
        return name;
    }

    public Dictionary<String, Counter> getNext_p() {
        return next_p;
    }

    public void add(String POSname) {
        if (hasKey(POSname)) {
            Counter value = next_p.get(POSname);
            value.inc();
        } else {
            next_p.put(POSname, new Counter(1));
        }
    }

    private boolean hasKey(String arg) {
        Enumeration<String> key = next_p.keys();
        while(key.hasMoreElements()) {
            if (key.nextElement().equals(arg)) return true;
        }
        return false;
    }
}
