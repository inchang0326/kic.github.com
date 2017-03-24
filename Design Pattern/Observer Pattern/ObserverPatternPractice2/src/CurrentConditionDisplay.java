
public class CurrentConditionDisplay implements Observer {
	@Override
	public void update(double temperature){
		System.out.printf("현재 온도 : %.2f\n", temperature);
	}
}
