/*
 *  �� �߰� Ŭ������ �δ� ������
 *  1) ��� �������� �����ϱ� ���� Ÿ���� �ȴ�.
 *  2) �ڽ� Ŭ������ ��, ��Ŀ���� �ݵ�� ������ �ؾ� �ϴ� �޼ҵ尡 ������ �� �߰� Ŭ������ �߻�ȭ�����ν� �ݵ�� ����Բ� �� �� �ִ�.
 */
public abstract class CondimentDecorator extends Beverage {

	// Beverage���� �߻� �޼ҵ尡 �ƴϾ��� getDesciption �޼ҵ带 �߻�ȭ ���ָ鼭 CondimentDecorator Ŭ������ ��ӹ޴� �ڽ� Ŭ������ �̰��� �� ������ ���־�� �Ѵ�.
	public abstract String getDescription();
	
	@Override
	public int cost() {
		return 0;
	}
	
}
