
public class Test {

	public static void main(String[] args) {
		NewsPublisher newsServer = new SportsNewsPublisher(); 
		NewsClient client = new NewsClient("T"); 
		NewsClient client2 = new NewsClient("Y");
		client.setInterval(1);
		client2.setInterval(3);
		newsServer.addObserver(client);
		newsServer.addObserver(client2);
		newsServer.setNewsFeed("����Ǯ 1: ���� 0");
		newsServer.setNewsFeed("����Ǯ 2: ���� 0"); 
		newsServer.setNewsFeed("����Ǯ 3: ���� 0");
		newsServer.setNewsFeed("����Ǯ 3: ���� 1");
		newsServer.deleteObserver(client);
		newsServer.setNewsFeed("����Ǯ 3: ���� 2");
		newsServer.setNewsFeed("����Ǯ 4: ���� 2");
	}
}
