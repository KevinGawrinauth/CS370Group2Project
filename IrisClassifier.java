import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class IrisClassifier {
    public static Iris[] search; // data set from file

    public static void main(String[] args) throws FileNotFoundException {
        String[] a = new String[5]; // strings of iris attributes
        a[0] = "Iris Species"; // species, categorical attribute, should be first
        a[1] = "Sepal Length";
        a[2] = "Sepal Width";
        a[3] = "Petal Length";
        a[4] = "Petal Width";

        Scanner IrisScanner = null;
        try {// attempt to open data set files
            IrisScanner = new Scanner(new FileReader("iris.csv"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Iris data set files not found. Program closing.");
            System.exit(1);
        }

        RandomForest rf = new RandomForest(10, 5, null);
        rf.loadData(IrisScanner);
        search = rf.returnSet(); // Initialize the search array

        // Open file to construct the random forest
        Scanner read = null;
        try {
            read = new Scanner(new FileReader("forestFileFinal.txt"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Random Forest file for classification not found.\nProgram closing.");
            System.exit(1);
        }

        rf.readForest(read); // create forest and all its trees

        float defaultVal[] = new float[4];
        for (int i = 0; i < 4; i++) {
            defaultVal[i] = 0;
        }

        // find average values to use as default values to handle missing data
        for (int av = 0; av < search.length; av++) {
            defaultVal[0] += search[av].getSepalLength();
            defaultVal[1] += search[av].getSepalWidth();
            defaultVal[2] += search[av].getPetalLength();
            defaultVal[3] += search[av].getPetalWidth();
        }

        // order of default values is the same for the string of attributes (excluding
        // species)
        defaultVal[0] /= (float) search.length;
        defaultVal[1] /= (float) search.length;
        defaultVal[2] /= (float) search.length;
        defaultVal[3] /= (float) search.length;

        // close all files
        read.close();
        IrisScanner.close();
        new GUISwitch(rf, defaultVal);// open GUI
    }

    // find an iris from the data set that is similar to the passed iris
    // iris is similar if at least 4 attributes are close
    public static Iris findIris(Iris q) {
        final int MIN_SIMILARITIES = 3;
        final float ATTRIBUTE_TOLERANCE = 0.10f;

        for (int j = 0; j < 150; j++) { // size of dataset
            int similarities = 1; // Initialized to 1 for species match
            if (!q.getSpecies().equals(search[j].getSpecies())) // Iris color is a string
                continue;

            // Check similarities for each attribute
            similarities += isAttributeSimilar(q.getSepalLength(), search[j].getSepalLength(), ATTRIBUTE_TOLERANCE)
                    ? 1
                    : 0;
            similarities += isAttributeSimilar(q.getSepalWidth(), search[j].getSepalWidth(), ATTRIBUTE_TOLERANCE) ? 1
                    : 0;
            similarities += isAttributeSimilar(q.getPetalLength(), search[j].getPetalLength(), ATTRIBUTE_TOLERANCE)
                    ? 1
                    : 0;
            similarities += isAttributeSimilar(q.getPetalWidth(), search[j].getPetalWidth(), ATTRIBUTE_TOLERANCE) ? 1
                    : 0;

            if (similarities >= MIN_SIMILARITIES) // if there are at least 3 similarities between iris, return as answer
                return search[j];
        }
        return null; // no similar iris found
    }

    // Helper method to check if two attributes are similar within a given tolerance
    private static boolean isAttributeSimilar(float attribute1, float attribute2, float tolerance) {
        float comp = attribute1 * tolerance;
        return attribute1 - comp <= attribute2 && attribute1 + comp >= attribute2;
    }
}
