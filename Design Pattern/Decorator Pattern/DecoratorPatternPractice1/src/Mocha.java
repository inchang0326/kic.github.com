// 중간 클래스 CondimentDecorator 메소드는 상속을 이용하지 않지만, 결과물인 자식 클래스들은 상속을 이용한다.
public class Mocha extends CondimentDecorator {

	// has-a관계를 형성하면서 내가 장식할 대상을 멤버로 유지
	private Beverage beverage;
	
	public Mocha(Beverage beverage){
		this.beverage = beverage;
	}
	
	// 장식
	@Override
	public String getDescription() {
		return beverage.getDescription()+", Mocha";
	}

	@Override
	public int cost(){
		return beverage.cost() + 200;
	}
}
