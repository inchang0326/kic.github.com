package postfix;

interface IntStack{
	boolean isEmpty();
	boolean isFull();
	void push(int item);
	void delete();
	int pop();
	void display();
}

//intÇü array stack
class IntArrayStack implements IntStack{
	private int top;
	private int sizeofstack;
	private int Array[];
	
	public IntArrayStack(int sizeofstack){
		top = -1;
		this.sizeofstack = sizeofstack;
		Array = new int[this.sizeofstack];
	}
	
	public boolean isEmpty() {
		return (top == -1);
	}
	
	public boolean isFull(){
		return (top == sizeofstack-1);
	}
	
	public void push(int item) {
		if(isFull()){
			System.out.println("The stack is full at the moment. No more spaces");
		}
		else{
			Array[++top] = item;
		}
	}
	
	public int pop() {
		if(isEmpty()){
			System.out.println("The stack is empty at the moment. No data at all");
			return 0;
		}
		else{
			return Array[top--];
		}
	}
	
	public void delete(){
		if(isEmpty()){
			System.out.println("The stack is empty at the moment. No data at all");
		}
		else{
			top--;
		}
	}

	public void display(){
		System.out.print(">> ");
		for(int i=0;i<=this.currentsizeofstack();i++)
			System.out.print((Array[i]));	
		System.out.println();
	}
	
	public int currentsizeofstack(){
		return top;
	}
	
	public int getArray(int index){
		return Array[index];
	}
}