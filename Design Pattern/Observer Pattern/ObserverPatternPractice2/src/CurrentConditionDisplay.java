
public class CurrentConditionDisplay implements Observer {
	@Override
	public void update(double temperature){
		System.out.printf("���� �µ� : %.2f\n", temperature);
	}
}
