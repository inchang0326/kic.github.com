// 다중 쓰레드 환경에서는 singleton이 보장이 되지 않을 수 있다.
public class SyncSingleTon {
	private static SyncSingleTon unique = null;
	
	private SyncSingleTon(){}
	// 멀티스레드의 문제점을 해결하는 방안이지만 속도가 늦어짐
	// synchronized - 다중 스레드 환경에서 함수의 쪼개짐 없이 사용가능 - 다른 쓰레드가 중간에 사용 못함 - 대신 속도가 늦어짐
	public static synchronized SyncSingleTon getInstance(){
		if(unique == null) unique = new SyncSingleTon();
		return unique;
	}
}
