package unsw.dungeon.test;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

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
	
//	public void AC4() {
//		Game g = Game.createGame(new ImpossibleGoal(), ""
//				+ "!   P*   !   \n"
//			);
//		g.movePlayer(Direction.RIGHT);
//		
//		g.moveEnemies();
//		
//		assertEquals(g.getBoardString(),""
//				+ "!   P*    !  \n"
//			);
//			
//		g.moveEnemies();
//		assertEquals(g.getBoardString(),""
//				+ "!   P*     ! \n"
//			);
//		
//	}
	
	
}
