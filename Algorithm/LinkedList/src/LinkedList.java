
public class LinkedList {
	private Node head;
	private Node tail;
	private int size = 0;

	// ó���� ��� ����
	public void addFirst(int data) {
		Node newNode = new Node(data);

		// ù ��带 ������ ���� �ǹ� ������ �ι�°�� ������� ������ ������ ������ ��带 ����Ű���� �Ѵ�.
		newNode.setNext(head);

		// head�� ���� ��������� ��带 ���Ѵ�.
		head = newNode;
		size++;

		// head�� next�� ���ٴ� ���� ó�� ������� �� �ϳ��� ��常 �����Ѵٴ� ���� �ǹ��Ѵ�. ��, ��带 �߰��Ҽ��� ù
		// ��尡 ������ �� tail�� �ȴ�.
		if (head.getNext() == null)
			tail = head;
	}

	// �������� ��� ����
	public void addLast(int data) {
		if (size == 0)
			addFirst(data);
		else {

			// ���ο� ��� ����
			Node newNode = new Node(data);

			// ���� tail�� next�� ���� �� ������ ���� �������������ν� �����Ѵ�.
			tail.setNext(newNode);

			// tail�� ���� �� ������ ���� ����
			tail = newNode;
			size++;
		}
	}

	// �߰��� ��� ����
	public void add(int index, int data) {
		if (index == 0)
			addFirst(data);
		else {
			
			// ���ο� ��� ����
			Node newNode = new Node(data);

			// ���ο� ���� ������ ���ʿ� �ִ� ��� ã��
			Node left = node(index - 1);

			// ���ο� ��尡 ������ �����ʿ� �ִ� ��� ã��
			Node right = left.getNext();

			// ���� ��尡 ���ο� ��带 ����
			left.setNext(newNode);

			// ���ο� ��尡 ������ ��带 ����
			newNode.setNext(right);
			size++;

			// ���ο� ����� ���� ��尡 ���ٴ� ���� ������ ����� ���̹Ƿ� tail�� ����
			if (newNode.getNext() == null) {
				tail = newNode;
			}
		}
	}

	// ��� ã��
	private Node node(int index) {
		Node theNode = head;
		for (int i = 0; i < index; i++) theNode = theNode.getNext();
		return theNode;
	}

	// ù��° ��� ����
	public void removeFirst() {
		Node newNode = head;
		head = newNode.getNext();
		newNode = null;
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

			if (newNode == tail)
				tail = left;

			newNode = null;
			size--;
		}
	}

	// ������ ��� ����
	public void removeLast() {
		remove(size-1);
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

	// ���� ������ ��
	public String toString() {
		
		// ��尡 ���ٸ� [] ��ȯ
		if (head == null)
			return "[]";

		Node newNode = head;
		String spec = "[";
		// ������ ���� ���� ��尡 ���� ������ �Ʒ��� ������ ������ ���� ���ܵ˴ϴ�.

		// ��尡 null�� ������ LinkedList ���� ��ȸ
		while (newNode.getNext() != null) {
			spec += newNode.getData() + ", ";

			// ���� ���� �̵�
			newNode = newNode.getNext();
		}

		// ������ ��� ��°�� ���� ����
		spec += newNode.getData();
		return spec + "]";
	}
}
