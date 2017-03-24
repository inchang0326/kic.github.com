
public class CurrentConditionDisplay implements Observer {
	@Override
	public void update(Subject subject){
		WeatherStation ws = (WeatherStation)subject;
		System.out.printf("���� �µ� : %.2f\n", ws.getTemperature());
	}
}
