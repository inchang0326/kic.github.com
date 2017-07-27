
public class Stack {
	// 사이즈를 5로 제한
	private int stack[] = new int[5];
	private int top = -1;
	private int size = 0;

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isFull() {
		return size == stack.length;
	}

	public void push(int data) {
		if (overFlow())
			throw new IndexOutOfBoundsException();
		stack[++top] = data;
		size++;
	}

	private boolean overFlow() {
		if (isFull())
			return true;
		else
			return false;
	}

	public int pop() {
		int data;
		if (underFlow())
			throw new IndexOutOfBoundsException();
		data = stack[top--];
		size--;
		return data;
	}

	private boolean underFlow() {
		if (isEmpty())
			return true;
		else
			return false;
	}

	public int getSize() {
		return size;
	}

	public String toString() {
		String str = "[";
		for (int i = 0; i < size; i++) {
			str += String.valueOf(stack[i]);
			if (i != size - 1)
				str += ", ";
		}
		return str += "]";
	}
}
