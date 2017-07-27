
public class Main {
	public static void main(String args[]) {
		CircularlyLinkedList circularlyLinkedList = new CircularlyLinkedList();
		circularlyLinkedList.addFirst(5);
		circularlyLinkedList.addFirst(10);
		System.out.println(circularlyLinkedList.toString());
		System.out.println(circularlyLinkedList.indexOf(10));
	}
}
