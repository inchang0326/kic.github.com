// �����Ͱ� ���Ӱ� �ٲ�� ����� �˷��ָ鼭 �����͸� ���� ��(push/pull ���)
public class Test {

	public static void main(String[] args) {
		WeatherStation ws = new WeatherStation();
		Observer o = new CurrentConditionDisplay();
		ws.registerObserver(o);
		ws.setMeasurement(20.3);
	}

}
