package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;

/**
 * Tests for basic game engine things that don't really fit in any user story.
 */
public class TestBasics {
	
	/**
	 * Trying to make a N x 1 dungeon failed at some point in the past. This
	 * test checks that this and related cases are correctly implemented.
	 */
	@Test
	public void testTinyDungeons() {
		Game g1 = Game.createGame(""
			+ "  P  \n"
		);
		
		assertEquals(g1.getBoardString(), ""
			+ "  P  \n"
		);
		
		Game g2 = Game.createGame(""
			+ " \n"
			+ " \n"
			+ "P\n"
			+ " \n"
		);
		
		assertEquals(g2.getBoardString(), ""
			+ " \n"
			+ " \n"
			+ "P\n"
			+ " \n"
		);
		
		Game g3 = Game.createGame(""
			+ "P\n"
		);
		
		assertEquals(g3.getBoardString(), ""
			+ "P\n"
		);
	}
	
	/**
	 * Test that a player walk around, but not into walls.
	 */
	@Test
	public void testSimplePlayerNavigation() {
		Game g = Game.createGame(""
			+ "     \n"
			+ "  P  \n"
			+ "  W  \n"
			+ "     \n"
		);
		
		g.movePlayer(Direction.DOWN);
		
		assertEquals(g.getBoardString(), ""
			+ "     \n"
			+ "  P  \n"
			+ "  W  \n"
			+ "     \n"
		);
		
		g.movePlayer(Direction.LEFT);
		
		assertEquals(g.getBoardString(), ""
			+ "     \n"
			+ " P   \n"
			+ "  W  \n"
			+ "     \n"
		);
		
		g.movePlayer(Direction.LEFT);
		
		assertEquals(g.getBoardString(), ""
			+ "     \n"
			+ "P    \n"
			+ "  W  \n"
			+ "     \n"
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(g.getBoardString(), ""
			+ "     \n"
			+ " P   \n"
			+ "  W  \n"
			+ "     \n"
		);
		
		g.movePlayer(Direction.UP);
		
		assertEquals(g.getBoardString(), ""
			+ " P   \n"
			+ "     \n"
			+ "  W  \n"
			+ "     \n"
		);
		
		g.movePlayer(Direction.DOWN);

		assertEquals(g.getBoardString(), ""
			+ "     \n"
			+ " P   \n"
			+ "  W  \n"
			+ "     \n"
		);
		
	}
}
