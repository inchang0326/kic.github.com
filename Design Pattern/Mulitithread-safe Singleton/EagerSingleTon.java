
public class EagerSingleTon {
	// �ʹ� ���� �ε�Ǿ� �����Ǵ� ����� ����
	private static EagerSingleTon unique = new EagerSingleTon();
	private EagerSingleTon(){}
	public static EagerSingleTon getInstance(){
		return unique;
	}
}
