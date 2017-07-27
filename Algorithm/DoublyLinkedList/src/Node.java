
public class Node {
	private int data;
	private Node next;
	// 이전 노드 추가
	private Node prev;
	
	public Node(int data) {
		this.data = data;
		this.next = null;
		
		// 이전 노드 초기화
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
	
	// 이전 노드 반환
	public Node getPrev() {
		return prev;
	}
	
	// 이전 노드 지정
	public void setPrev(Node perv) {
		this.prev = prev;
	}
	
	public String toString() {
		return String.valueOf(this.data);
	}
}
