package observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Buttons implements Observable {
	
	//ukoliko se menja observable on obavestava sve koji imaju interes od toga (sve observere)
	
	private int counter;
	private List<Observer> observers = new ArrayList<Observer>();
	
	public void setCounter(int counter) {
		this.counter = counter;
		notifyObservers();
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		Iterator<Observer> it = observers.iterator();
		while(it.hasNext())
			it.next().update(counter);
		
	}

}

