// 데이터가 새롭게 바뀌는 사실을 알려주면서 데이터를 새로 줌(push/pull 방식)
public class Test {

	public static void main(String[] args) {
		WeatherStation ws = new WeatherStation();
		Observer o = new CurrentConditionDisplay();
		ws.registerObserver(o);
		ws.setMeasurement(20.3);
	}

}
