package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.Subject;
import unsw.dungeon.back.event.TreasureCollectedEvent;

public class Treasure implements Entity, Observer, Subject {
	private List<Observer> observers;
	private Cell location;
	
	/**
	 * Construct a new Treasure object.
	 * @param c cell the treasure is located at
	 */
	public Treasure(Cell c) {
		this.observers = new ArrayList<Observer>(); 
		this.location = c;
	}
	
	/**
	 * Removes this treasure from its location on the board. Notify all
	 * listeners of a new {@link unsw.dungeon.back.event.TreasureCollectedEvent}.
	 */
	public void collect() {
		this.notifyAllOf(new TreasureCollectedEvent());
		this.location.removeEntity(this);
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
	public int getZ() {
		return 996;
	}

	@Override
	public char getTexture() {
		return 'T';
	}
	
	@Override
	public void notifyOf(Event event) {
		if (event instanceof CellEnteredEvent) {
			this.onEnter((CellEnteredEvent) event);
		} 
	}
	
	private void onEnter(CellEnteredEvent event) {
		if (event.getWhoEntered() instanceof Player) {
			this.collect();
		}
	}
}
