
public class Main {
	public static void main(String args[]) {
		Queue queue = new Queue();
		queue.add(10);
		queue.add(20);
		queue.add(30);
		System.out.println("peek    : " + queue.peek());
		System.out.println("toString: " + queue.toString());
		System.out.println("remove  : " + queue.remove());
		System.out.println("remove  : " + queue.remove());
		System.out.println("remove  : " + queue.remove());
		System.out.println("toString: " +queue.toString());
		queue.add(40);
		queue.add(50);
		System.out.println("toString: " +queue.toString());
		System.out.println("remove  : " + queue.remove());
		System.out.println("remove  : " + queue.remove());
		System.out.println("toString: " +queue.toString());
		queue.add(60);
		System.out.println("toString: " +queue.toString());
	}
}
