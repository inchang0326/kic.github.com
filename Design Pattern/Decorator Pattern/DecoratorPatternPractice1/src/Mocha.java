// �߰� Ŭ���� CondimentDecorator �޼ҵ�� ����� �̿����� ������, ������� �ڽ� Ŭ�������� ����� �̿��Ѵ�.
public class Mocha extends CondimentDecorator {

	// has-a���踦 �����ϸ鼭 ���� ����� ����� ����� ����
	private Beverage beverage;
	
	public Mocha(Beverage beverage){
		this.beverage = beverage;
	}
	
	// ���
	@Override
	public String getDescription() {
		return beverage.getDescription()+", Mocha";
	}

	@Override
	public int cost(){
		return beverage.cost() + 200;
	}
}
