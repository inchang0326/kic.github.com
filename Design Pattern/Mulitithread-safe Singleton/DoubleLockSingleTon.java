// ���� ������ ȯ�濡���� singleton�� ������ ���� ���� �� �ִ�.
public class DoubleLockSingleTon {
	// volatile
	private volatile static DoubleLockSingleTon unique = null;
	
	private DoubleLockSingleTon(){}
	
	public static DoubleLockSingleTon getInstance(){
		if(unique == null) 
			synchronized(DoubleLockSingleTon.class){// 2. ���� �ʿ��� �κи� sync��(snycsinlgeton Ŭ�������� ������)
			unique = new DoubleLockSingleTon(); // 1. new �ϴ� �κп� synchronized�� �ʿ��� ����
		}
		return unique;
	}
}
