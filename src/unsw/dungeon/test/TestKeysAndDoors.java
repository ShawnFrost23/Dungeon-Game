package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

/**
 * Tests for the Keys and Doors user story.
 */
public class TestKeysAndDoors {
	/**
	 * "Keys and doors are loaded from the map file and rendered in their
	 * specified position."
	 */
	@Test
	public void AC1() {
		Game g = Game.createMockGame(new ImpossibleGoal(), ""
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
		Game g = Game.createMockGame(new ImpossibleGoal(), ""
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
	 * "If a player walks into a door while holding the corresponding key, the
	 * key disappears and the door permanently enters an "open" state."
	 * 
	 * Note: the "corresponding" part isn't tested here. It is behaviour that
	 * is necessarily demonstrated in AC5.
	 */
	@Test
	public void AC3() {
		Game g = Game.createMockGame(new ImpossibleGoal(), ""
			+ "P~#      \n"
			, ""
			+ "   ~#    \n"
			, ""
			+ "     ~#  \n"
		);
		
		for (int i = 0; i < 7; ++i) {
			g.movePlayer(Direction.RIGHT);
		}
		
		assertEquals(""
			+ "  | | |P \n"
			, g.getBoardString()
		);
		
		for (int i = 0; i < 7; ++i) {
			g.movePlayer(Direction.LEFT);
		}
		
		assertEquals(""
			+ "P | | |  \n"
			, g.getBoardString()
		);
	}
	
	/**
	 * "A closed door behaves like a wall. An open door behaves like "nothing"
	 * (boulders can be pushed through it, enemies and players can walk through
	 * it)."
	 */
	@Test
	public void AC4() {
		Game g = Game.createMockGame(new ImpossibleGoal(), ""
			+ " PB#!  \n"
			+ "    ~  \n"
		);

		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ " PB#!  \n"
			+ "    ~  \n"
			, g.getBoardString()
		);
		
		g.moveEnemies();
		
		assertEquals(""
			+ " PB#!  \n"
			+ "    ~  \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.DOWN);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.UP);
		
		assertEquals(""
			+ "  B#!  \n"
			+ "   P~  \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.UP);

		assertEquals(""
			+ "  BP!  \n"
			+ "       \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.DOWN);
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.UP);
		
		assertEquals(""
			+ " PB|!  \n"
			+ "       \n"
			, g.getBoardString()
		);
		
		g.moveEnemies();
		
		assertEquals(""
			+ " PB!   \n"
			+ "       \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.DOWN);
		g.moveEnemies();
		g.movePlayer(Direction.UP);
		
		assertEquals(""
			+ " PB|   \n"
			+ "   !   \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);

		assertEquals(""
			+ "   |PB \n"
			+ "   !   \n"
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
	public void AC5() {
		Game g = Game.createMockGame(new ImpossibleGoal(), ""
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
	 * "Enemies will treat closed doors as walls and will not pick up the keys."
	 */
	@Test
	public void EnemiesDontPickUpKeys() {
		Game g = Game.createMockGame(new ImpossibleGoal(), ""
			+ "P   #  ~!\n"
		);
		g.moveEnemies();
		assertEquals(""
			+ "P   #  ! \n"
			, g.getBoardString()
		);
		g.moveEnemies();
		assertEquals(""
			+ "P   # !~ \n"
			, g.getBoardString()
		);
		g.moveEnemies();
		g.moveEnemies();
		g.moveEnemies();
		assertEquals(""
			+ "P   #! ~ \n"
			, g.getBoardString()
		);
	}
}
