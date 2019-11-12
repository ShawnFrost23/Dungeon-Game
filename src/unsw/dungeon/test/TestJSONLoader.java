package unsw.dungeon.test;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
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
		Game g;
		try {
			g = Game.createGame("dungeons/maze.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("Test raised a FileNotFoundException");
			return;
		}
		
		assertEquals(""
			+ "WWWWWWWWWWWWWWWWWWWW\n"
			+ "WPW            WWWWW\n"
			+ "W W WWWWWWWWWW     W\n"
			+ "W      W     WWWWW W\n"
			+ "WW W W  WW W W     W\n"
			+ "WW W WW  W W WWWWWWW\n"
			+ "WW W WWW W W W     W\n"
			+ "WW W WWW W WWW WWW W\n"
			+ "W  W   W W   W W W W\n"
			+ "W WW W W W   W W W W\n"
			+ "W W  W W WWW W W W W\n"
			+ "W W  W W   W W W W W\n"
			+ "W WW W WWW W W W W W\n"
			+ "W  W W   W W W W W W\n"
			+ "WW W  WWWW W W W W W\n"
			+ "WW WW    W W W   W W\n"
			+ "WW    WWW      WWWEW\n"
			+ "WWWWWWWWWWWWWWWWWWWW\n"
			, g.getBoardString()
		);
	}
	
	/**
	 * Test that goals are loaded and attached properly.
	 */
	@Test
	public void TestGoalLoading() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ " PT  \n"
		);
		JSONObject json = Board.boardStringsToJSON(g.getBoardString());
		JSONObject jsonGoal = new JSONObject();
		jsonGoal.put("goal", "treasure");
		json.put("goal-condition", jsonGoal);

		g = Game.createGame(json);
		
		assertFalse(g.getHasWon());
		g.movePlayer(Direction.RIGHT);
		assertTrue(g.getHasWon());
		
	}
	
}
