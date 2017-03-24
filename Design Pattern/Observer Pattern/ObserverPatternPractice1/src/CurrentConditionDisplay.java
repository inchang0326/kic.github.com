import java.util.Observable;
import java.util.Observer;

public class CurrentConditionDisplay implements Observer{
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 * 
	 * arg0은 객체를 매개하는 변수, arg1은 데이터를 매개하는 변수로
	 * push 방식으로 할 것이면 뒤에있는 데이터를 push형태로 바꾸어 준다. 즉, arg1를 double로 형 변환 한다.
	 */
	public void update(Observable arg0, Object arg1){
		Double temperature = (Double)arg1;
		System.out.printf("현재 온도 : %.2f\n", temperature);
		
		/* pull
		WeatherStation ws = (WeatherStation)arg0; 
		System.out.printf("현재 온도 : %.2f\n", ws.getTemperature());
		 */
	}
}
