
public class CircularlyLinkedList {
	private Node head;
	private Node tail;
	private int size = 0;

	// ó���� ��� ����
	public void addFirst(int data) {
		Node newNode = new Node(data);
		newNode.setNext(head);
		head = newNode;
		if (head.getNext() == null)
			tail = head;

		// �׻� tail�� next�� head�� ��ȯ������ �����Ѵ�.
		tail.setNext(head);
		size++;
	}

	// �������� ��� ����
	public void addLast(int data) {
		if (size == 0)
			addFirst(data);
		else {
			Node newNode = new Node(data);
			tail.setNext(newNode);
			tail = newNode;

			// �׻� tail�� next�� head�� ��ȯ������ �����Ѵ�.
			tail.setNext(head);
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
			newNode.setNext(right);
			size++;
			if (newNode.getNext() == null) {
				tail = newNode;

				// �׻� tail�� next�� head�� ��ȯ������ �����Ѵ�.
				tail.setNext(head);
			}
		}
	}

	// ��� ã��
	private Node node(int index) {
		Node theNode = head;
		for (int i = 0; i < index; i++)
			theNode = theNode.getNext();
		return theNode;
	}

	// ù��° ��� ����
	public void removeFirst() {
		Node newNode = head;
		head = newNode.getNext();
		newNode = null;
		size--;

		// �׻� tail�� next�� head�� ��ȯ������ �����Ѵ�.
		tail.setNext(head);
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
			if (newNode == tail) {
				tail = left;
				
				// �׻� tail�� next�� head�� ��ȯ������ �����Ѵ�.
				tail.setNext(head);
			}
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

	// Ư�� �������� �ε��� ��ȯ
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

	// ���� ������ ��
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
