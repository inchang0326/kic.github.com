
public class DoublyLinkedList {
	private Node head;
	private Node tail;
	private int size = 0;

	// 첫번째에 노드 삽입
	public void addFirst(int data) {
		Node newNode = new Node(data);
		newNode.setNext(head);
		
		// head가 null이란 것은 첫 노드를 삽입하는 것으로 prev 연결이 필요 없다.
		// 하지만 head가 null이 아닐 경우, 새로운 노드를 바뀌기 전 head의 prev로 지정한다.
		if (head != null)
			head.setPrev(newNode);
		
		head = newNode;
		size++;

		if (head.getNext() == null)
			tail = head;
	}

	// 마지막에 노드 삽입
	public void addLast(int data) {
		if (size == 0)
			addFirst(data);
		else {
			Node newNode = new Node(data);
			tail.setNext(newNode);

			// newNode의 prev를 tail로 지정
			newNode.setPrev(tail);

			tail = newNode;
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
			
			// 새로운 노드의 prev를 left로 지정
			newNode.setPrev(left);
			
			newNode.setNext(right);
			
			// right의 prev를 새로운 노드로 지정
			right.setPrev(newNode);

			size++;
			if (newNode.getNext() == null) {
				tail = newNode;
			}
		}
	}

	// 노드 찾기
	Node node(int index) {
		Node theNode;
		// 찾고자 하는 노드의 인덱스가 전체 노드 수의 반보다 작다면
		if (index < size / 2) {
			theNode = head;
			for (int i = 0; i < index; i++)
				theNode = theNode.getNext();
			return theNode;
			
			// 찾고자 하는 노드의 인덱스가 전체 노드의 수보다 크다면
		} else {
			
			// tail부터 prev를 이용해서 인덱스에 해당하는 노드를 찾습니다.
			theNode = tail;
			for (int i = size - 1; i > index; i--)
				theNode = theNode.getPrev();
			return theNode;
		}
	}

	// 첫번째 노드 제거
	public void removeFirst() {
		Node newNode = head;
		head = newNode.getNext();
		newNode = null;

		// head가 null이 아니면, 노드가 이미 존재하는 것을 의미한다.
		// 즉, (제거될 노드)<->(head)<->(data)<->....<->(data) 상태
		// 완전히 첫 노드와 분리시키기 위해 두번째 노드의 prev를 null로 지정해준다.
		if (head != null)
			head.setPrev(null);
		size--;
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
			
			// left의 next가 tail이 아니라면
			if( left.getNext() != null)
				// left의 다음 노드 역시 left를 prev로 지정
				left.getNext().setPrev(left);
			
			if (newNode == tail)
				tail = left;
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
	
	// 특정 데이터의 인덱스 찾기
	public int indexOf(int data) {
		Node newNode = head;
		int index = 0;
		
		while( newNode.getData() != data ) {	
			newNode = newNode.getNext();
			index++;
			if(newNode == null) return -1;
		}
		return index;
	}

	// 내부 데이터 명세
	public String toString() {
		if (head == null)
			return "[]";

		Node temp = head;
		String str = "[";
		
		while (temp.getNext() != null) {
			str += temp.getData() + ", ";
			temp = temp.getNext();
		}

		str += temp.getData();
		return str + "]";
	}

}
