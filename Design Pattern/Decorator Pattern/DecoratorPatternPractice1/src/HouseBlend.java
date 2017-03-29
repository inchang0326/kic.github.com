
public class HouseBlend extends Beverage {

	// private int cost = 1000;
	
	public HouseBlend(){
		setDescription("HouseBlend");
	}
	
	@Override
	public int cost() {
		return 1000; // 멤버변수로 cost를 따로 선언할 필요가 없음
	}

}
