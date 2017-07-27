
public class Main {
	public static void main(String args[]) {
		Stack stack = new Stack();
		System.out.println("Size\t: " + stack.getSize());
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
		System.out.println("Result\t: " + stack.toString());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());		
		System.out.println("Result\t: " + stack.toString());
	}
}
