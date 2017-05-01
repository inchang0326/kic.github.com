
public class EagerSingleTon {
	// 너무 일찍 로드되어 생성되는 비용이 따름
	private static EagerSingleTon unique = new EagerSingleTon();
	private EagerSingleTon(){}
	public static EagerSingleTon getInstance(){
		return unique;
	}
}
