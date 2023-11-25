import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class IrisClassifier {
	public static Iris [] search;//data set from file
		
	public static void main(String[] args) throws FileNotFoundException {
		String [] a = new String[5];//strings of iris attributes
		a[0] = "Iris Species";//species, categorical attribute, should be first
		a[1] = "Sepal Length";
		a[2] = "Sepal Width";
		a[3] = "Petal Length";
		a[4] = "Petal Width";

		Scanner IrisScanner = null;
		try {//attempt to open data set files
			IrisScanner = new Scanner(new FileReader("iris.csv"));
        }
		catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Iris date set files not found. Program closing."); 
			System.exit(1);
		}
		
		RandomForest rf = new RandomForest(5,4,null);
		rf.loadData(IrisScanner);
		search = rf.returnSet();
		
		Scanner read = null;
		try {//attempt to open file to construct random forest
			read =  new Scanner(new FileReader("forestFileFinal.txt"));
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Random Forest file for classification not found.\nProgram closing."); 
			System.exit(1);
		}

		rf.readForest(read);//create forest and all its trees
		
		float defaultVal[] = new float[3];
		for(int i = 0; i<3; i++){
			defaultVal[i] = 0;
		}

		//find average values to use as default values to handle missing data
		for(int av = 0; av<search.length; av++) {
			defaultVal[0] += search[av].getSepalLength();
			defaultVal[1] += search[av].getSepalWidth();
			defaultVal[2] += search[av].getPetalLength();
			defaultVal[3] += search[av].getPetalWidth();
		}
		
		//order of default values is same for string of attributes(excluding color)
		defaultVal[0] /= (float)search.length;
		defaultVal[1] /= (float)search.length;
		defaultVal[2] /= (float)search.length;
		defaultVal[3] /= (float)search.length;
		
		//close all files
		read.close();
		//GUISwitch wg = new GUISwitch(rf, defaultVal);//open GUI
	}
	    
	//find a iris from data set that is similar to passed iris
	//iris is similar if at least 4 attributes are close
	public static Iris findIris(Iris q) {
		int sim;
		float comp;
		for(int j=0; j<150; j++){ //size of dataset
			sim=1;
			if(!q.getSpecies().equals (search[j].getSpecies())) //Iris color is string
				continue;	
					
			comp = (q.getSepalLength()/10.0f); //10% of the input
			//if iris is + or - 10% of given iris it has similar value for attribute
			if(q.getSepalLength()-comp <= (search[j].getSepalLength()) && (q.getSepalLength()+comp)>=(search[j].getSepalLength()))
				sim++;

			comp =(q.getSepalWidth()/10.0f);
			if(q.getSepalWidth()-comp <= (search[j].getSepalWidth()) && (q.getSepalWidth()+comp) >= (search[j].getSepalWidth()))
				sim++; 
			
			comp = (q.getPetalLength()/10.0f);
			if(q.getPetalLength()-comp <= (search[j].getPetalLength()) && (q.getPetalLength()+comp) >= (search[j].getPetalLength()))
				sim++;

			comp = (q.getPetalWidth()/10.0f);
			if(q.getPetalLength()-comp<=(search[j].getPetalLength()) && (q.getPetalLength()+comp) >= (search[j].getPetalLength()))
				sim++;

		
			if(sim>=4) //if there are at least 4 similarities between iris, return as answer
				return search[j];
			}
			return null;//no similar iris found
	}
}