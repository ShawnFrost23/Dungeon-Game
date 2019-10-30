package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;



public class TestExits {
	/**
	 * "Exits are loaded in from the map file and rendered in their
	 * correct position."
	 */
	@Test
	public void AC1() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "      E \n"
			+ "    _   \n"
			+ "  P     \n"
			+ "    _   \n"
			+ " E      \n"
		);
		
		assertEquals(""
			+ "      E \n"
			+ "    _   \n"
			+ "  P     \n"
			+ "    _   \n"
			+ " E      \n"
			, g.getBoardString()
		);
	}
	
	/**
	 * "Exits can be walked over, for a non-Maze Goal"
	 */
	@Test
	public void AC3() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "  PE \n"
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "   P \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "   EP\n"
			, g.getBoardString()
		);
		
		/**
		 * "Exits can be walked over for all
		 *  entities, accept player for a Maze Goal"
		 */
		
		Game g1 = Game.createGame(new MazeGoal(), ""
				+ "  PB E    \n"
				+ "        B_\n"
			);
			
			g1.movePlayer(Direction.RIGHT);
			g1.movePlayer(Direction.RIGHT);
			
			
			assertFalse(g2.getHasWon());
			assertEquals(""
					+ "    PB    \n"
					+ "        B_\n"
					, g3.getBoardString()
			);
			
	}
		
	/**
	 * "Exits can be walked over, for a non-Maze Goal"
	 */
	@Test
	public void AC2() {
		Game g1 = Game.createGame(new MazeGoal(), ""
			+ " PE  \n"
		);
		
		assertFalse(g1.getHasWon());
		
		g1.movePlayer(Direction.RIGHT);
		
		assertTrue(g1.getHasWon());
		
		Game g2 = Game.createGame(new TreasureGoal(), ""
				+ " P   \n"
				+ " E   \n"
				+ "     \n"
			);
			
		assertFalse(g2.getHasWon());
			
		g2.movePlayer(Direction.RIGHT);
			
		assertFalse(g2.getHasWon());
			
		g2.movePlayer(Direction.DOWN);

		assertFalse(g2.getHasWon());
			
		assertEquals(""
				+ "     \n"
				+ " EP  \n"
				+ "     \n"
				, g2.getBoardString()
		);
			
		g2.movePlayer(Direction.LEFT);
			
		assertTrue(g2.getHasWon());
	}
		
	
}
