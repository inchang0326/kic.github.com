
public class CircularlyLinkedList {
	private Node head;
	private Node tail;
	private int size = 0;

	// 처음에 노드 삽입
	public void addFirst(int data) {
		Node newNode = new Node(data);
		newNode.setNext(head);
		head = newNode;
		if (head.getNext() == null)
			tail = head;

		// 항상 tail의 next는 head로 순환구조를 형성한다.
		tail.setNext(head);
		size++;
	}

	// 마지막에 노드 삽입
	public void addLast(int data) {
		if (size == 0)
			addFirst(data);
		else {
			Node newNode = new Node(data);
			tail.setNext(newNode);
			tail = newNode;

			// 항상 tail의 next는 head로 순환구조를 형성한다.
			tail.setNext(head);
			size++;
		}
	}

	// 중간에 노드 삽입
	public void add(int index, int data) {
		if (index == 0)
			addFirst(data);
		else {
			Node newNode = new Node(data);
			Node left = node(index - 1);
			Node right = left.getNext();
			left.setNext(newNode);
			newNode.setNext(right);
			size++;
			if (newNode.getNext() == null) {
				tail = newNode;

				// 항상 tail의 next는 head로 순환구조를 형성한다.
				tail.setNext(head);
			}
		}
	}

	// 노드 찾기
	private Node node(int index) {
		Node theNode = head;
		for (int i = 0; i < index; i++)
			theNode = theNode.getNext();
		return theNode;
	}

	// 첫번째 노드 제거
	public void removeFirst() {
		Node newNode = head;
		head = newNode.getNext();
		newNode = null;
		size--;

		// 항상 tail의 next는 head로 순환구조를 형성한다.
		tail.setNext(head);
	}

	// 중간 노드 제거
	public void remove(int index) {
		if (index == 0)
			removeFirst();
		else {
			Node newNode;
			Node left = node(index - 1);
			newNode = left.getNext();
			left.setNext(left.getNext().getNext());
			if (newNode == tail) {
				tail = left;
				
				// 항상 tail의 next는 head로 순환구조를 형성한다.
				tail.setNext(head);
			}
			newNode = null;
			size--;
		}
	}

	// 마지막 노드 제거
	public void removeLast() {
		remove(size - 1);
	}

	// 리스트 크기 반환
	public int getSize() {
		return size;
	}

	// 특정 인덱스에 위치한 데이터 반환
	public int get(int index) {
		Node newNode = node(index);
		return newNode.getData();
	}

	// 특정 데이터의 인덱스 반환
	public int indexOf(int data) {
		Node newNode = head;
		int index = 0;
		while( newNode.getData() != data ) {	
			newNode = newNode.getNext();
			index++;
			if(newNode == head) return -1;
		}
		return index;
	}

	// 내부 데이터 명세
	public String toString() {
		if (head == null)
			return "[]";
		Node newNode = head;
		String spec = "[";
		while (newNode.getNext() != head) {
			spec += newNode.getData() + ", ";
			newNode = newNode.getNext();
		}
		spec += newNode.getData();
		return spec + "]";
	}
}
