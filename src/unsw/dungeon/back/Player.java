package unsw.dungeon.back;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

public class Player implements Moveable, Observer {
	private Cell location;
		
	/**
	 * Set the location of this player.
	 * @param location location to set
	 */
	public void setLocation(Cell location) {
		this.location = location;
	}
	
	/**
	 * Get the location of this player.
	 * @return this player's location
	 */
	public Cell getLocation() {
		return this.location;
	}
	
	/**
	 * Signal that the player is trying to "push" from their current location
	 * in the given direction.
	 * @param d direction to push in
	 */
	public void push(Direction d) {
		this.location.adjacent(d).push(this, d);
	}

	/**
	 * Signal that this player has touched an enemy. This will result in either
	 * the death of the enemy, or the death of the player.
	 */
	public void touchEnemy(Enemy e) {
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
	
	private void onEnter(CellEnteredEvent event) {
		Moveable who = event.getWhoEntered();
		if (who instanceof Enemy) {
			this.touchEnemy((Enemy) who);
		}
	}
}
