
public class Queue {

	private int queue[] = new int[5];
	private int front = 0;
	private int rear = 0;
	private int size = 0;

	private boolean isEmpty() {
		return size == 0;
	}

	private boolean isFull() {
		return size == queue.length;
	}

	// Queue�� enqueue - ������ ���� �޼ҵ� / ex) add(), offer()
	// Queue�� add() �޼ҵ�� ������ true�� ��ȯ�ϰ� �����͸� �����Ѵ�. ������ �� á�� ���, ���� ó�� �Ѵ�.
	public boolean add(int data) {
		if (!isFull()) {
			queue[rear++] = data;
			size++;
			return true;
		} else
			throw new IllegalStateException();
	}

	// Queue�� offer() �޼ҵ�� ������ true�� ��ȯ�ϰ� �����͸� �����Ѵ�. ������ �� á�� ���, false�� ��ȯ�Ѵ�.
	public boolean offer(int data) {
		if (!isFull()) {
			queue[rear++] = data;
			size++;
			return true;
		} else
			return false;
	}

	// Queue deqeue - ������ ���� �޼ҵ� / ex) remove(), poll()
	// Queue remove() �޼ҵ�� front�� �ִ� �����͸� ��ȯ�ϰ� �����Ѵ�. ������ ���� ��� ���� ó�� �Ѵ�.
	public int remove() {
		if (!isEmpty()) {
			int data = queue[front];
			queue[front++] = 0;
			if(front == queue.length) front = rear = 0;
			// Queue�� ����, ������ ���� ��, ���� ��� �����͸� ������ �Ű��־�� ��
			/*
			 * for(int i=queue.length-1; i>0; i--) { queue[i-1] = queue[i]; }
			 */			
			size--;
			return data;
		} else
			throw new IllegalStateException();
	}

	// Queue poll() �޼ҵ�� front�� �ִ� �����͸� ��ȯ�ϰ� �����Ѵ�. ������ ���� ��� null�� ��ȯ�Ѵ�.
	public int poll() {
		if (!isEmpty()) {
			int data = queue[front];
			queue[front++] = 0;
			if(front == queue.length) front = rear = 0;
			// Queue�� ����, ������ ���� ��, ���� ��� �����͸� ������ �Ű��־�� ��
			/*
			 * for(int i=queue.length-1; i>0; i--) { queue[i-1] = queue[i]; }
			 */
			size--;
			return data;
		} else
			return (Integer) null;
	}

	// Queue�� element() �޼ҵ�� queue�� front�� ��ȯ������ �������� �ʴ´�. ������ ���� ���, ���� ó�� �Ѵ�.
	public int element() {
		if (!isEmpty()) {
			return queue[front];
		} else
			throw new IllegalStateException();
	}

	// Queue�� peek() �޼ҵ�� queue�� front�� ��ȯ������ �������� �ʴ´�. ������ ���� ���, null�� ��ȯ�Ѵ�.
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
