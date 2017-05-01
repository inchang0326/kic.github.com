// ���� ������ ȯ�濡���� singleton�� ������ ���� ���� �� �ִ�.
public class SyncSingleTon {
	private static SyncSingleTon unique = null;
	
	private SyncSingleTon(){}
	// ��Ƽ�������� �������� �ذ��ϴ� ��������� �ӵ��� �ʾ���
	// synchronized - ���� ������ ȯ�濡�� �Լ��� �ɰ��� ���� ��밡�� - �ٸ� �����尡 �߰��� ��� ���� - ��� �ӵ��� �ʾ���
	public static synchronized SyncSingleTon getInstance(){
		if(unique == null) unique = new SyncSingleTon();
		return unique;
	}
}
