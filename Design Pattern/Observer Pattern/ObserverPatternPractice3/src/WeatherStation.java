import java.util.ArrayList;

// ���� �����ڸ� �����ؾ� ��
public class WeatherStation implements Subject {

	// �ڹٿ��� ���߰����ڸ� ���� ���� ������ �� �ִ� ���
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
