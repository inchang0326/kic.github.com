// 다중 쓰레드 환경에서는 singleton이 보장이 되지 않을 수 있다.
public class InnerSingleTon {
	
	// 내부 클래스를 만들고 객체를 내부 클래스 안에다 스테틱한 멤버로 만드는 것임
	// 따라서 이너싱클톤 클래스가 로드되는 시점에서 내부 클래스까지 초기화 하지 않음, 아직 필요 없으니까(jvm은 클래스 로드를 필요한 순간에 가서 로드함)
	// 겟인스턴스를 하기 전까지 내부 클래스 홀더를 이니셜라이즈 안함 따라서 그 안에 있는 애도 안만들어짐
	// 겟 인스턴스가 로드되는 시점에 로드 댐 lazy + 멀티스레드 환경에서 사용가능 + 싱크를 하지 않아도 댐 자바 5이전에 많이 사용댐
	private static class Holder {//스테틱클래스가 되면 아우터클래스에 대한 포인터를 가지고 있지 않음
		private static final InnerSingleTon unique = new InnerSingleTon();
	}
	
	private InnerSingleTon(){}
	public static InnerSingleTon getInstance(){
		return Holder.unique;
	}
}