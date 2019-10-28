package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

/**
 * Tests for the Floor Switches user story.
 */
public class TestFloorSwitches {
	/**
	 * "Floor switches are loaded in from the map file and rendered in their
	 * correct position."
	 */
	@Test
	public void AC1() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "      _ \n"
			+ "    _   \n"
			+ "  P     \n"
			+ "    _   \n"
			+ " _      \n"
		);
		
		assertEquals(""
			+ "      _ \n"
			+ "    _   \n"
			+ "  P     \n"
			+ "    _   \n"
			+ " _      \n"
			, g.getBoardString()
		);
	}
	
	/**
	 * "Floor switches can be walked over."
	 */
	@Test
	public void AC2() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "  P_ \n"
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "   P \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "   _P\n"
			, g.getBoardString()
		);
	}
	
	/**
	 * "If a player pushes boulders over every switch when a puzzle-goal is
	 * specified, they win the game (win = the map is reloaded)."
	 */
	@Test
	public void AC3() {
		Game g1 = Game.createGame(new PuzzleGoal(), ""
			+ " PB_ \n"
		);
		
		
		assertFalse(g1.getHasWon());
		
		g1.movePlayer(Direction.RIGHT);
		
		assertTrue(g1.getHasWon());
		
		Game g2 = Game.createGame(new PuzzleGoal(), ""
			+ " PB_ \n"
			+ "_BB  \n"
			+ "  _  \n"
		);
		
		assertFalse(g2.getHasWon());
		
		g2.movePlayer(Direction.RIGHT);
		
		assertFalse(g2.getHasWon());
		
		g2.movePlayer(Direction.DOWN);

		assertFalse(g2.getHasWon());
		
		assertEquals(""
			+ "   B \n"
			+ "_BP  \n"
			+ "  B  \n"
			, g2.getBoardString()
		);
		
		g2.movePlayer(Direction.LEFT);
		
		assertTrue(g2.getHasWon());

		Game g3 = Game.createGame(new PuzzleGoal(), ""
			+ "   PB_    \n"
			+ "        B_\n"
		);
		
		g3.movePlayer(Direction.RIGHT);
		g3.movePlayer(Direction.RIGHT);
		g3.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "     _PB  \n"
			+ "        B_\n"
			, g3.getBoardString()
		);
		
		g3.movePlayer(Direction.DOWN);
		g3.movePlayer(Direction.RIGHT);
		g3.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "     _ B  \n"
			+ "        PB\n"
			, g3.getBoardString()
		);
		
		assertFalse(g3.getHasWon());
		
		g3.movePlayer(Direction.UP);
		g3.movePlayer(Direction.LEFT);

		
		assertEquals(""
			+ "     _BP  \n"
			+ "         B\n"
			
			, g3.getBoardString()
		);
		
		assertFalse(g3.getHasWon());
		
		g3.movePlayer(Direction.LEFT);
		
		assertTrue(g3.getHasWon());
		
	}
	
	/**
	 * "Boulders that start on top of floor switches will cause the switch to
	 * start in a pressed state."
	 */
	@Test
	public void AC4() {
		Game g1 = Game.createGame(new PuzzleGoal(), ""
			+ "  P_ \n"
			, ""
			+ "   B \n"
		);
		
		assertTrue(g1.getHasWon());
		
		Game g2 = Game.createGame(new PuzzleGoal(), ""
			+ "_BP_ \n"
			, ""
			+ "   B \n"
		);
		
		assertFalse(g2.getHasWon());
		
		g2.movePlayer(Direction.LEFT);
		
		assertTrue(g2.getHasWon());
	}
	
	/**
	 * "Pushing a boulder directly from one floor switch to another will not
	 * cause both to be temporarily pressed at once."
	 */
	@Test
	public void AC5() {
		Game g = Game.createGame(new PuzzleGoal(), ""
			+ "PB__\n"
		);
		
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "  PB\n"
			, g.getBoardString()
		);
		
		assertFalse(g.getHasWon());
	}	
}
