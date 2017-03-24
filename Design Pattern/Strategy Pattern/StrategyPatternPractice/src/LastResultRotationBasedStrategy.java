import java.util.Random;

public class LastResultRotationBasedStrategy implements PlayingStrategy {
	
	private Random randomGen = new Random();
	private HandType prevHand = HandType.valueOf(randomGen.nextInt(3));
	private ResultType prevResult = ResultType.valueOf(randomGen.nextInt(3));
	
	public void setPrevHand(HandType prevHand){
		this.prevHand = prevHand;
	}
	
	@Override
	public void recordHistory(ResultType currentResult){
		this.prevResult = currentResult;	
	}
	
	@Override
	public HandType nextHand(){
		switch(prevResult){
			//	LastResultBasedStrategy ����
			/*	������� ������ �ٲ� �� ����-����-�� ������ �ٲٴ� ������ �ִ�. ���� LastResultBased ������ �����鼭 ����-����-�� ȸ�� ���������� ������ �ٲ��ش�.
			 * 	(��ó: http://www.huffingtonpost.kr/2014/05/02/story_n_5251458.html).
			 */
			case WON  :{
				/*
				 * 	Ex) �� : ����, ��� : ���ڱ�
				 *		���� �����ϱ� ������ �ٲ� ���̰�, ����-����-�� ���� ���� ������ �� ���̴�. / ���� ���� ������ �̱�� �ָ��� ����.
				 */
//				System.out.printf("%s���� �̰��.\n", prevHand);
				return HandType.valueOf((prevHand.failValueOf().next().winValueOf().ordinal()));	
			}
			case DRAWN:{
				/*
				 * 	Ex) �� : ����, ��� : ����
				 *		������� ���� ����-����-�� ���� ���� ���� �� ���̶� ���� ������ ���̴�. / ���� ���� ���� �̱�� ������ �� ���̰�, �� ������ �̱�� ������ ����.
				 */
//				System.out.printf("%s���� ����.\n", prevHand);
				return HandType.valueOf((prevHand.next().winValueOf().winValueOf().ordinal()));			
			}
			case LOST :{
				/*
				 * 	������ �̰��� �� ������ �´� ���� �״�� ���� ������ �����Ƿ� LastResultBasedStrategy�� ������ �״�� ������.
				 */
//				System.out.printf("%s���� ����.\n", prevHand);
				return prevHand.winValueOf().winValueOf();
			}
		}
		return null;
	}
}