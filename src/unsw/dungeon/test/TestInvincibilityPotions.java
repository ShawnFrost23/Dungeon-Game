package unsw.dungeon.test;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;


/**
 * Tests for the Invinncibility Potions user story.
 */
public class TestInvincibilityPotions {
	/**
	 * Potions are loaded in from the map file and rendered in their
	 * correct position."
	 */
	@Test
	public void AC1() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "      * \n"
			+ "        \n"
			+ "  P     \n"
			+ "        \n"
			+ " *      \n"
		);
		
		assertEquals(""
			+ "      * \n"
			+ "        \n"
			+ "  P     \n"
			+ "        \n"
			+ " *      \n"
			, g.getBoardString()
		);
	}
	
	/**
	 * "Invincibility potions are automatically picked up when the player walks
	 * over them."
	 */
	@Test
	public void AC2() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "   P*   \n"
		);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "     P  \n"
			, g.getBoardString()
		);
	}
	
	/**
	 * "Walking over an invincibility potion will trigger "invincibility" for 15
	 * seconds. In the invincible state, the player will kill enemies on
	 * collision."
	 */
	@Test
	public void AC3() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "   P***!!!\n"
		);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "      P!!!\n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertFalse(g.getHasLost());

		assertEquals(""
			+ "       P!!\n"
			, g.getBoardString()
		);
		
		for (int i = 0; i < 14; ++i) {
			g.tickBuffs();
		}
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "        P!\n"
			, g.getBoardString()
		);
		
		assertFalse(g.getHasLost());
		
		g.tickBuffs();
		
		g.movePlayer(Direction.RIGHT);
		
		assertTrue(g.getHasLost());
	}
	
	/**
	 * "Enemies will run away from a player while invincible (try to maximise
	 * their distance to the player rather than minimise it)."
	 */
	@Test
	public void AC4() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "W!   P*   !  W\n"
		);
		
		g.tickBuffs();
		
		g.moveEnemies();

		assertEquals(g.getBoardString(),""
			+ "W !  P*  !   W\n"
		);
		
		g.movePlayer(Direction.RIGHT);
		
		g.moveEnemies();
		
		assertEquals(g.getBoardString(),""
			+ "W!    P   !  W\n"
		);
		
		g.moveEnemies();

		assertEquals(g.getBoardString(),""
			+ "W!    P    ! W\n"
		);
		
		for (int i = 0; i < 15; ++i) {
			g.tickBuffs();
		}
		
		g.moveEnemies();
		
		assertEquals(g.getBoardString(),""
			+ "W !   P   !  W\n"
		);
	}

	
	/**
	 * "The player avatar blinks when there are less than 2 seconds of
	 * invincibility remaining."
	 * 
	 * Note: nothing is tested for here -- the entirety of this functionality is
	 * (for now) delegated to the UI. 
	 */
	@Test
	public void AC5() {
		
	}
	
}
