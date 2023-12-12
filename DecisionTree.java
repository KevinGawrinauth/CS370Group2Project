import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class DecisionTree {
	private int attributes;//number of attributes tree considers
	protected String attribute[];//attributes tree considers (no repeated attribute within single tree)
	protected Iris dataset[];//dataset used (only root can see entire set)
	protected TreeNode root;
	private int maxDepth;
	
	private float [] interval;//possible threshold values for each continuous variable	
	private float [] min;//min value of each continuous variable
	private float [] max;//max
	
	DecisionTree(int m, String [] at, Iris [] data){
		maxDepth = m;
		attribute = at;
		dataset = data;
		if(attribute != null) {
			attributes = attribute.length;
			interval = new float[attributes];
			min = new float[attributes];
			max = new float[attributes];
		}
	}
	
	void buildTree() {
		calculateInterval();
		root = buildingTree(0, dataset);//root is always depth 0, root has entire data set to look through
	}
	
	public void calculateInterval() {//find good threshold values for each continuous variable independently
		float avg = 0;
		float v;
		int i = 0;
		if(attribute[0].equals("Iris Species"))//skip categorial attribute
			i = 1;
		
		while(i<interval.length) {//find interval for each attribute
			min[i] = 0;
			max[i] = 10;
			avg = 0;
			
			for(int j = 0; j<dataset.length; j++) {
				//System.out.println(attribute[i]);
				v = (float) dataset[j].getByName(attribute[i]);
				avg += v;
				
				if(v>max[i])
					max[i] = v;
				else if(v<min[i])
					min[i] = v;
			}
			avg /= (float)dataset.length;
			interval[i] = (float) (avg-min[i]/10.0);
			i++;
		}
	}
	
	
	//needs pass subset of dataset applicable to current branch
	TreeNode buildingTree(int depth, Iris [] set) {
		if(depth == maxDepth-1) {//max depth reached
			DecisionNode n = new DecisionNode();
			int seto = 0, veri = 0, virg = 0;
			for(int i = 0; i<set.length; i++) {
				if(set[i].getSpecies().equals("setosa"))
					seto++;
				else if(set[i].getSpecies().equals("versicolor"))
					veri++;
				else
					virg++;
			}
			if(seto > veri && seto > virg)
				n.setDecision("setosa");
			else if (veri > seto && veri > virg)
				n.setDecision("versicolor");
			else 
				n.setDecision("virginica");
			return n;
		}
		
		float giniNoSplit = calculateGini(null,null,set);
		if(giniNoSplit == 0) {//current set contains 1 class
			DecisionNode n = new DecisionNode();
			if(set[0].getSpecies().equals("setosa"))
				n.setDecision("setosa");
			else if(set[0].getSpecies().equals("versicolor"))
				n.setDecision("versicolor");
			else
				n.setDecision("virginica");
			
			return n;
		}
		
		float gain = (float) 0.0;
		String chooseSplit = "";//final best split
		Object splitValue = null;//final best split value
		float giniSplit;
		int find = 0;
		if(attribute[0].equals("Iris Species")) {
			find = 1;
			giniSplit = calculateGini(attribute[0], "setosa", set);
			if(gain < giniNoSplit - giniSplit) {
				gain = giniNoSplit - giniSplit;
				chooseSplit = "Iris Species";
				splitValue = "setosa";
			}
			if(gain == giniNoSplit) {//perfect split
				find = attributes;
			}
		}
		
		while(find < attributes) {//find best split from continuous variable
			for(float thresHold = min[find] + interval[find]; thresHold<max[find]; thresHold += interval[find]) {
				giniSplit = calculateGini(attribute[find], thresHold, set);
				if(gain < giniNoSplit - giniSplit) {
					gain = giniNoSplit - giniSplit;
					chooseSplit =  attribute[find];
					splitValue = thresHold;
				}
				if(gain == giniNoSplit)//perfect split 
					break;
			}
			find++;
		}
		
		if(gain == 0.0) {//no better split found
			DecisionNode d = new DecisionNode();
			int seto = 0, veri = 0, virg = 0;
			for(int i = 0; i<set.length; i++) {
				if(set[i].getSpecies().equals("setosa"))
					seto++;
				else if(set[i].getSpecies().equals("versicolor"))
					veri++;
				else
					virg++;
			}
			if(seto > veri && seto > virg)
				d.setDecision("setosa");
			else if (veri > seto && veri > virg)
				d.setDecision("versicolor");
			else 
				d.setDecision("virginica");
			return d;
		}
		
		BranchNode m = new BranchNode();//a better split found
		m.setComparisonAttribute(chooseSplit);
		m.setComparisonValue(splitValue);
		Iris [] left;
		Iris [] right;
		int side = 0;//amount in left branch
		
		for(int check = 0; check<set.length; check++) {//count iris in left and right branch
			if(chooseSplit.equals("Iris Species")){
				if(set[check].getSpecies().equals("Species"))
					side++;
			}
			else {
				if((float) set[check].getByName(chooseSplit) < (float) splitValue)
					side++;
			}
		}
		left = new Iris[side];
		right = new Iris[set.length - side];
		
		int l = 0, r = 0;
		for(int check = 0; check<set.length; check++) {//assign iriss to correct branch
			if(chooseSplit.equals("Iris Species")){
				if(set[check].getSpecies().equals("setosa"))
					left[l++] = set[check]; 
				else
					right[r++] = set[check];
			}
			else {
				if((float) set[check].getByName(chooseSplit) < (float) splitValue)
					left[l++] = set[check];
				else
					right[r++] = set[check];
			}
		}
		
		m.left = buildingTree(depth+1, left);
		m.right = buildingTree(depth+1, right);
		
		return m;
	}
	
	//calculate gini value of splitting set using attribute and value
	//if attribute is null calculate without split
	public float calculateGini(String atr, Object value, Iris [] set) {
		if(atr == null) {
			int s = set.length;
			int good = 0;
			int bad = 0;
			for(int c = 0; c<s; c++) {//count each good and bad quality iris
				if(set[c].getSpecies().equals("setosa"))
					good++;
				else
					bad++;
			}
			
			float p1 = (float)good/(float)s;
			float p2 = (float)bad/(float)s;
			float g = (float)((p1 *(1.00 - p1) ) + (p2 * (1.00-p2)));
			return g;
		}
		
		int leftGood = 0, leftBad = 0, rightGood = 0, rightBad = 0;
		for(int i = 0; i<set.length; i++) {
			if(atr.equals("Iris Species")) {
				if(set[i].getSpecies().compareTo((String) value) < 0) {
					if(set[i].getSpecies().equals("setosa"))
						leftBad++;
					else
						leftGood++;
				}
				else {
					if(set[i].getSpecies().equals("versicolor"))
						rightBad++;
					else
						rightGood++;
				}
			}
			else {
				float getV = (float) set[i].getByName(atr);
				float cV = (float) value;
				if(getV < cV) {
					if(set[i].getSpecies().equals("versicolor"))
						leftBad++;
					else
						leftGood++;
				}
				else {
					if(set[i].getSpecies().equals("versicolor"))
						rightBad++;
					else
						rightGood++;
				}
			}
		}
		
		float tL = (leftBad + leftGood);
		float p1L = (float)leftBad/tL;
		float p2L = (float)leftGood/tL;
		
		float tR = (rightBad + rightGood);
		float p1R = (float)rightBad/tR;
		float p2R = (float)rightGood/tR;
		
		float gL = (float) ((p1L * (1.0 - p1L)) + (p2L * (1.0 - p2L)));
		float gR = (float) ((p1R * (1.0 - p1R)) + (p2R * (1.0 - p2R)));
		return (((tL/set.length) * gL) + ((tR/set.length) * gR));
	}
	
	public String test(Iris w) {//test iris through tree

		TreeNode p = root;
		
		while(p instanceof BranchNode) {//loop while p has children
			p = ((BranchNode) p).compare(w);
		}
		
		return ((DecisionNode)p).getDecision();
	}
	
	
	public void printTree(BufferedWriter writeF) throws IOException {
		printTreeR(root,writeF);
		writeF.write("\n");
	}
	
	private void printTreeR(TreeNode r, BufferedWriter writeF) throws IOException {//preorder write
		if(r == null)
			return;
		
		if(r instanceof BranchNode) {
			((BranchNode)r).printNode(writeF);
			printTreeR(((BranchNode)r).left, writeF);
			printTreeR(((BranchNode)r).right, writeF);
		}
		else if(r instanceof DecisionNode) {
			((DecisionNode)r).printNode(writeF);
		}
		
		writeF.flush();
	}
	
	public void readTree(Scanner read) {
		root = readingTree(read);
	}
	
	private TreeNode readingTree(Scanner read) {
		String s = read.nextLine();
		
		if(s.equalsIgnoreCase("branch node")) {//create branch node
			BranchNode b = new BranchNode();
			s = read.nextLine();
			b.setComparisonAttribute(s);
			if(s.equalsIgnoreCase("Iris-setosa")||s.equalsIgnoreCase("Iris-versicolor")||s.equalsIgnoreCase("Iris-virginica"))
				b.setComparisonValue(read.nextLine());
			else {
				s = read.nextLine();
				Float f = Float.parseFloat(s);
				b.setComparisonValue(f);
			}
				
			b.left = readingTree(read);
			b.right = readingTree(read);
			return b;
		}
		
		//if branch node not created decision node created
		DecisionNode d = new DecisionNode();
		d.setDecision(read.nextLine());
		return d;
	}
}
 