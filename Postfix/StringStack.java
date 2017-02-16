package postfix;

interface StringStack{
	boolean isEmpty();
	boolean isFull();
	void push(String item);
	void delete();
	String pop();
	void display();
}

class StringArrayStack implements StringStack{
	private int top;
	private int sizeofstack;
	private String Array[];
	
	public StringArrayStack(int sizeofstack){
		top = -1;
		this.sizeofstack = sizeofstack;
		Array = new String[this.sizeofstack];
	}

	public boolean isEmpty() {
		return (top == -1);
	}
	
	public boolean isFull(){
		return (top == sizeofstack-1);
	}
	
	public void push(String item) {
		if(isFull()){
			System.out.println("The stack is full at the moment. No more spaces");
		}
		else{
			Array[++top] = item;
		}
	}

	public String pop() {
		if(isEmpty()){
			System.out.println("The stack is empty at the moment. No data at all");
			return null;
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
			System.out.print(Array[i]);	
		System.out.println();
	}
	
	public int currentsizeofstack(){
		return top;
	}
	
	public String getArray(int index){
		return Array[index];
	}

}