public class IrisPlant {	
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
	public void setPetalWidthr(float PetalWidth) {
		this.PetalWidth = PetalWidth;
	}

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


    public String Identify(){


        return toString();
    }

    public String IdentifySpecies(float getSepalLength,float getSepalWidth,float getPetalLength,float getPetalWidth){


		return toString();
    }


    Boolean Validate(){
		if(this.SepalLength == null || this.SepalWidth == null || this.PetalLength == null || this.PetalWidth == null){
			AskUserForMoreData();

			return false;	
		}

		else{
			return true;
		}
    }

    void AskUserForMoreData(){
		System.out.println("Enter more information for better accuracy");


    }
}