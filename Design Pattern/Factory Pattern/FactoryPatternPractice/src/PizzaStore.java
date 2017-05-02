// ���� ��Ȳ : ����� ��ī���� ���ڸ� ����� ���� ���� �ٸ���. ���� ��� �𵨸� �� ���ΰ�?
// Simple Facotry Pattern ������ ���� �ذ� ���

public class PizzaStore {
	// has-a����� Facotry �������̽��� ����� �����´�.
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
