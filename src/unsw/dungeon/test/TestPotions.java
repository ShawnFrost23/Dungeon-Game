package unsw.dungeon.test;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

public class TestPotions {

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
	 * "If a player walks over a treasure
	 *  the treasure should no longer be on the map"
	 */
	@Test
	public void AC2() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "      _ \n"
			+ "  P *   \n"
			+ "        \n"
			+ "    _   \n"
			+ " _      \n"
		);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "      _ \n"
			+ "    P   \n"
			+ "        \n"
			+ "    _   \n"
			+ " _      \n"
			, g.getBoardString()
		);
		
		Game g1 = Game.createGame(new ImpossibleGoal(), ""
				+ "      _ \n"
				+ "  P *   \n"
				+ "        \n"
				+ "    _   \n"
				+ " _      \n"
			);
			g1.movePlayer(Direction.RIGHT);
			g1.movePlayer(Direction.RIGHT);
			g1.movePlayer(Direction.RIGHT);
			
			assertEquals(""
				+ "      _ \n"
				+ "     P  \n"
				+ "        \n"
				+ "    _   \n"
				+ " _      \n"
				, g1.getBoardString()
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
