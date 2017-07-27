
public class CircularQueue {

	private int queue[] = new int[6];
	private int front = 0;
	private int rear = 0;
	private int size = 0;

	private boolean isEmpty() {
		return size == 0;
	}

	// 포화상태를 판별한다.
	private boolean isFull() {
		// rear이 front보다 클 경우, 2가지 : rear이 queue.length-1 또는 queue.length-2일 경우
		if (rear > front) {
			return ((rear == queue.length - 1) && (front == 0));
		}
		// front가 rear보다 클 경우
		else return rear == front-1;
	}

	public boolean add(int data) {
		if (!isFull()) {
			queue[rear++] = data;

			// 삽입 연산 시, 논리적으로 원형 큐를 유지할 수 있도록 rear 인덱스 처리
			if (rear == queue.length) rear = rear % queue.length;
			size++;
			return true;
		} else {
			System.out.println("포화상태");
			throw new IllegalStateException();
		}
	}

	public boolean offer(int data) {
		if (!isFull()) {
			queue[rear++] = data;
			
			// 삽입 연산 시, 논리적으로 원형 큐를 유지할 수 있도록 rear 인덱스 처리
			if (rear == queue.length) rear = rear % queue.length;
			size++;
			return true;
		} else {
			System.out.println("포화상태");		
			return false;
		}
	}

	public int remove() {
		if (!isEmpty()) {
			int data = queue[front];
			queue[front++] = 0;

			// 삭제 연산 시, 논리적으로 원형 큐를 유지할 수 있도록 front 인덱스 처리
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
			
			// 삭제 연산 시, 논리적으로 원형 큐를 유지할 수 있도록 front 인덱스 처리
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