// 다중 쓰레드 환경에서는 singleton이 보장이 되지 않을 수 있다.
// enum의 특징 - 하나만을 보장한다는 전제

public enum EnumSingleTon {
	UNIQUE;
	public static EnumSingleTon getInstance(){
		return UNIQUE;
	}
}
