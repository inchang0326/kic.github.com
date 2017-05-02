// 문제 상황 : 뉴욕과 시카고에서 피자를 만드는 법은 각각 다르다. 따라서 어떻게 모델링 할 것인가?
// Simple Facotry Pattern 에서의 문제 해결 방안

public class PizzaStore {
	// has-a관계로 Facotry 인터페이스를 멤버로 가져온다.
	private PizzaFactory pizzaFactory;
	public void setPizzaFactory(PizzaFactory pizzaFactory){
		this.pizzaFactory = pizzaFactory;
	}
	
	public Pizza orderPizza(String type){
		Pizza pizza = pizzaFactory.createPizza(type);
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		return pizza;
	}
}
