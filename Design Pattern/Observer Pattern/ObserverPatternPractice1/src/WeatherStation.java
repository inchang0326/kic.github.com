import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

// ���� �����ڸ� �����ؾ� ��
public class WeatherStation extends Observable{

	private double temperature;
	
	public void setMeasurement(double temperature){
		this.temperature = temperature;
		// setChanged() �޼ҵ尡 �־�� notify�޼ҵ带 �ҷ��� Ȱ���� �� ���� - ������ ����, ���� �뺸�Ǵ� ���� ������ �� ����
		setChanged();
		
		// push���
		notifyObservers(temperature);
		// pull��� : notifyObservers();
	}
	
	// pull ��� ��
	public double getTemperature(){
		return temperature;
	}
}
