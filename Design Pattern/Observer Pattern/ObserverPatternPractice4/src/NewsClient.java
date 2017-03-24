import java.util.ArrayList;

public class NewsClient implements Observer {
	
	private String id;
	private int interval;
	private int i = 0;
	
	public NewsClient(String id){
		this.id = id;
	}
	
	public void setInterval(int interval){// NewsClient의 통보시점을 설정
		if(interval > 0) this.interval = interval;
		else this.interval = 1;
	}
	
	@Override
	public int getInterval(){// NewsClient의 통보시점을 반환
		return this.interval;
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null || o.getClass() != getClass()) return false;
		if(o == this) return true;
		NewsClient nc = (NewsClient)o;
		return id.equals(nc.id);
	}
	/*
	2. update 메소드 모델링 과정
	① notifyObservers 메소드로부터 newsList 배열 변수를 매개받아 출력
	② 하지만 새로운 소식뿐만아니라 이전 소식까지 반복되어 업데이트되는 문제 발생
	③ 따라서 문제 해결을 위해, 클래스 자체에 private 변수 i를 두어 이전까지 출력했던 newsList 인덱스를 누적
	④ 통보시점마다 구독자에게 새로운 소식 전달(interval 간격으로 i부터 newsList.size()까지의 인덱스에 있는 데이터 출력)
	*/
	@Override
	public void update(ArrayList<String> news) { 
		int limit = interval;
		
		while(limit > 0){
			System.out.printf("%s : %s\n", id, news.get(i++));	
			limit--;
		}
		System.out.println("===============================");
	}
}
