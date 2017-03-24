
public class CurrentConditionDisplay implements Observer {
	@Override
	public void update(Subject subject){
		WeatherStation ws = (WeatherStation)subject;
		System.out.printf("현재 온도 : %.2f\n", ws.getTemperature());
	}
}
