
public class Node {
	private int data;
	private Node next;
	// ���� ��� �߰�
	private Node prev;
	
	public Node(int data) {
		this.data = data;
		this.next = null;
		
		// ���� ��� �ʱ�ȭ
		this.prev = null;
	}
	
	public Node getNext() {
		return next;
	}
	
	public void setNext(Node next) {
		this.next = next;
	}
	
	public int getData() {
		return data;
	}
	
	public void setData(int data) {
		this.data = data;
	}
	
	// ���� ��� ��ȯ
	public Node getPrev() {
		return prev;
	}
	
	// ���� ��� ����
	public void setPrev(Node perv) {
		this.prev = prev;
	}
	
	public String toString() {
		return String.valueOf(this.data);
	}
}
