
public class Test {

	public static void main(String[] args) {
		Pizza pizza;
		PizzaStore pizzaStore = new NYPizaaStore();
		pizza = pizzaStore.orderPizza("potato");
		System.out.println(pizza.getClass());
	}
}
