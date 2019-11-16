package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.CellHitWithSwordEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.CellKeyDroppedEvent;
import unsw.dungeon.back.event.PlayerKilledEvent;
import unsw.dungeon.back.event.Subject;

public class Player implements Moveable, Subject, Observer {
	private Cell location;
	private List<Observer> observers;
	
	private Buffs buffs;
	private Key heldKey;
	private IntegerProperty swordDurability = new SimpleIntegerProperty();
	
	/**
	 * Create a new Player instance.
	 * @param c the cell the player is located on
	 */
	public Player(Cell c) {
		this.location = c;
		this.observers = new ArrayList<Observer>();
		this.heldKey = null;
		this.swordDurability.set(0);
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
	 * Signal that the player is trying to swing their sword from their current
	 * location in a particular direction. If the player has a sword, this will
	 * spend one point of its durability to
	 * {@link Cell#hitWithSword() hitWithSword} the targeted {@link Cell} (the
	 * cell that is in direction <b>d</b> from the player's current location.)
	 * @param d direction sword swing is to be swung in
	 */
	public void swingSword(Direction d) {
		if (this.isHoldingSword()) {
			this.location.adjacent(d).hitWithSword(d);
			this.swordDurability.set(this.swordDurability.get() - 1);
			if (this.swordDurability.get() == 0) {
				// HACK ...
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
			e.kill();
		} else {
			this.kill();
		}
	}
	
	private void kill() {
		this.location.removeEntity(this);
		this.notifyAllOf(new PlayerKilledEvent());
	}

	/**
	 * Get whether the player is currently invincible.
	 * @return true if the player is invincible
	 */
	public boolean isInvincible() {
		return this.buffs.isInvincible();
	}

	/**
	 * Check if the player is currently holding a sword.
	 * @return true if the player is holding a sword
	 */
	public boolean isHoldingSword() {
		return this.swordDurability.get() != 0;
	}
	
	/**
	 * Check if the player is currently holding any key.
	 * @return true if the player is holding a key
	 */
	public boolean isHoldingKey() {
		return this.heldKey != null;
	}
	
	/**
	 * Check if the player is holding a key that matches the provided ID.
	 * @param ID key ID to check against
	 * @return true if the player is holding the key of matching ID.
	 */
	public boolean hasKey(int ID) {
		if (!this.isHoldingKey()) {
			return false;
		}
		return this.heldKey.getID() == ID;
	}

	/**
	 * Take a {@link Key} entity off of the ground, and place it in the player's
	 * inventory.
	 * @param key key to pick up and carry. The key must be located in the same
	 * cell as the player.
	 */
	public void take(Key key) {
		this.location.removeEntity(key);
		this.heldKey = key;
	}

	/**
	 * Take a {@link Sword} entity off of the ground, and place it in the
	 * player's inventory.
	 * @param sword sword to pick up and carry. The sword must be located in the
	 * same cell as the player.
	 */
	public void take(Sword sword) {
		this.location.removeEntity(sword);
		this.swordDurability.set(5);
	}
	
	/**
	 * Take an {@link InvincibilityPotion} entity off of the ground. Consume it.
	 * @param potion invincibility potion to pick up and consume. The potion
	 * must be located in the same cell as the player.
	 */
	public void take(InvincibilityPotion potion) {
		this.location.removeEntity(potion);
		this.buffs.addInvincibility();
	}

	/**
	 * Swap the key currently held by the player with the one in the cell
	 * they're located in. If the player is not holding a key, or if they are
	 * not standing on a key, nothing will occur. 
	 */
	public void swapKey() {
		Key oldHeldKey = this.heldKey;
		this.heldKey = null;
		
		this.location.exit(this);
		this.location.enter(this);
		if (!this.isHoldingKey()) {
			this.heldKey = oldHeldKey;
		} else {
			this.location.dropKey(oldHeldKey);
		}
	}

	/**
	 * Destroy the key that the player is currently holding.
	 */
	public void consumeHeldKey() {
		this.heldKey = null;
	}
	
	/**
	 * Pass one second of duration for all of this player's potion effects. 
	 */
	public void tickBuffs() {
		this.buffs.tick();
	}
	
	/**
	 * Get the Sword durability
	 * @return IntegerProperty of the Sword durability 
	 */
	public IntegerProperty getBuffValue() {
		return buffs.getInvincibilityDuration();
	}
	
	/**
	 * Get whether the this Game's goal has been satisfied.
	 * @return true if the goal has been satisfied
	 */
	public IntegerProperty getSwordDurabilityValue() {
		return swordDurability;
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
	public Texture getTexture() {
		if (this.isHoldingSword()) {
			return new Texture('P', "human_new_with_sword.png");
		} else {
			return new Texture('P', "human_new.png");			
		}
		
	}

	@Override
	public boolean isCollidable() {
		return false;
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
}
