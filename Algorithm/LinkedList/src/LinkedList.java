
public class LinkedList {
	private Node head;
	private Node tail;
	private int size = 0;

	// 처음에 노드 삽입
	public void addFirst(int data) {
		Node newNode = new Node(data);

		// 첫 노드를 생성할 때는 의미 없으나 두번째로 만들어진 노드부터 이전에 생성한 노드를 가리키도록 한다.
		newNode.setNext(head);

		// head는 새로 만들어지는 노드를 취한다.
		head = newNode;
		size++;

		// head의 next가 없다는 것은 처음 만들어진 딱 하나의 노드만 존재한다는 것을 의미한다. 즉, 노드를 추가할수록 첫
		// 노드가 끝으로 가 tail이 된다.
		if (head.getNext() == null)
			tail = head;
	}

	// 마지막에 노드 삽입
	public void addLast(int data) {
		if (size == 0)
			addFirst(data);
		else {

			// 새로운 노드 생성
			Node newNode = new Node(data);

			// 현재 tail의 next를 조금 전 생성된 노드로 지정시켜줌으로써 연결한다.
			tail.setNext(newNode);

			// tail을 조금 전 생성된 노드로 지정
			tail = newNode;
			size++;
		}
	}

	// 중간에 노드 삽입
	public void add(int index, int data) {
		if (index == 0)
			addFirst(data);
		else {
			
			// 새로운 노드 생성
			Node newNode = new Node(data);

			// 새로운 노드로 연결할 왼쪽에 있는 노드 찾기
			Node left = node(index - 1);

			// 새로운 노드가 연결할 오른쪽에 있는 노드 찾기
			Node right = left.getNext();

			// 왼쪽 노드가 새로운 노드를 연결
			left.setNext(newNode);

			// 새로운 노드가 오른쪽 노드를 연결
			newNode.setNext(right);
			size++;

			// 새로운 노드의 다음 노드가 없다는 것은 마지막 노드라는 것이므로 tail로 지정
			if (newNode.getNext() == null) {
				tail = newNode;
			}
		}
	}

	// 노드 찾기
	private Node node(int index) {
		Node theNode = head;
		for (int i = 0; i < index; i++) theNode = theNode.getNext();
		return theNode;
	}

	// 첫번째 노드 제거
	public void removeFirst() {
		Node newNode = head;
		head = newNode.getNext();
		newNode = null;
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

			if (newNode == tail)
				tail = left;

			newNode = null;
			size--;
		}
	}

	// 마지막 노드 제거
	public void removeLast() {
		remove(size-1);
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
		int index = 0;
		Node newNode = head;

		while (newNode.getData() != data) {
			newNode = newNode.getNext();
			index++;
		}
		if (newNode == null)
			return -1;
		else
			return index;
	}

	// 내부 데이터 명세
	public String toString() {
		
		// 노드가 없다면 [] 반환
		if (head == null)
			return "[]";

		Node newNode = head;
		String spec = "[";
		// 마지막 노드는 다음 노드가 없기 때문에 아래의 구문은 마지막 노드는 제외됩니다.

		// 노드가 null일 때까지 LinkedList 내부 순회
		while (newNode.getNext() != null) {
			spec += newNode.getData() + ", ";

			// 다음 노드로 이동
			newNode = newNode.getNext();
		}

		// 마지막 노드 출력결과 역시 저장
		spec += newNode.getData();
		return spec + "]";
	}
}
