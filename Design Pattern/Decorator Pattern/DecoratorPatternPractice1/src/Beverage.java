
public abstract class Beverage {
	private String description = "Unknown Beverage";
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	// core of this project
	abstract public int cost();
}
