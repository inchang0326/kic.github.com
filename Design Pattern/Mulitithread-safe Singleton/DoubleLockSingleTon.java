// 다중 쓰레드 환경에서는 singleton이 보장이 되지 않을 수 있다.
public class DoubleLockSingleTon {
	// volatile
	private volatile static DoubleLockSingleTon unique = null;
	
	private DoubleLockSingleTon(){}
	
	public static DoubleLockSingleTon getInstance(){
		if(unique == null) 
			synchronized(DoubleLockSingleTon.class){// 2. 따라서 필요한 부분만 sync함(snycsinlgeton 클래스와의 차이점)
			unique = new DoubleLockSingleTon(); // 1. new 하는 부분에 synchronized가 필요한 것임
		}
		return unique;
	}
}
