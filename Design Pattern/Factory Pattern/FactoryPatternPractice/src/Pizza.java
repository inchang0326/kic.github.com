
public abstract class Pizza {
	private String name;
	protected Pizza(String name){
		this.name = name;
	}
	public void prepare(){
		System.out.printf("%s�� ���� ��� �غ���\n", name);		
	}
	public void bake(){
		System.out.printf("%s�� ������ ������\n", name);
	}
	public void cut(){
		System.out.printf("%s�� 8�������� �߷���\n", name);
	}
	public void box(){
		System.out.printf("%s�� ���� �Ǿ���\n", name);
	}
}
