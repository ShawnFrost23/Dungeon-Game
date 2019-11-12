package unsw.dungeon.test;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

/**
 * Tests that the JSON board creation mechanism works.
 */
public class TestJSONLoader {
	
	/**
	 * Test that walls and the player are loaded in the correct positions, and
	 * that the player is properly "tracked" and moveable.
	 */
	@Test
	public void TestSimpleWalledDungeon() {
		Game g = Game.createGame("/dungeons/maze.json");
		
		assertEquals(""
			+ "WWWWWWWWWWWWWWWWWWWW"
			+ "WPW            WWWWW"
			+ "W W WWWWWWWWWW     W"
			+ "W      W     WWWWW W"
			+ "WW W W  WW W W     W"
			+ "WW W WW  W W WWWWWWW"
			+ "WW W WWW W W W     W"
			+ "WW W WWW W WWW WWW W"
			+ "W  W   W W   W W W W"
			+ "W WW W W W   W W W W"
			+ "W W  W W WWW W W W W"
			+ "W W  W W   W W W W W"
			+ "W WW W WWW W W W W W"
			+ "W  W W   W W W W W W"
			+ "WW W  WWWW W W W W W"
			+ "WW WW    W W W   W W"
			+ "WW    WWW      WWWEW"
			+ "WWWWWWWWWWWWWWWWWWWW"
			, g.getBoardString()
		);
	}
	
	/**
	 * Test that goals are loaded and attached properly.
	 */
	@Test
	public void TestGoalLoading() {
		fail("Test not implemented");
	}
	
}
