import java.util.ArrayList;

// 다중 관찰자를 유지해야 함
public class WeatherStation implements Subject {

	// 자바에서 다중관찰자를 가장 쉽게 유지할 수 있는 방안
	private ArrayList<Observer> observers = new ArrayList<>();
	private double temperature;
	
	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for(Observer o : observers) o.update(this);
	}
	
	public void setMeasurement(double temperature){
		this.temperature = temperature;
		notifyObservers();
	}
	
	public double getTemperature(){
		return temperature;
	}

}
