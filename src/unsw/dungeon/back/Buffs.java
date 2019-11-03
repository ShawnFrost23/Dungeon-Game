package unsw.dungeon.back;

/**
 * The Buffs object encodes a counter that represents the duration of the
 * invincibility effect. 
 * @see {@link Player}.
 */
public class Buffs {
	private int invincibilityDuration;
	
	/**
	 * Construct a new Buffs object.
	 */
	public Buffs() {
		this.invincibilityDuration = 0;
	}
	
	/**
	 * Get whether the invincibility buff is still present.
	 * @return true if invincibility is still present
	 */
	public boolean isInvincible() {
		return this.invincibilityDuration != 0;
	}
	

	/**
	 * Starts an invincibility buff of duration 15s.
	 */
	public void addInvincibility() {
		this.invincibilityDuration = 15;
	}

	/**
	 * Pass one second of duration for the invincibility potion effect. 
	 */
	public void tick() {
		if (this.isInvincible()) {
			this.invincibilityDuration -=1;
		}
	}
	
}
