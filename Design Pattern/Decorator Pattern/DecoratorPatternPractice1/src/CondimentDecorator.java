/*
 *  이 중간 클래스를 두는 이유는
 *  1) 장식 도구들을 구별하기 위한 타입이 된다.
 *  2) 자식 클래스들 즉, 장식용들이 반드시 재정의 해야 하는 메소드가 있으면 이 중간 클래스에 추상화함으로써 반드시 만들게끔 할 수 있다.
 */
public abstract class CondimentDecorator extends Beverage {

	// Beverage에서 추상 메소드가 아니었던 getDesciption 메소드를 추상화 해주면서 CondimentDecorator 클래스를 상속받는 자식 클래스는 이것을 꼭 재정의 해주어야 한다.
	public abstract String getDescription();
	
	@Override
	public int cost() {
		return 0;
	}
	
}
