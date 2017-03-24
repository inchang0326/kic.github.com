import java.util.ArrayList;

public class NewsClient implements Observer {
	
	private String id;
	private int interval;
	private int i = 0;
	
	public NewsClient(String id){
		this.id = id;
	}
	
	public void setInterval(int interval){// NewsClient�� �뺸������ ����
		if(interval > 0) this.interval = interval;
		else this.interval = 1;
	}
	
	@Override
	public int getInterval(){// NewsClient�� �뺸������ ��ȯ
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
	2. update �޼ҵ� �𵨸� ����
	�� notifyObservers �޼ҵ�κ��� newsList �迭 ������ �Ű��޾� ���
	�� ������ ���ο� �ҽĻӸ��ƴ϶� ���� �ҽı��� �ݺ��Ǿ� ������Ʈ�Ǵ� ���� �߻�
	�� ���� ���� �ذ��� ����, Ŭ���� ��ü�� private ���� i�� �ξ� �������� ����ߴ� newsList �ε����� ����
	�� �뺸�������� �����ڿ��� ���ο� �ҽ� ����(interval �������� i���� newsList.size()������ �ε����� �ִ� ������ ���)
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
