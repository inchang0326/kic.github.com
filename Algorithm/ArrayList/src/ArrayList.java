
public class ArrayList {
	private int size = 0;
	private int[] list = new int[100];
	
	// �������� ������ ����
	public void add(int data) {
		list[size] = data;
		size++;
	}
	
	// �߰��� ������ ����
	public void add(int index, int data) {
		for(int i=size-1; i>=index; i--) {
			list[i + 1] = list[i];
		}
		list[index] = data;
		size++;
	}
	
	// ó���� ������ ����
	public void addFirst(int data) {
		add(0, data);
		/*
		for(int i=size-1; i>=0; i--) {
			list[i + 1] = list[i];
		}
		list[0] = data;
		size++;
		*/
	}
	
	// ������ ����
	public void remove(int index) {
		for(int i=index+1; i<size; i++) {
			list[i-1] = list[i];
		}
		size--;
		list[size] = (Integer) null;
	}

	public void removeFirst() {
		remove(0);
	}
	
	public void removeLast() {
		remove(size-1);
	}
	
	// ������ ���� �� ��ȯ
	public int remove2(int index) {
		int removedData = list[index];
		for(int i=index+1; i<size; i++) {
			list[i-1] = list[i];
		}
		size--;
		list[size] = (Integer) null;
		return removedData;
	}
	
	// ������ ��������
	public int get(int index) {
		return list[index];
	}
	
	// ����Ʈ ũ�� ��������
	public int size() {
		return size;
	}
	
	// �ش� �����͸� �����ϰ� �ִ� �ε��� ����
	public int indexOf(int data){
		int indexes[] = new int[100];
		for(int i=0; i<size; i++){
	        if(data == list[i]){
	            return i;
	        }
	    }
	    return -1;
	}
	
	// ������ ��
	public String toString() {
	    String str = "[";
	    for(int i=0; i<size; i++){
	        str += list[i];
	        if(i < size-1) str += ", ";
	    }
	    return str + "]";
	}
}
