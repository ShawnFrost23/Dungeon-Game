package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

/**
 * Tests for the Boulder user story.
 */
public class TestBoulder {
	/**
	 * "Boulders are loaded from the map file and rendered in their correct
	 * position."
	 */
	@Test
	public void AC1() {
		Game g = Game.createMockGame(new ImpossibleGoal(), ""
			+ " B P\n"
			+ "  BB\n"
			+ "    \n"
		);
		
		assertEquals(g.getBoardString(),""
			+ " B P\n"
			+ "  BB\n"
			+ "    \n"
		);
	}
	
	/**
	 * "Can push boulders into adjacent squares by walking into from the
	 * opposite direction."
	 */
	@Test
	public void AC2() {
		Game g = Game.createMockGame(new ImpossibleGoal(), ""
			+ "        \n"
			+ "  B     \n"
			+ " BPB    \n"
			+ "  B     \n"
			+ "        \n"
		);
		
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "        \n"
			+ "  B     \n"
			+ "B PB    \n"
			+ "  B     \n"
			+ "        \n"
			, g.getBoardString()
		);

		g.movePlayer(Direction.UP);
		g.movePlayer(Direction.DOWN);

		assertEquals(""
			+ "  B     \n"
			+ "        \n"
			+ "B PB    \n"
			+ "  B     \n"
			+ "        \n"
			, g.getBoardString()
		);

		g.movePlayer(Direction.DOWN);
		g.movePlayer(Direction.UP);
		
		assertEquals(""
			+ "  B     \n"
			+ "        \n"
			+ "B PB    \n"
			+ "        \n"
			+ "  B     \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "  B     \n"
			+ "        \n"
			+ "B  PB   \n"
			+ "        \n"
			+ "  B     \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "  B     \n"
			+ "        \n"
			+ "B   PB  \n"
			+ "        \n"
			+ "  B     \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "  B     \n"
			+ "        \n"
			+ "B    PB \n"
			+ "        \n"
			+ "  B     \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.DOWN);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.UP);
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);
		
		assertEquals(""
			+ "  B     \n"
			+ "        \n"
			+ "BBP     \n"
			+ "        \n"
			+ "  B     \n"
			, g.getBoardString()
		);
	}
	
	/**
	 * "Can only push boulders if there are no walls or other boulders in its
	 * path. Boulders otherwise cannot be walked into (behave like walls)."
	 */
	@Test
	public void AC3() {
		Game g = Game.createMockGame(new ImpossibleGoal(), ""
			+ "B BPB W\n"
		);
		
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);

		assertEquals(""
			+ "BBP B W\n"
			, g.getBoardString()
		);

		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "BB  PBW\n"
			, g.getBoardString()
		);
		
	}
	
	
	/**
	 * "Boulders cannot be pushed into enemies. Pushing boulders on top of a
	 * pickup-item (sword, key, or invincibility potion ...) will result in
	 * that item's destruction."
	 */
	@Test
	public void AC4() {
		// Test that boulders cannot be pushed into enemies.
		Game g1 = Game.createMockGame(new ImpossibleGoal(), ""
			+ " PB!  \n"
		);
		
		g1.movePlayer(Direction.RIGHT);
		assertEquals(""
			+ " PB!  \n"
			, g1.getBoardString()
		);

		// First check that we don't pickup the spoof items.
		Game g2 = Game.createMockGame(new ImpossibleGoal(), ""
			+ "?PB?  \n"
		);
		g2.movePlayer(Direction.LEFT);
		g2.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "?PB?  \n"
			, g2.getBoardString()
		);
		
		// Then check that they disappear once a boulder gets pushed over them.
		g2.movePlayer(Direction.RIGHT);
		g2.movePlayer(Direction.RIGHT);
		g2.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "?   PB\n"
			, g2.getBoardString()
		);		
	}
}
