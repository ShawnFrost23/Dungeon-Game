package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;

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
		Game g = Game.createGame(""
			+ " B  \n"
			+ "  BB\n"
			+ "    \n"
		);
		
		assertEquals(g.getBoardString(),""
			+ " B  \n"
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
		Game g = Game.createGame(""
			+ "        \n"
			+ "  B     \n"
			+ " BPB    \n"
			+ "  B     \n"
			+ "        \n"
		);
		
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(g.getBoardString(), ""
			+ "        \n"
			+ "  B     \n"
			+ "B PB    \n"
			+ "  B     \n"
			+ "        \n"
		);

		g.movePlayer(Direction.UP);
		g.movePlayer(Direction.DOWN);

		assertEquals(g.getBoardString(), ""
			+ "  B     \n"
			+ "        \n"
			+ "B PB    \n"
			+ "  B     \n"
			+ "        \n"
		);

		g.movePlayer(Direction.DOWN);
		g.movePlayer(Direction.UP);
		
		assertEquals(g.getBoardString(), ""
			+ "  B     \n"
			+ "        \n"
			+ "B PB    \n"
			+ "        \n"
			+ "  B     \n"
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(g.getBoardString(), ""
			+ "  B     \n"
			+ "        \n"
			+ "B  PB   \n"
			+ "        \n"
			+ "  B     \n"
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(g.getBoardString(), ""
			+ "  B     \n"
			+ "        \n"
			+ "B   PB  \n"
			+ "        \n"
			+ "  B     \n"
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(g.getBoardString(), ""
			+ "  B     \n"
			+ "        \n"
			+ "B    PB \n"
			+ "        \n"
			+ "  B     \n"
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
		
		assertEquals(g.getBoardString(), ""
			+ "  B     \n"
			+ "        \n"
			+ "BBP     \n"
			+ "        \n"
			+ "  B     \n"
		);
	}
	
	/**
	 * "Can only push boulders if there are no walls or other boulders in its
	 * path. Boulders otherwise cannot be walked into (behave like walls)."
	 */
	@Test
	public void AC3() {
		Game g = Game.createGame(""
			+ "B BPB W\n"
		);
		
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);

		assertEquals(g.getBoardString(), ""
			+ "BBP B W\n"
		);

		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(g.getBoardString(), ""
			+ "BB  PBW\n"
		);
		
	}
	
	
	/**
	 * "Boulders cannot be pushed into enemies. Pushing boulders on top of a
	 * pickup-item (sword, key, or invincibility potion ...) will result in
	 * that item's destruction."
	 */
	@Test
	public void AC4() {
		Game g1 = Game.createGame(""
			+ " PB! \n"
		);
		
		g1.movePlayer(Direction.RIGHT);
		assertEquals(g1.getBoardString(), ""
			+ " PB! \n"
		);
		
		fail("Boulder crushing pickups not implemented!");
	}
}
