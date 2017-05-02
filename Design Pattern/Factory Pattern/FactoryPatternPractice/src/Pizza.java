
public abstract class Pizza {
	private String name;
	protected Pizza(String name){
		this.name = name;
	}
	public void prepare(){
		System.out.printf("%s를 위한 재료 준비중\n", name);		
	}
	public void bake(){
		System.out.printf("%s가 완전히 구어짐\n", name);
	}
	public void cut(){
		System.out.printf("%s가 8조각으로 잘려짐\n", name);
	}
	public void box(){
		System.out.printf("%s가 포장 되었음\n", name);
	}
}
