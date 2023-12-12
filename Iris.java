public class Iris {
	private String Species;
	private Float SepalLength;
	private Float SepalWidth;
	private Float PetalLength;
	private Float PetalWidth;

	public String getSpecies() {
		return Species;
	}
	
	public void setSpecies(String Species) {
		this.Species = Species;
	}

	public float getSepalLength() {
		return SepalLength;
	}
	
	public void setSepalLength(float SepalLength) {
		this.SepalLength = SepalLength;
	}

	public float getSepalWidth() {
		return SepalWidth;
	}

	public void setSepalWidth(float SepalWidth) {
		this.SepalWidth = SepalWidth;
	}

	public float getPetalLength() {
		return PetalLength;
	}
	
	public void setPetalLength(float PetalLength) {
		this.PetalLength = PetalLength;
	}

	public float getPetalWidth() {
		return PetalWidth;
	}
	
	public void setPetalWidth(float PetalWidth) {
		this.PetalWidth = PetalWidth;
	}
	
	//get attribute by name
	public Object getByName(String s) {
		if(s.equals("Iris Species")) 
			return Species;
		else if(s.equals("Sepal Length"))
			return SepalLength;
		else if(s.equals("Sepal Width")) 
			return SepalWidth;
		else if(s.equals("Petal Length")) 
			return PetalLength;
		else if(s.equals("Petal Width"))
			return PetalWidth;
		return null;
	}
	
}