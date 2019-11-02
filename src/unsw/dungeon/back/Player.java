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
	
	private Buffs buffs;
	private Key heldKey;
	private int swordDurability;
	

	public Player(Cell c) {
		this.location = c;
		this.observers = new ArrayList<Observer>();
		this.heldKey = null;
		this.swordDurability = 0;
		this.buffs = new Buffs();

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
				// HACK! this if your sword breaks while standing on a dropped
				// key, this will cause you to pick the key up!
				this.location.exit(this);
				this.location.enter(this);
			}
		}
	}
	
	/**
	 * Signal that this player has touched an enemy. This will result in either
	 * the death of the enemy, or the death of the player.
	 */
	public void touchEnemy(Enemy e) {
		if (this.isInvincible()) {
			//System.
			e.kill();
		} else {
			this.notifyAllOf(new PlayerKilledEvent());
		}
	}
	
	public boolean isInvincible() {
		return this.buffs.isInvincible();
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

	public boolean isHoldingKey() {
		return this.heldKey != null;
	}

	public boolean isHoldingSword() {
		return this.swordDurability != 0;
	}
	
	public boolean hasKey(int ID) {
		if (!this.isHoldingKey()) {
			return false;
		}
		return this.heldKey.getID() == ID;
	}

	public void take(Key key) {
		this.location.removeEntity(key);
		this.heldKey = key;
	}
	
	public void take(Sword sword) {
		this.location.removeEntity(sword);
		this.swordDurability = 5;
	}
	
	public void take(InvincibilityPotion ip) {
		this.location.removeEntity(ip);
		this.buffs.addInvincibility();
	}

	public void dropHeldKey() {
		this.location.addEntity(this.heldKey); // TODO: check that this location doesn't have a key under it ...
		this.heldKey = null;
	}
	
	public void consumeHeldKey() {
		this.heldKey = null;
	}
	
	public void tickBuffs() {
		this.buffs.tick();
	}
}
