package unsw.dungeon.test;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

/**
 * Tests that the basic JSON board creation mechanism works.
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
	 * Test that goals seem to be loaded and attached properly.
	 */
	@Test
	public void TestGoalLoading() {
		Game g1 = Game.createMockGame(new ImpossibleGoal(), ""
			+ " PT  \n"
		);
		JSONObject json = Board.boardStringsToJSON(g1.getBoardString());
		JSONObject jsonGoal = new JSONObject();
		jsonGoal.put("goal", "treasure");
		json.put("goal-condition", jsonGoal);

		g1 = Game.createGame(json);
		
		assertFalse(g1.getHasWon());
		g1.movePlayer(Direction.RIGHT);
		assertTrue(g1.getHasWon());
		
		List<Direction> winningDirections = new ArrayList<Direction>();
		winningDirections.add(Direction.LEFT);
		winningDirections.add(Direction.RIGHT);
		for (Direction d : winningDirections) {
			Game g2 = Game.createMockGame(new ImpossibleGoal(), ""
				+ "_BPT  \n"
			);
			json = Board.boardStringsToJSON(g2.getBoardString());
			jsonGoal = new JSONObject();
			jsonGoal.put("goal", "OR");
			
			JSONArray jsonSubGoals = new JSONArray();
			JSONObject treasureGoal = new JSONObject();
			treasureGoal.put("goal", "treasure");
			JSONObject puzzleGoal = new JSONObject();
			puzzleGoal.put("goal", "boulders");
			jsonSubGoals.put(treasureGoal);
			jsonSubGoals.put(puzzleGoal);
			jsonGoal.put("subgoals", jsonSubGoals);
			
			json.put("goal-condition", jsonGoal);
			
			g2 = Game.createGame(json);
			
			assertFalse(g2.getHasWon());
			g2.movePlayer(d);
			assertTrue(g2.getHasWon());
		}
	}
	
}
