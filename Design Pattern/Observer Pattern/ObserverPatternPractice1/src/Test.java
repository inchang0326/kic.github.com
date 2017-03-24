import java.util.Observer;

public class Test {

	public static void main(String[] args) {
		WeatherStation ws = new WeatherStation();
		Observer o = new CurrentConditionDisplay();
		// Observable�� ��ӹ����� register, remove �� �� �������ش�. 
		ws.addObserver(o);
		ws.setMeasurement(20.3);
		ws.deleteObserver(o);
		ws.setMeasurement(20.3);
	}

}