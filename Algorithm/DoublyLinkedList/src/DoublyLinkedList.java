
public class DoublyLinkedList {
	private Node head;
	private Node tail;
	private int size = 0;

	// ù��°�� ��� ����
	public void addFirst(int data) {
		Node newNode = new Node(data);
		newNode.setNext(head);
		
		// head�� null�̶� ���� ù ��带 �����ϴ� ������ prev ������ �ʿ� ����.
		// ������ head�� null�� �ƴ� ���, ���ο� ��带 �ٲ�� �� head�� prev�� �����Ѵ�.
		if (head != null)
			head.setPrev(newNode);
		
		head = newNode;
		size++;

		if (head.getNext() == null)
			tail = head;
	}

	// �������� ��� ����
	public void addLast(int data) {
		if (size == 0)
			addFirst(data);
		else {
			Node newNode = new Node(data);
			tail.setNext(newNode);

			// newNode�� prev�� tail�� ����
			newNode.setPrev(tail);

			tail = newNode;
			size++;
		}
	}

	// �߰��� ��� ����
	public void add(int index, int data) {
		if (index == 0)
			addFirst(data);
		else {
			Node newNode = new Node(data);
			Node left = node(index - 1);
			Node right = left.getNext();
			
			left.setNext(newNode);
			
			// ���ο� ����� prev�� left�� ����
			newNode.setPrev(left);
			
			newNode.setNext(right);
			
			// right�� prev�� ���ο� ���� ����
			right.setPrev(newNode);

			size++;
			if (newNode.getNext() == null) {
				tail = newNode;
			}
		}
	}

	// ��� ã��
	Node node(int index) {
		Node theNode;
		// ã���� �ϴ� ����� �ε����� ��ü ��� ���� �ݺ��� �۴ٸ�
		if (index < size / 2) {
			theNode = head;
			for (int i = 0; i < index; i++)
				theNode = theNode.getNext();
			return theNode;
			
			// ã���� �ϴ� ����� �ε����� ��ü ����� ������ ũ�ٸ�
		} else {
			
			// tail���� prev�� �̿��ؼ� �ε����� �ش��ϴ� ��带 ã���ϴ�.
			theNode = tail;
			for (int i = size - 1; i > index; i--)
				theNode = theNode.getPrev();
			return theNode;
		}
	}

	// ù��° ��� ����
	public void removeFirst() {
		Node newNode = head;
		head = newNode.getNext();
		newNode = null;

		// head�� null�� �ƴϸ�, ��尡 �̹� �����ϴ� ���� �ǹ��Ѵ�.
		// ��, (���ŵ� ���)<->(head)<->(data)<->....<->(data) ����
		// ������ ù ���� �и���Ű�� ���� �ι�° ����� prev�� null�� �������ش�.
		if (head != null)
			head.setPrev(null);
		size--;
	}

	// �߰� ��� ����
	public void remove(int index) {
		if (index == 0)
			removeFirst();
		else {
			Node newNode;
			Node left = node(index - 1);
			newNode = left.getNext();
			left.setNext(left.getNext().getNext());
			
			// left�� next�� tail�� �ƴ϶��
			if( left.getNext() != null)
				// left�� ���� ��� ���� left�� prev�� ����
				left.getNext().setPrev(left);
			
			if (newNode == tail)
				tail = left;
			newNode = null;
			size--;
		}
	}

	// ������ ��� ����
	public void removeLast() {
		remove(size - 1);
	}

	// ����Ʈ ũ�� ��ȯ
	public int getSize() {
		return size;
	}
	
	// Ư�� �ε����� ��ġ�� ������ ��ȯ
	public int get(int index) {
		Node newNode = node(index);
		return newNode.getData();
	}
	
	// Ư�� �������� �ε��� ã��
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

	// ���� ������ ��
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
