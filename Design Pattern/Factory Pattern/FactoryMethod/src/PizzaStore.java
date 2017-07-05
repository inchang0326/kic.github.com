
public abstract class PizzaStore {
	
	protected abstract Pizza createPizza(String type);
	
	public final Pizza orderPizza(String type){
		Pizza pizza = createPizza(type);
		return pizza;
	}
}
