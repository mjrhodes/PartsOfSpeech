import com.google.gson.Gson;

import java.io.*;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by michael on 6/8/17.
 */


public class ToJSON {

    public static void main(String[] args) {
        //Temporary Main
        String[] obs = new String[] {"normal", "cold", "dizzy"};
        String[] states = new String[] {"Healthy", "Fever"};

        Dictionary<String, Double> start_p = new Hashtable<>();
        start_p.put("Healthy", 0.6);
        start_p.put("Fever", 0.4);

        //---- trans_p ----
        Dictionary<String, Dictionary<String, Double>> trans_p = new Hashtable<>();
        Dictionary<String, Double> trans_p_1 = new Hashtable<>();
        trans_p_1.put("Healthy", 0.7);
        trans_p_1.put("Fever", 0.3);

        Dictionary<String, Double> trans_p_2 = new Hashtable<>();
        trans_p_2.put("Healthy", 0.4);
        trans_p_2.put("Fever", 0.6);

        trans_p.put("Healthy", trans_p_1);
        trans_p.put("Fever", trans_p_2);
        //---- end ----

        //---- emit_p ----
        Dictionary<String, Dictionary<String, Double>> emit_p = new Hashtable<>();
        Dictionary<String, Double> emit_p_1 = new Hashtable<>();
        emit_p_1.put("normal", 0.5);
        emit_p_1.put("cold", 0.4);
        emit_p_1.put("dizzy", 0.1);

        Dictionary<String, Double> emit_p_2 = new Hashtable<>();
        emit_p_2.put("normal", 0.1);
        emit_p_2.put("cold", 0.3);
        emit_p_2.put("dizzy", 0.6);

        emit_p.put("Healthy", emit_p_1);
        emit_p.put("Fever", emit_p_2);
        //---- end ----

        ToJSON.serialize(obs, "obs");
        ToJSON.serialize(states, "states");
        ToJSON.serialize(start_p, "start_p");
        ToJSON.serialize(trans_p, "trans_p");
        ToJSON.serialize(emit_p, "emit_p");

        JavaRunCommand.run();
    }

    public static void serialize(Object o, String fileName) {
        Gson gson = new Gson();
        File file = new File(fileName + ".json");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String result = gson.toJson(o);
        pw.print(result);
        pw.close();
    }
}
