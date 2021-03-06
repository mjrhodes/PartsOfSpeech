import java.util.ArrayList;

/**
 * Created by michael on 6/8/17.
 */
public class PartsOfSpeech {

    public static void main(String[] args) {
        //Our eventual project main class
        StructureLoader loader = new StructureLoader();
        loader.loadStructures("test.txt");
        Parser p = new Parser();
        ArrayList<String> obs = p.getObs("test.txt");  //Pass file name of file to use for testing
        System.out.println("Serializing...");
        ToJSON.serialize(obs, "obs");
        ToJSON.serialize(loader.getStates(), "states");
        ToJSON.serialize(loader.getStart_p(), "start_p");
        ToJSON.serialize(loader.getTrans_p(), "trans_p");
        ToJSON.serialize(loader.getEmit_p(), "emit_p");

        System.out.println("Running Viterbi...");
        JavaRunCommand.run();
    }

}
