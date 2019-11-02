package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.Subject;
import unsw.dungeon.back.event.ExitEvent;

public class Exit implements Entity, Observer, Subject{
	private Cell location;
	private List<Observer> observers;

	public Exit(Cell c) {
		this.observers = new ArrayList<Observer>(); 
		// treasure on given cell
		this.location = c;
	}
	
	@Override
	public int getZ() {
		return 990;
	}

	@Override
	public char getTexture() {
		return 'E';
	}
	
	@Override
	public void attachListener(Observer observer) {
		this.observers.add(observer);
	}

	@Override
	public void detachListener(Observer observer) {
		this.observers.remove(observer);
		
	}

	@Override
	public void notifyAllOf(Event event) {
		for (Observer observer : this.observers) {
			observer.notifyOf(event);
		}
	}
	
	@Override
	public void notifyOf(Event event) {
		if (event instanceof CellEnteredEvent) {
			this.onEnter((CellEnteredEvent) event);
		} 
		
	}
	
	public void exit() {
		this.notifyAllOf(new ExitEvent());
	}
	
	private void onEnter(CellEnteredEvent event) {
		if (event.getWhoEntered() instanceof Player) {
			//Calls pickUp
			this.exit();
		}
	}
	
}
