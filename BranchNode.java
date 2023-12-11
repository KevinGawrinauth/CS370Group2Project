import java.io.BufferedWriter;
import java.io.IOException;

public class BranchNode implements TreeNode{//internal node
	private String comparisonAttribute;
	private Object comparisonValue;
	protected TreeNode left;
	protected TreeNode right;
	
	TreeNode compare(Iris i) {		
		int c = 0;
		if(comparisonAttribute.equals("Iris Species")) {
			c = (i.getSpecies().compareTo((String) comparisonValue));
		}
		else if(i.getByName(comparisonAttribute) != null) {
			if((float)i.getByName(comparisonAttribute) < (float) comparisonValue)
				c = -1;
			else
				c = 1;	
		}
		//if i.attribute is less than comparisonValue return left
		if(c < 0)
			return left;
		
		return right;//else return right
	}
	
	public void printNode(BufferedWriter writeF) throws IOException {//writes all info of specific node(do not write left or right nodes)
		writeF.write("branch node\n" + comparisonAttribute + "\n" + comparisonValue.toString() +"\n");
	}

	public String getComparisonAttribute() {
		return comparisonAttribute;
	}

	public void setComparisonAttribute(String comparisonAttribute) {
		this.comparisonAttribute = comparisonAttribute;
	}

	public Object getComparisonValue() {
		return comparisonValue;
	}

	public void setComparisonValue(Object comparisonValue) {
		this.comparisonValue = comparisonValue;
	}
	
}
