
public enum ResultType {
	WON, 
	DRAWN, 
	LOST;
	
	public static ResultType valueOf(int n){
		return ResultType.values()[n];
	}
}
