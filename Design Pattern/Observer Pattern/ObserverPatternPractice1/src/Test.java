import java.util.Observer;

public class Test {

	public static void main(String[] args) {
		WeatherStation ws = new WeatherStation();
		Observer o = new CurrentConditionDisplay();
		// Observable을 상속받으면 register, remove 등 다 제공해준다. 
		ws.addObserver(o);
		ws.setMeasurement(20.3);
		ws.deleteObserver(o);
		ws.setMeasurement(20.3);
	}

}