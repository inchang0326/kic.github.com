/*
** SportNewsPublisher 클래스와 모델링 

1. SportsNewsPublisher 클래스 
- 출판사는 구독자들을 등록하고 구독자 리스트와 각각의 통보시점을 알아야 한다.
- 구독자가 원할 때 등록을 취소할 수 있고 통보시점 데이터 또한 제거되어야 한다. 
- 출판사는 각 관찰자마다 원하는 통보시점에 맞게 뉴스 소식을 전해주어야 한다. 

1) 멤버변수
① ArrayList<Observer> observers          : 모든 구독자를 유지하기 위한 배열 변수
② ArrayList<String> newsList             : 뉴스 소식을 유지하기 위한 배열 변수
③ ArrayList<Integer> intervalList        : 각 구독자의 통보시점을 유지하기 위한 배열 변수
④ ArrayList<Integer> currentIntervalList : 구독자의 현재 통보시점을 확인하기 위한 배열 변수

2) 멤버함수
① pulibc void addObserver(Observer o)
- observers 변수에 해당 구독자를 등록
- intervalList와 currentIntervalList에 해당 구독자의 통보시점을 저장

② public void deleteObserver(Observer o)
- observers 변수에서 해당 구독자를 제거
- intervalList와 currentIntervalList에서 해당 구독자의 통보시점을 제거

③ public void setNewsFeed(String news)
- 새로운 소식을 newsList 변수에 저장
- 모든 구독자의 통보시점을 1씩 감소
- isItTiming 메소드가 True 값을 반환하면 notifyObservers 메소드 수행하고 통보시점을 초기화

④ private booelan isItTiming(int i)
- 해당 관찰자의 통보시점이 다 되었을 경우 True값 반환

⑤ void notifyObservers(Observer o)
- newsList 변수를 update 메소드에 매개하여 구독자의 소식을 업데이트
*/

import java.util.ArrayList;

public class SportsNewsPublisher implements NewsPublisher {
	// 다중 관찰자를 쉽게 유지할 수 있는 방안으로 ArrayList가 있음
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private ArrayList<String> newsList = new ArrayList<String>();
	private ArrayList<Integer> intervalList = new ArrayList<Integer>();
	private ArrayList<Integer> currentIntervalList = new ArrayList<Integer>();
	
	private boolean isItTiming(int i){
		if(currentIntervalList.get(i) == 0) return true;
		else return false;
	}
	
	@Override
	public void setNewsFeed(String news) {
		newsList.add(news);
		
		for(int i=0; i<observers.size(); i++){
			currentIntervalList.set(i,	 currentIntervalList.get(i)-1);
		}
		
		for(int i=0; i<observers.size(); i++){
			if(isItTiming(i)){
				notifyObservers(observers.get(i));
				currentIntervalList.set(i, intervalList.get(i));
			}
		}
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
		intervalList.add(o.getInterval());
		currentIntervalList.add(o.getInterval());
	}

	@Override
	public void deleteObserver(Observer o) {
		int index = observers.indexOf(o);
		
		observers.remove(o);	
		intervalList.remove(index);
		currentIntervalList.remove(index);
	}
	
	@Override
	public void notifyObservers(Observer o) {
		o.update(newsList);
	}
}