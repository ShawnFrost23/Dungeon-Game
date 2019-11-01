package unsw.dungeon.back;
import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.PotionPickedUpEvent;
import unsw.dungeon.back.event.Subject;
import unsw.dungeon.back.Player;

public class Potion implements Entity, Observer, Subject {

	private List<Observer> observers;
	private Cell location;
	
	public Potion(Cell c) {
		
		this.observers = new ArrayList<Observer>(); 
		//Potion on given cell
		this.location = c;
	}
	
	public void pickUp() {
		this.notifyAllOf(new PotionPickedUpEvent());
		// Remove from Board cell
		this.location.removeEntity(this);
	}
	
	@Override
	public int getZ() {
		return 997;
	}

	@Override
	public char getTexture() {
		return '*';
	}
	
	@Override
	public void notifyOf(Event event) {
		if (event instanceof CellEnteredEvent) {
			this.onEnter((CellEnteredEvent) event);
		} 
		
	}
	private void onEnter(CellEnteredEvent event) {
		if (event.getWhoEntered() instanceof Player) {
			//Calls pickUp
			this.pickUp();
			((Player)event.getWhoEntered()).invincibilityOn();
		}
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
}
