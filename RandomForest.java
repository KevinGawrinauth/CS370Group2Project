import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class RandomForest {
	private int treeNum;//number of trees to create (pick odd number to prevent ties)
	private DecisionTree forest[];//set size to treeNum
	private int maxDepth;//max depth each tree can get
	private String attributes[];// all possible attributes
	private Iris dataSet[];//all data from dataset
	private int setSize;//dataset size forest has
	private int treeDataSize;//amount from dataset each tree receives
	
	RandomForest(int tree, int depth, String at[]){
		treeNum = tree;
		maxDepth = depth;
		attributes = at;
		forest = new DecisionTree[treeNum];
	}

	public Iris[] returnSet(){
		return dataSet;
	}
	
	public void loadData(Scanner IrisScan) {
		int i = 0;
		dataSet = new Iris[150];
		String s;
		String [] atr;
		Iris in;
		String q;
		//files are csv files, first line is order of attributes
		//data set has no missing attributes
		s = IrisScan.nextLine();
		while(IrisScan.hasNextLine()) {
			s = IrisScan.nextLine();
			if(!s.isEmpty()){
				atr = s.split(";");
				in = new Iris();
				in.setSepalLength(Float.parseFloat(atr[0]));
				in.setSepalWidth(Float.parseFloat(atr[1]));
				in.setPetalLength(Float.parseFloat(atr[2]));
				in.setPetalWidth(Float.parseFloat(atr[3]));
			
				q = (atr[4]);
			
				if(q.equalsIgnoreCase("setosa"))
					in.setSpecies("setosa");
				else if(q.equalsIgnoreCase("versicolor"))
					in.setSpecies("versicolor");
				else
					in.setSpecies("virginica");

				dataSet[i] = in;
				i++;
			}
		}	
		setSize = 150;
	}
	
	
	
	public void buildForest(){
		treeDataSize = (setSize * 2)/3;
		int a = attributes.length;
		Random r = new Random();//random number generator
		Iris [] dataR;
		int x;//number of attributes for singular tree
		boolean [] selected = new boolean[a];//array for all possible attributes
		int s;//an selected attribute
		
		for(int i = 0; i<treeNum; i++) {//create each tree
			for(int j = 0; j<a; j++)//set to no selected attributes for tree to be created
				selected[j] = false;
			
			x = r.nextInt(a);//0-(attributes-1)
			x++;//1 - attributes
			
			int k = 0;
			if(x == 4) {//if tree would take all attributes
				k = 4;
				for(int m = 0; m<4; m++)
					selected[m] = true;
			}
			
			while(k<x) {//select attributes randomly
				s = r.nextInt(a);//select attribute by index
				if(selected[s] == false) {//select x amount of different attributes
					selected[s] = true;
					k++;
				}
			}
	
			String [] atrTree = new String[x];
			k = 0;
			for(int apply = 0; apply<a; apply++) {//array of selected attributes
				if(selected[apply]) {
					atrTree[k] = attributes[apply];
					k++;
				}
			}
			
			dataR = new Iris[treeDataSize];
			//create data subset from determined size(can include repeats of the same instance)
			for(int d = 0; d<treeDataSize; d++)
				dataR[d] = dataSet[(r.nextInt(dataSet.length))];//select random iris in data set
			
			forest[i] = new DecisionTree(maxDepth, atrTree, dataR);
			forest[i].buildTree();
		}
	}
	
	
	public String test(Iris I) {
		String answer;
		int class1 = 0;//good
		int class2 = 0;//bad
		int class3 = 0;
		for(int i = 0; i<treeNum; i++) {
			answer = forest[i].test(I);
			if(answer.equals("setosa"))
				class1++;
			else if(answer.equals("versicolor"))
				class2++;
			else class3++;
		}
		
		if(class1 > class2 && class1 > class3)//return majority answer
			return "setosa";
		else if(class2 > class1 && class2 > class3)
			return "versicolor";
		else if(class3 > class1 && class3 > class2)
			return "virginica";
		else
			return "uncertain";
	}
	
	public void writeForest(BufferedWriter writeF) throws IOException {
		writeF.write("Random forest for iris plant\n"+treeNum+"\n\n");
		for(int i = 0; i<treeNum; i++)
			forest[i].printTree(writeF);
	}
	
	public void readForest(Scanner read) {
		String r = read.nextLine();
		r = read.nextLine();
		if(!r.isEmpty()){
			treeNum = Integer.parseInt(r);
			forest =  new DecisionTree[treeNum];
			for(int i = 0; i<treeNum; i++) {
				forest[i] = new DecisionTree(maxDepth, null, null);
				
				r = read.nextLine();//skip empty line
				forest[i].readTree(read);
			}	
		} 
	}
	
	public Iris getIris() {
		Random r = new Random();
		int x = r.nextInt(setSize);
		return dataSet[x];
	}
}
