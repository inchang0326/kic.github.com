
public interface PlayingStrategy {
	
	HandType nextHand();
	
	void recordHistory(ResultType currentResult);
}
