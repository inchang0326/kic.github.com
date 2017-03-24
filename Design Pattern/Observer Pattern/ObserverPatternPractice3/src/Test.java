//pull ¹æ¹ý but, tight-couplingÀÌ µÊ

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WeatherStation ws = new WeatherStation();
		Observer o = new CurrentConditionDisplay();
		ws.registerObserver(o);
		ws.setMeasurement(20.3);
	}

}
