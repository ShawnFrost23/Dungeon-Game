package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

/**
 * Tests for the Keys and Doors user story
 */
public class TestKeysAndDoors {
	/**
	 * "Keys and doors are loaded from the map file and rendered in their
	 * specified position."
	 */
	@Test
	public void AC1() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "P ~ W \n"
			+ "    # \n"
			+ "    W \n"
			, ""
			+ "      \n"
			+ "~     \n"
			+ "#     \n"
		);
 
		assertEquals(""
			+ "P ~ W \n"
			+ "~   # \n"
			+ "#   W \n"
			, g.getBoardString()
		);
	}

	/**
	 * "A player "picks up" a key by walking over it, provided they are not
	 * already holding a key, in which case it is left on the ground."
	 */
	@Test
	public void AC2() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "P~    #\n"
			, ""
			+ "  ~  # \n"
		);

		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);

		assertEquals(""
			+ "  ~P ##\n"
			, g.getBoardString()
		);
	}

	/**
	 * "Pressing "q" will swap the key the player is currently holding with the
	 * one on the ground at the players location. Pressing q will do nothing if
	 * the player does not have a key, or if they are not standing on a cell
	 * with a key."
	 */
	@Test
	public void AC3() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "P~    # \n"
			, ""
			+ "  ~  #  \n"
		);
		
		g.swapKey();
		
		assertEquals(""
			+ "P~~  ## \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "  ~P ## \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "  ~ P## \n"
			, g.getBoardString()
		);
		
		g.swapKey();
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "  ~ P## \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);
		g.swapKey();
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "  ~ P## \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "  ~  P# \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "     P# \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "     ||P\n"
			, g.getBoardString()
		);

	}
	
	/**
	 * "Keys cannot be dropped on top of other keys, or on top of swords."
	 */
	@Test
	public void AC4() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "P~ S   #\n"
			, ""
			+ "  ~   # \n"
		);
		
		g.movePlayer(Direction.RIGHT);
		g.swapKey();
		g.movePlayer(Direction.RIGHT);
		g.swapKey();
		
	}
	/**
	 * "Can Pick up keys by simply walking over them"
	 */
	@Test
	public void ACXXX() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
				+ " P  W  \n"
				+ " ~  #  \n"
				+ "    W  \n"
		);

		g.movePlayer(Direction.DOWN);
		assertEquals(g.getBoardString(),""
				+ "    W  \n"
				+ " P  #  \n"
				+ "    W  \n"
		);

		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		assertEquals(g.getBoardString(),""
				+ "    W  \n"
				+ "   P#  \n"
				+ "    W  \n"
		);

		g.movePlayer(Direction.RIGHT);
		assertEquals(g.getBoardString(),""
				+ "    W  \n"
				+ "    P  \n"
				+ "    W  \n"
		);

		g.movePlayer(Direction.RIGHT);
		assertEquals(g.getBoardString(),""
				+ "    W  \n"
				+ "    |P \n"
				+ "    W  \n"
		);

	}

	/**
	 * "Player cannot cross the door without a key"
	 */
	@Test
	public void ACXXXX() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
				+ " P  W  \n"
				+ "    #  \n"
				+ "  ~ W  \n"
		);

		g.movePlayer(Direction.DOWN);
		assertEquals(g.getBoardString(),""
				+ "    W  \n"
				+ " P  #  \n"
				+ "  ~ W  \n"
		);

		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);

		assertEquals(g.getBoardString(),""
				+ "    W  \n"
				+ "   P#  \n"
				+ "  ~ W  \n"
		);
	}
	/**
	 * "Enemies will treat closed doors as walls and will not
	 * pick up the keys"
	 */
	@Test
	public void ACXXXXXX() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
				+"P   #  ~!\n"
		);
		g.moveEnemies();
		assertEquals(g.getBoardString(),""
				+"P   #  ! \n"
		);
		g.moveEnemies();
		assertEquals(g.getBoardString(),""
				+"P   # !~ \n"
		);
		g.moveEnemies();
		g.moveEnemies();
		g.moveEnemies();
		assertEquals(g.getBoardString(),""
				+"P   #! ~ \n"
		);

	}

}
