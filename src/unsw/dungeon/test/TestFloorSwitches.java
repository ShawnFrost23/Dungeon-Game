package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;

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
		Game g = Game.createGame(""
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
		Game g = Game.createGame(""
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
		fail("Test not implemented!");
	}
}
