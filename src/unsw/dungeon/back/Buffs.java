package unsw.dungeon.back;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The Buffs object encodes a counter that represents the duration of the
 * invincibility effect. 
 * @see {@link Player}.
 */
public class Buffs {
	private IntegerProperty invincibilityDuration = new SimpleIntegerProperty();

	/**
	 * Construct a new Buffs object.
	 */
	public Buffs() {
		this.invincibilityDuration.set(0);
	}
	
	/**
	 * Get the Sword durability
	 * @return IntegerProperty of the Sword durability 
	 */
	public IntegerProperty getInvincibilityDuration() {
		return invincibilityDuration;
	}
	
	/**
	 * Get whether the invincibility buff is still present.
	 * @return true if invincibility is still present
	 */
	public boolean isInvincible() {
		return this.invincibilityDuration.get() != 0;
	}
	

	/**
	 * Starts an invincibility buff of duration 15s.
	 */
	public void addInvincibility() {
		this.invincibilityDuration.set(15);
	}

	/**
	 * Pass one second of duration for the invincibility potion effect. 
	 */
	public void tick() {
		if (this.isInvincible()) {
			this.invincibilityDuration.set(this.invincibilityDuration.get() - 1);
		}
	}
	
	
	
}
