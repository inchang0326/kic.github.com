
public class Test {

	public static void main(String[] args) {
		NewsPublisher newsServer = new SportsNewsPublisher(); 
		NewsClient client = new NewsClient("T"); 
		NewsClient client2 = new NewsClient("Y");
		client.setInterval(1);
		client2.setInterval(3);
		newsServer.addObserver(client);
		newsServer.addObserver(client2);
		newsServer.setNewsFeed("리버풀 1: 맨유 0");
		newsServer.setNewsFeed("리버풀 2: 맨유 0"); 
		newsServer.setNewsFeed("리버풀 3: 맨유 0");
		newsServer.setNewsFeed("리버풀 3: 맨유 1");
		newsServer.deleteObserver(client);
		newsServer.setNewsFeed("리버풀 3: 맨유 2");
		newsServer.setNewsFeed("리버풀 4: 맨유 2");
	}
}
