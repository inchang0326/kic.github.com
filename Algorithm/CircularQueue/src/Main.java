import java.util.Queue;

public class Main {
	public static void main(String[] args) {
		CircularQueue circularQueue = new CircularQueue();
		circularQueue.add(10);
		circularQueue.add(20);
		circularQueue.add(30);
		circularQueue.add(40);
		circularQueue.add(50);
		System.out.println(circularQueue.toString());
		circularQueue.remove();
		System.out.println(circularQueue.toString());
		circularQueue.add(60);
		System.out.println(circularQueue.toString());
		circularQueue.remove();
		circularQueue.remove();
		System.out.println(circularQueue.toString());
		circularQueue.add(10);
		circularQueue.add(20);
		System.out.println(circularQueue.toString());
		//circularQueue.add(30);
		//System.out.println(circularQueue.toString());
	}
}
