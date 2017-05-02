
public class SimplePizzaFactory implements PizzaFactory {
	public Pizza createPizza(String type){
		switch(type){
			case "cheese" : {
				return new CheesePizza(); 
			}
			case "potato" : {
				return new PotatoPizza(); 
			}
		}
		return null;
	}
}
