package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

public class Buffs {
	private int invincibilityDuration;
	
	public Buffs() {
		this.invincibilityDuration = 0;
	}
	
	public boolean isInvincible() {
		return this.invincibilityDuration != 0;
	}
	

	public void addInvincibility() {
		this.invincibilityDuration = 15;
	}

	public void tick() {
		if (this.isInvincible()) {
			this.invincibilityDuration -=1;
		}
	}
	
}
