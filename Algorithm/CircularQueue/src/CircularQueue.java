
public class CircularQueue {

	private int queue[] = new int[6];
	private int front = 0;
	private int rear = 0;
	private int size = 0;

	private boolean isEmpty() {
		return size == 0;
	}

	// ��ȭ���¸� �Ǻ��Ѵ�.
	private boolean isFull() {
		// rear�� front���� Ŭ ���, 2���� : rear�� queue.length-1 �Ǵ� queue.length-2�� ���
		if (rear > front) {
			return ((rear == queue.length - 1) && (front == 0));
		}
		// front�� rear���� Ŭ ���
		else return rear == front-1;
	}

	public boolean add(int data) {
		if (!isFull()) {
			queue[rear++] = data;

			// ���� ���� ��, �������� ���� ť�� ������ �� �ֵ��� rear �ε��� ó��
			if (rear == queue.length) rear = rear % queue.length;
			size++;
			return true;
		} else {
			System.out.println("��ȭ����");
			throw new IllegalStateException();
		}
	}

	public boolean offer(int data) {
		if (!isFull()) {
			queue[rear++] = data;
			
			// ���� ���� ��, �������� ���� ť�� ������ �� �ֵ��� rear �ε��� ó��
			if (rear == queue.length) rear = rear % queue.length;
			size++;
			return true;
		} else {
			System.out.println("��ȭ����");		
			return false;
		}
	}

	public int remove() {
		if (!isEmpty()) {
			int data = queue[front];
			queue[front++] = 0;

			// ���� ���� ��, �������� ���� ť�� ������ �� �ֵ��� front �ε��� ó��
			if (front == queue.length) front = front % queue.length;
			size--;
			return data;
		} else
			throw new IllegalStateException();
	}

	public int poll() {
		if (!isEmpty()) {
			int data = queue[front];
			queue[front++] = 0;
			
			// ���� ���� ��, �������� ���� ť�� ������ �� �ֵ��� front �ε��� ó��
			if (front == queue.length) front = front % queue.length;
			size--;
			return data;
		} else
			return (Integer) null;
	}

	public int element() {
		if (!isEmpty()) {
			return queue[front];
		} else
			throw new IllegalStateException();
	}

	public int peek() {
		if (!isEmpty()) {
			return queue[front];
		} else
			return (Integer) null;
	}

	public int getSize() {
		return size;
	}

	public int indexOf(int data) {
		for (int i = front; i < rear; i++) {
			if (queue[i] == data)
				return i;
		}
		return -1;
	}

	public String toString() {
		String str = "[";
		for (int i = 0; i < queue.length; i++) {
			str += Integer.toString(queue[i]);
			if (i != queue.length - 1)
				str += ", ";
		}
		return str += "]";
	}
}