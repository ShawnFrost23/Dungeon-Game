package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

public class TestSwordplay {
	/**
	 * "Sword items are loaded from map and rendered in correct position."
	 */ 
	@Test
	public void AC1() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ " P S\n"
		);
		fail("Test not implemented!");
	}
	
	/**
	 * "A player can pick up a sword by walking over it (if they are not holding
	 * one already), and their player avatar changes to indicate they are
	 * holding sword."
	 */
	@Test
	public void AC2() {
		fail("Test not implemented!");
	}
	
	/**
	 * "If a player is already holding a sword, walking over it will do nothing
	 * -- when the player walks off of the tile, the original sword will still
	 * be there." 
	 */
	@Test
	public void AC3() {
		fail("Test not implemented!");
	}
	
	/**
	 * "If a player has a sword, they can press one of "w, a, s, d" to attack
	 * the tile immediately to their left, bottom, right, top."
	 */
	@Test
	public void AC4() {
		fail("Test not implemented!");
	}
	
	/**
	 * "If there is an enemy in the tile that is attacked, it is killed
	 * (disappears)."
	 */
	@Test
	public void AC5() {
		fail("Test not implemented!");
	}
	 
	/**
	 * "Every swing of the sword, regardless of whether it hits, causes the
	 * sword to lose one durability. A sword disappears when it loses all of
	 * its durability. Each sword begins with 5 durability."
	 */
	@Test
	public void AC6() {
		fail("Test not implemented!");
	}

	/**
	 * "If the player is standing on top of a sword when their held sword
	 * breaks, they automatically pick it up."
	 */
	@Test
	public void AC7() {
		fail("Test not implemented!");
	}

	/**
	 * "Every swing of the sword, regardless of whether it hits, causes the
	 * sword to lose one durability. A sword disappears when it loses all of its
	 * durability. Each sword begins with 5 durability."
	 */
	@Test
	public void AC8() {
		fail("Test not implemented!");
	}

	/**
	 *  If all enemies have been defeated when an enemy-destruction goal is
	 *  specified, the player wins the game.
	 */
	@Test
	public void AC9() {
		fail("Test not implemented!");
	}
	
	// TODO: also test that swords stood atop of are automatically picked up
	// if you perform your last sting while standing atop a new sword.
	// Viz., "sword-break" should try to instigate a sword-pickup.

}
