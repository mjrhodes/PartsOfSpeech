import java.util.ArrayList;

/**
 * Created by michael on 6/8/17.
 */
public class PartsOfSpeech {

    public static void main(String[] args) {
        //Our eventual project main class
        Parser p = new Parser();
        System.out.println("Training...");
        p.parse("training_dataset.txt");
        System.out.println("Done");
    }

}
