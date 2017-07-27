
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

	// Queue의 enqueue - 데이터 삽입 메소드 / ex) add(), offer()
	// Queue의 add() 메소드는 성공시 true를 반환하고 데이터를 삽입한다. 공간이 꽉 찼을 경우, 예외 처리 한다.
	public boolean add(int data) {
		if (!isFull()) {
			queue[rear++] = data;
			size++;
			return true;
		} else
			throw new IllegalStateException();
	}

	// Queue의 offer() 메소드는 성공시 true를 반환하고 데이터를 삽입한다. 공간이 꽉 찼을 경우, false를 반환한다.
	public boolean offer(int data) {
		if (!isFull()) {
			queue[rear++] = data;
			size++;
			return true;
		} else
			return false;
	}

	// Queue deqeue - 데이터 삭제 메소드 / ex) remove(), poll()
	// Queue remove() 메소드는 front에 있는 데이터를 반환하고 삭제한다. 공간이 없을 경우 예외 처리 한다.
	public int remove() {
		if (!isEmpty()) {
			int data = queue[front];
			queue[front++] = 0;
			if(front == queue.length) front = rear = 0;
			// Queue의 단점, 데이터 삭제 시, 이후 모든 데이터를 앞으로 옮겨주어야 함
			/*
			 * for(int i=queue.length-1; i>0; i--) { queue[i-1] = queue[i]; }
			 */			
			size--;
			return data;
		} else
			throw new IllegalStateException();
	}

	// Queue poll() 메소드는 front에 있는 데이터를 반환하고 삭제한다. 공간이 없을 경우 null을 반환한다.
	public int poll() {
		if (!isEmpty()) {
			int data = queue[front];
			queue[front++] = 0;
			if(front == queue.length) front = rear = 0;
			// Queue의 단점, 데이터 삭제 시, 이후 모든 데이터를 앞으로 옮겨주어야 함
			/*
			 * for(int i=queue.length-1; i>0; i--) { queue[i-1] = queue[i]; }
			 */
			size--;
			return data;
		} else
			return (Integer) null;
	}

	// Queue의 element() 메소드는 queue의 front를 반환하지만 삭제하지 않는다. 공간이 없을 경우, 예외 처리 한다.
	public int element() {
		if (!isEmpty()) {
			return queue[front];
		} else
			throw new IllegalStateException();
	}

	// Queue의 peek() 메소드는 queue의 front를 반환하지만 삭제하지 않는다. 공간이 없을 경우, null을 반환한다.
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
