/*
** SportNewsPublisher Ŭ������ �𵨸� 

1. SportsNewsPublisher Ŭ���� 
- ���ǻ�� �����ڵ��� ����ϰ� ������ ����Ʈ�� ������ �뺸������ �˾ƾ� �Ѵ�.
- �����ڰ� ���� �� ����� ����� �� �ְ� �뺸���� ������ ���� ���ŵǾ�� �Ѵ�. 
- ���ǻ�� �� �����ڸ��� ���ϴ� �뺸������ �°� ���� �ҽ��� �����־�� �Ѵ�. 

1) �������
�� ArrayList<Observer> observers          : ��� �����ڸ� �����ϱ� ���� �迭 ����
�� ArrayList<String> newsList             : ���� �ҽ��� �����ϱ� ���� �迭 ����
�� ArrayList<Integer> intervalList        : �� �������� �뺸������ �����ϱ� ���� �迭 ����
�� ArrayList<Integer> currentIntervalList : �������� ���� �뺸������ Ȯ���ϱ� ���� �迭 ����

2) ����Լ�
�� pulibc void addObserver(Observer o)
- observers ������ �ش� �����ڸ� ���
- intervalList�� currentIntervalList�� �ش� �������� �뺸������ ����

�� public void deleteObserver(Observer o)
- observers �������� �ش� �����ڸ� ����
- intervalList�� currentIntervalList���� �ش� �������� �뺸������ ����

�� public void setNewsFeed(String news)
- ���ο� �ҽ��� newsList ������ ����
- ��� �������� �뺸������ 1�� ����
- isItTiming �޼ҵ尡 True ���� ��ȯ�ϸ� notifyObservers �޼ҵ� �����ϰ� �뺸������ �ʱ�ȭ

�� private booelan isItTiming(int i)
- �ش� �������� �뺸������ �� �Ǿ��� ��� True�� ��ȯ

�� void notifyObservers(Observer o)
- newsList ������ update �޼ҵ忡 �Ű��Ͽ� �������� �ҽ��� ������Ʈ
*/

import java.util.ArrayList;

public class SportsNewsPublisher implements NewsPublisher {
	// ���� �����ڸ� ���� ������ �� �ִ� ������� ArrayList�� ����
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