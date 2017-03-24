
public class Player {
	private int[] resultCount = new int[3];
	private PlayingStrategy strategy;
	public void setStrategy(PlayingStrategy strategy) {
		this.strategy = strategy;
	}
	public HandType nextHand(){
		return strategy.nextHand();
	}
	private int getGameCount(){
		int sum = 0;
		for(int i: resultCount) sum += i;
		return sum;
	}
	public void setResult(ResultType currentResult){
		++resultCount[currentResult.ordinal()];
	}
	public String toString(String name){
		return String.format("%s�� ���Ӽ�: %d / %d��, %d��, %d��", name, getGameCount(), resultCount[0], resultCount[1], resultCount[2]);
	}
}
