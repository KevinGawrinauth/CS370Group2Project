import java.io.BufferedWriter;
import java.io.IOException;

public class DecisionNode implements TreeNode{//leaf node
	protected String Decision;	
	
	public String getDecision() {
		return Decision;
	}

	public void setDecision(String Decision) {
		this.Decision = Decision;
	}

	public void printNode(BufferedWriter writeF) throws IOException {//writes decision
		writeF.write("decision node\n"+Decision+"\n");
	}
}