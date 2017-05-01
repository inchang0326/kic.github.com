// ���� ������ ȯ�濡���� singleton�� ������ ���� ���� �� �ִ�.
public class InnerSingleTon {
	
	// ���� Ŭ������ ����� ��ü�� ���� Ŭ���� �ȿ��� ����ƽ�� ����� ����� ����
	// ���� �̳ʽ�Ŭ�� Ŭ������ �ε�Ǵ� �������� ���� Ŭ�������� �ʱ�ȭ ���� ����, ���� �ʿ� �����ϱ�(jvm�� Ŭ���� �ε带 �ʿ��� ������ ���� �ε���)
	// ���ν��Ͻ��� �ϱ� ������ ���� Ŭ���� Ȧ���� �̴ϼȶ����� ���� ���� �� �ȿ� �ִ� �ֵ� �ȸ������
	// �� �ν��Ͻ��� �ε�Ǵ� ������ �ε� �� lazy + ��Ƽ������ ȯ�濡�� ��밡�� + ��ũ�� ���� �ʾƵ� �� �ڹ� 5������ ���� ����
	private static class Holder {//����ƽŬ������ �Ǹ� �ƿ���Ŭ������ ���� �����͸� ������ ���� ����
		private static final InnerSingleTon unique = new InnerSingleTon();
	}
	
	private InnerSingleTon(){}
	public static InnerSingleTon getInstance(){
		return Holder.unique;
	}
}