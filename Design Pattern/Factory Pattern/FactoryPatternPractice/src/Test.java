
public class Test {

	public static void main(String[] args) {
		PizzaStore pizzaStore = new PizzaStore();
		pizzaStore.setPizzaFactory(new SimplePizzaFactory());
		pizzaStore.orderPizza("cheese");

	}

}
