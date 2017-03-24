import java.util.Observable;
import java.util.Observer;

public class CurrentConditionDisplay implements Observer{
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 * 
	 * arg0�� ��ü�� �Ű��ϴ� ����, arg1�� �����͸� �Ű��ϴ� ������
	 * push ������� �� ���̸� �ڿ��ִ� �����͸� push���·� �ٲپ� �ش�. ��, arg1�� double�� �� ��ȯ �Ѵ�.
	 */
	public void update(Observable arg0, Object arg1){
		Double temperature = (Double)arg1;
		System.out.printf("���� �µ� : %.2f\n", temperature);
		
		/* pull
		WeatherStation ws = (WeatherStation)arg0; 
		System.out.printf("���� �µ� : %.2f\n", ws.getTemperature());
		 */
	}
}
