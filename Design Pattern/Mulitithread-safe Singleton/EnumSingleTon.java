// ���� ������ ȯ�濡���� singleton�� ������ ���� ���� �� �ִ�.
// enum�� Ư¡ - �ϳ����� �����Ѵٴ� ����

public enum EnumSingleTon {
	UNIQUE;
	public static EnumSingleTon getInstance(){
		return UNIQUE;
	}
}
