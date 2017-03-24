
public class Test {

	public static void main(String[] args) {
		
		Player player1 = new Player();
		Player player2 = new Player();
		LastResultBasedStrategy resultBased = new LastResultBasedStrategy();
		LastResultRotationBasedStrategy rotationBased = new LastResultRotationBasedStrategy();
		
		player1.setStrategy(resultBased);
		player2.setStrategy(rotationBased);
		
		for(int i=0; i<100; i++){
			HandType hand1 = player1.nextHand();
			HandType hand2 = player2.nextHand();
			System.out.printf("%s vs %s", hand1, hand2);
			if(hand1 == hand2){ 
				System.out.println("> 결과: 무승부");
				player1.setResult(ResultType.DRAWN);
				resultBased.recordHistory(ResultType.DRAWN);
				
				player2.setResult(ResultType.DRAWN);
				rotationBased.recordHistory(ResultType.DRAWN);
			}
			else if(hand2.winValueOf() == hand1){
				System.out.println("> 결과 : 사용자1 승");
				
				player1.setResult(ResultType.WON);
				resultBased.recordHistory(ResultType.WON);
				
				player2.setResult(ResultType.LOST);	
				rotationBased.recordHistory(ResultType.LOST);
			}
			else{
				System.out.println("> 결과 : 사용자2 승");
				player1.setResult(ResultType.LOST);
				resultBased.recordHistory(ResultType.LOST);
				
				player2.setResult(ResultType.WON);	
				rotationBased.recordHistory(ResultType.WON);
			}	

			resultBased.setPrevHand(hand1);
			rotationBased.setPrevHand(hand2);
		}
		
		System.out.println(player1.toString("사용자1"));
		System.out.println(player2.toString("사용자2"));
	}
}
