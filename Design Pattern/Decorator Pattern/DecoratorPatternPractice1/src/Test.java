
public class Test {

	public static void main(String[] args) {
		Beverage beverage = new HouseBlend();
		beverage = new Mocha(beverage);
		beverage = new Mocha(beverage);
		System.out.printf("%s : %d¿ø", beverage.getDescription(), beverage.cost());
	}

}
