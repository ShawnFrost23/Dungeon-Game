package unsw.dungeon.test;

import static org.junit.Assert.fail;
import org.junit.Test;

import unsw.dungeon.back.*;

/**
 * Tests for the Enemies user story.
 */
public class TestEnemies {
	/**
	 * "Enemies are loaded from the map file and rendered in their correct
	 * position."
	 */
	@Test
	public void AC1() {
		fail("Test not implemented!");
	}
	
	/**
	 * "Enemies move toward the player at one tile per half second (left, right,
	 * up, down)."
	 * 
	 * @Note Only tests the "moves toward the player", not for the existence of
	 * any timers.
	 */
	@Test
	public void AC2() {
		fail("Test not implemented!");
	}
	
	/**
	 * "Enemies do not move through walls, push boulders. Every tick, they
	 * choose their move (or stay still) in a way that minimises their distance
	 * (L2) to the player's current position."
	 */
	@Test
	public void AC3() {
		fail("Test not implemented!");
	}
	
	/**
	 * "Enemies will not stand on top of one another."
	 */
	@Test
	public void AC4() {
		fail("Test not implemented!");
	}
	
	/**
	 * "If enemies collide with the player (they walk into the player or the
	 * player walks into them), the player dies. It is sufficient here that
	 * death = the map resets."
	 */
	@Test
	public void AC5() {
		fail("Test not implemented!");
	}

	/**
	 * "Enemies do not start moving until the player presses makes their first
	 * move -- they are 'paused' until the player is ready to start."
	 * 
	 * @Note Doesn't actually test for anything.
	 */
	@Test
	public void AC6() {
		fail("Test not implemented!");
	}
	
}
