package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

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
		Game g1 = Game.createMockGame(new ImpossibleGoal(), ""
			+ "  P  \n"
		);
		
		assertEquals(""
			+ "  P  \n"
			, g1.getBoardString()
		);
		
		Game g2 = Game.createMockGame(new ImpossibleGoal(), ""
			+ " \n"
			+ " \n"
			+ "P\n"
			+ " \n"
		);
		
		assertEquals(""
			+ " \n"
			+ " \n"
			+ "P\n"
			+ " \n"
			, g2.getBoardString()
		);
		
		Game g3 = Game.createMockGame(new ImpossibleGoal(), ""
			+ "P\n"
		);
		
		assertEquals(""
			+ "P\n"
			, g3.getBoardString()
		);
	}
	
	/**
	 * Test that a player walk around, but not into walls.
	 */
	@Test
	public void testSimplePlayerNavigation() {
		Game g = Game.createMockGame(new ImpossibleGoal(), ""
			+ "     \n"
			+ "  P  \n"
			+ "  W  \n"
			+ "     \n"
		);
		
		g.movePlayer(Direction.DOWN);
		
		assertEquals(""
			+ "     \n"
			+ "  P  \n"
			+ "  W  \n"
			+ "     \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.LEFT);
		
		assertEquals(""
			+ "     \n"
			+ " P   \n"
			+ "  W  \n"
			+ "     \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.LEFT);
		
		assertEquals(""
			+ "     \n"
			+ "P    \n"
			+ "  W  \n"
			+ "     \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "     \n"
			+ " P   \n"
			+ "  W  \n"
			+ "     \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.UP);
		
		assertEquals(""
			+ " P   \n"
			+ "     \n"
			+ "  W  \n"
			+ "     \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.DOWN);

		assertEquals(""
			+ "     \n"
			+ " P   \n"
			+ "  W  \n"
			+ "     \n"
			, g.getBoardString()
		);
	}
	
	/**
	 * Test that boards can be properly overlayed
	 */
	@Test
	public void testOverlayBoardConstruction() {
		Game g = Game.createMockGame(new ImpossibleGoal(), ""
			+ "  W \n"
			+ "P_  \n"
			+ "    \n"
			, ""
			+ "    \n"
			+ " B  \n"
			+ "  W \n"
			, ""
			+ "    \n"
			+ "_   \n"
			+ "    \n"
		);
		
		assertEquals(""
			+ "  W \n"
			+ "PB  \n"
			+ "  W \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "  W \n"
			+ "__PB\n"
			+ "  W \n"
			, g.getBoardString()
		);
	}
}
