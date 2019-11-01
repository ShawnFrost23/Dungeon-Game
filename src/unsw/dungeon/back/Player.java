package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.PlayerKilledEvent;
import unsw.dungeon.back.event.Subject;

public class Player implements Moveable, Subject, Observer {
	private Cell location;
	private List<Observer> observers;
	private boolean isInvincible;
	private int invincibleDuration;
	private boolean hasKey;
	
	public Player(Cell c) {
		this.observers = new ArrayList<Observer>();
		this.location = c;
		this.isInvincible = false;
		this.invincibleDuration = 0;
		this.hasKey = false;

	}
	
	/**
	 * Get the location of this player.
	 * @return this player's location
	 */
	public Cell getLocation() {
		return this.location;
	}
	
	public void setisInvincible(boolean status) {
		this.isInvincible = true;
	}
	
	public void setinvincibleDuration(int time) {
		this.invincibleDuration = time;
	}
	
	public boolean getisInvincible() {
		return this.isInvincible;
	}
	
	public int getinvincibleDuration() {
		return this.invincibleDuration;
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
	 * Signal that the player is trying to swing a sword from their current
	 * location in a given direction. Note: they may or may not have a sword to
	 * swing, this function will be called nonetheless.
	 * @param d direction sword swing is attempted in
	 */
	public void swingSword(Direction d) {
		if (this.isHoldingSword()) {
			this.location.adjacent(d).hitWithSword();
			this.swordDurability -= 1;
			if (this.swordDurability == 0) {
				this.location.exit(this); // TODO: YA, hack! new CellSwordBroke event ...
				this.location.enter(this);
			}
		}
	}
	
	/**
	 * Signal that this player has touched an enemy. This will result in either
	 * the death of the enemy, or the death of the player.
	 */
	public void touchEnemy(Enemy e) {
		
		this.notifyAllOf(new PlayerKilledEvent());
	}
	
	public void invincibilityOn() {
		this.isInvincible = true;
		this.invincibleDuration = 15;
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
	
	private void onEnter(CellEnteredEvent event) {
		Moveable who = event.getWhoEntered();
		if (who instanceof Enemy) {
			this.touchEnemy((Enemy) who);
		}
	}

	public boolean hasKey() {
		return this.hasKey;
	}

	public void pickUp(Key key) {
		this.location.removeEntity(key);
		this.hasKey = true;
	}
	
	/// TODO{Nick} this is poor code, built because I don't want to implement
	// something "nice" that goes against Arth's key-pickup system. I WILL FIX
	// IT LATER, but it ought to be functionally fine as is.
	private int swordDurability = 0;
	public void pickupSword(Sword s) {
		this.swordDurability = 5;
		this.location.removeEntity(s);
	}
	public boolean isHoldingSword() {
		return this.swordDurability != 0;
	}
}
