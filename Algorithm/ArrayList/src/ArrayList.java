
public class ArrayList {
	private int size = 0;
	private int[] list = new int[100];
	
	// 마지막에 데이터 삽입
	public void add(int data) {
		list[size] = data;
		size++;
	}
	
	// 중간에 데이터 삽입
	public void add(int index, int data) {
		for(int i=size-1; i>=index; i--) {
			list[i + 1] = list[i];
		}
		list[index] = data;
		size++;
	}
	
	// 처음에 데이터 삽입
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
	
	// 데이터 삭제
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
	
	// 데이터 삭제 및 반환
	public int remove2(int index) {
		int removedData = list[index];
		for(int i=index+1; i<size; i++) {
			list[i-1] = list[i];
		}
		size--;
		list[size] = (Integer) null;
		return removedData;
	}
	
	// 데이터 가져오기
	public int get(int index) {
		return list[index];
	}
	
	// 리스트 크기 가져오기
	public int size() {
		return size;
	}
	
	// 해당 데이터를 포함하고 있는 인덱스 추출
	public int indexOf(int data){
		int indexes[] = new int[100];
		for(int i=0; i<size; i++){
	        if(data == list[i]){
	            return i;
	        }
	    }
	    return -1;
	}
	
	// 데이터 명세
	public String toString() {
	    String str = "[";
	    for(int i=0; i<size; i++){
	        str += list[i];
	        if(i < size-1) str += ", ";
	    }
	    return str + "]";
	}
}
