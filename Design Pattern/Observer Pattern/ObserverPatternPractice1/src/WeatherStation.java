import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

// 다중 관찰자를 유지해야 함
public class WeatherStation extends Observable{

	private double temperature;
	
	public void setMeasurement(double temperature){
		this.temperature = temperature;
		// setChanged() 메소드가 있어야 notify메소드를 불러서 활용할 수 있음 - 유연성 제공, 자주 통보되는 것을 조절할 수 있음
		setChanged();
		
		// push방식
		notifyObservers(temperature);
		// pull방식 : notifyObservers();
	}
	
	// pull 방식 용
	public double getTemperature(){
		return temperature;
	}
}
