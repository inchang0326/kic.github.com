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
			//	LastResultBasedStrategy 설명
			/*	사람들은 선택을 바꿀 때 가위-바위-보 순으로 바꾸는 경향이 있다. 따라서 LastResultBased 전략을 따르면서 가위-바위-보 회전 순서에따라 선택을 바꿔준다.
			 * 	(출처: http://www.huffingtonpost.kr/2014/05/02/story_n_5251458.html).
			 */
			case WON  :{
				/*
				 * 	Ex) 나 : 가위, 상대 : 보자기
				 *		상대는 졌으니까 선택을 바꿀 것이고, 가위-바위-보 순에 따라 가위를 낼 것이다. / 따라서 나는 가위를 이기는 주먹을 낸다.
				 */
//				System.out.printf("%s내서 이겼다.\n", prevHand);
				return HandType.valueOf((prevHand.failValueOf().next().winValueOf().ordinal()));	
			}
			case DRAWN:{
				/*
				 * 	Ex) 나 : 바위, 상대 : 바위
				 *		비겼으니 내가 가위-바위-보 순에 따라 보를 낼 것이라 상대는 생각할 것이다. / 따라서 상대는 보를 이기는 가위를 낼 것이고, 난 가위를 이기는 바위를 낸다.
				 */
//				System.out.printf("%s내서 비겼다.\n", prevHand);
				return HandType.valueOf((prevHand.next().winValueOf().winValueOf().ordinal()));			
			}
			case LOST :{
				/*
				 * 	상대방이 이겼을 때 이전에 냈던 것을 그대로 내는 경향이 있으므로 LastResultBasedStrategy의 전략을 그대로 따른다.
				 */
//				System.out.printf("%s내서 졌다.\n", prevHand);
				return prevHand.winValueOf().winValueOf();
			}
		}
		return null;
	}
}