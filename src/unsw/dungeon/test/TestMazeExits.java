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
	 * "Exits (possibly multiple) are loaded from map files and rendered in the
	 * correct tile."
	 */
	@Test
	public void AC1() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "      E \n"
			+ "        \n"
			+ "  P     \n"
			+ "        \n"
			+ " E      \n"
		);
		
		assertEquals(""
			+ "      E \n"
			+ "        \n"
			+ "  P     \n"
			+ "        \n"
			+ " E      \n"
			, g.getBoardString()
		);
	}
	
	
	
	/**
	 * "Walking over an exit when a maze-goal is specified causes the player to
	 * win the game."
	 */
	@Test
	public void AC2() {
 		Game g1 = Game.createGame(new MazeGoal(), ""
			+ "PE\n"
		);
		
		assertFalse(g1.getHasWon());
		
		g1.movePlayer(Direction.RIGHT);
		
		assertTrue(g1.getHasWon());

		Game g2 = Game.createGame(new MazeGoal(), ""
			+ "EPE\n"
		);
		
		assertFalse(g2.getHasWon());
		
		g2.movePlayer(Direction.RIGHT);
		
		assertTrue(g2.getHasWon());
		
		Game g3 = Game.createGame(new MazeGoal(), ""
			+ "EPE\n"
		);
		
		assertFalse(g3.getHasWon());
		
		g3.movePlayer(Direction.LEFT);
		
		assertTrue(g3.getHasWon());
		
 		Game g4 = Game.createGame(new ImpossibleGoal(), ""
			+ "PE\n"
		);
 		
 		g4.movePlayer(Direction.RIGHT);
 		
 		assertFalse(g4.getHasWon());
	}

	
	/**
	 * "Exits are treated like blank spaces, e.g. boulders can be pushed onto
	 * them, enemies can walk over them."
	 */
	@Test
	public void AC3() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ " PBE   E! \n"
		);
		
		g.moveEnemies();
		
		assertEquals(""
			+ " PBE   !  \n"
			, g.getBoardString()
		);
		
		g.moveEnemies();
		
		assertEquals(""
			+ " PBE  !E  \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);

		assertEquals(""
			+ "  PB  !E  \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);

		assertEquals(""
			+ "   PB !E  \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "   EPB!E  \n"
			, g.getBoardString()
		);
	}	
}
