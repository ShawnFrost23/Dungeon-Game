package unsw.dungeon.back;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

public class Player implements Moveable, Observer {
	private Cell location;
	
	public Player() {
		
	}
	
	public void setLocation(Cell location) {
		this.location = location;
	}
	
	public Cell getLocation() {
		return this.location;
	}
	
	public void push(Direction d) {
		this.location.adjacent(d).push(this, d);
	}
	
	// public void swing(Direction d){ }
	
	public void onEnter(CellEnteredEvent event) {
		Moveable who = event.getWhoEntered();
		if (who instanceof Enemy) {
			this.touchEnemy();
		}
	}
	
	public void touchEnemy() {
		this.kill();
	}
	
	public void kill() {
		System.out.println("Ded");
	}

	@Override
	public boolean canMove(Direction d) {
		return !this.location.adjacent(d).isCollidable();
	}

	@Override
	public void move(Direction d) {
		this.location.exit(this);
		this.location = this.location.adjacent(d);
		this.location.enter(this);
	}

	@Override
	public int getZ() {
		return 998;
	}

	@Override
	public char getTexture() {
		return 'P';
	}

	@Override
	public void notifyOf(Event event) {
		if (event instanceof CellEnteredEvent) {
			this.onEnter((CellEnteredEvent) event);
		}
	}
}
