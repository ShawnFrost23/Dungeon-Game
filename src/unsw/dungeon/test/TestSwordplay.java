package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

public class TestSwordplay {
	/**
	 * "Sword items are loaded from map and rendered in correct position."
	 */ 
	@Test
	public void AC1() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ " P S\n"
			+ "   S\n"
			+ " S  \n"
		);
		
		assertEquals(""
			+ " P S\n"
			+ "   S\n"
			+ " S  \n"
			, g.getBoardString()
		);
		
	}
	
	/**
	 * "A player can pick up a sword by walking over it (if they are not holding
	 * one already), and their player avatar changes to indicate they are
	 * holding sword."
	 * 
	 * "Player avatar changes" isn't tested.
	 */
	@Test
	public void AC2() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ " PS \n"
		);
		
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "   P\n"
			, g.getBoardString()
		);
	}
	
	/**
	 * "If a player is already holding a sword, walking over it will do nothing
	 * -- when the player walks off of the tile, the original sword will still
	 * be there." 
	 */
	@Test
	public void AC3() {
		Game g1 = Game.createGame(new ImpossibleGoal(), ""
			+ " P S S \n"
		);
		
		g1.movePlayer(Direction.RIGHT);
		g1.movePlayer(Direction.RIGHT);
		g1.movePlayer(Direction.RIGHT);
		g1.movePlayer(Direction.RIGHT);
		g1.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "     SP\n"
			, g1.getBoardString()
		);
		
		Game g2 = Game.createGame(new ImpossibleGoal(), ""
			+ " S \n"
			, ""
			+ " P \n"
		);
		
		g2.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "  P\n"
			, g2.getBoardString()
		);
	}
	
	/**
	 * "If a player has a sword, they can press one of "w, a, s, d" to attack
	 * the tile immediately to their left, bottom, right, top."
	 * 
	 * This test doesn't really test anything ...
	 */
	@Test
	public void AC4() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "   \n"
			+ " S \n"
			+ "   \n"
			, ""
			+ "   \n"
			+ " P \n"
			+ "   \n"
		);
		g.swingSword(Direction.UP);
		g.swingSword(Direction.DOWN);
		g.swingSword(Direction.LEFT);
		g.swingSword(Direction.RIGHT);
	}
	
	/**
	 * "If there is an enemy in the tile that is attacked, it is killed
	 * (disappears)."
	 */
	@Test
	public void AC5() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "    \n"
			+ "!!S \n"
			+ " !  \n"
			, ""
			+ "    \n"
			+ "  P \n"
			+ "    \n"
		);
		
		g.swingSword(Direction.LEFT);
		
		assertEquals(""
			+ "    \n"
			+ "! P \n"
			+ " !  \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.LEFT);
		
		assertFalse(g.getHasLost());
		
		g.swingSword(Direction.LEFT);
		
		assertEquals(""
			+ "    \n"
			+ " P  \n"
			+ " !  \n"
			, g.getBoardString()
		);		
		
		g.swingSword(Direction.DOWN);
		
		assertEquals(""
			+ "    \n"
			+ " P  \n"
			+ "    \n"
			, g.getBoardString()
		);
		
		g.moveEnemies();
		
		assertFalse(g.getHasLost());
		
	}
	 
	/**
	 * "Every swing of the sword, regardless of whether it hits, causes the
	 * sword to lose one durability. A sword disappears when it loses all of
	 * its durability. Each sword begins with 5 durability."
	 */
	@Test
	public void AC6() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ " ! \n"
			+ "SS!\n"
			+ " W \n"
			, ""
			+ "   \n"
			+ " P \n"
			+ "   \n"
		);
		
		g.swingSword(Direction.LEFT);
		g.swingSword(Direction.LEFT);
		g.swingSword(Direction.DOWN);
		g.swingSword(Direction.DOWN);
		g.swingSword(Direction.RIGHT);
	
		assertEquals(""
			+ " ! \n"
			+ "SP \n"
			+ " W \n"
			, g.getBoardString()
		);
		
		g.swingSword(Direction.UP);
		
		assertEquals(""
			+ " ! \n"
			+ "SP \n"
			+ " W \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.RIGHT);
		g.swingSword(Direction.UP);
		
		assertEquals(""
			+ "   \n"
			+ " P \n"
			+ " W \n"
			, g.getBoardString()
		);
	}

	/**
	 * "If the player is standing on top of a sword when their held sword
	 * breaks, they automatically pick it up."
	 */
	@Test
	public void AC7() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ " !!!SS!\n"
			+ "    !! \n"
			, ""
			+ "    P  \n"
			+ "       \n"
		);
		
		
		for (int i = 0; i < 4; ++i) {
			g.swingSword(Direction.DOWN);
		}

		assertEquals(""
			+ " !!!PS!\n"
			+ "     ! \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ " !!! P!\n"
			+ "     ! \n"
			, g.getBoardString()
		);
		
		g.swingSword(Direction.DOWN);
		g.swingSword(Direction.RIGHT);

		assertEquals(""
			+ " !!! P \n"
			+ "       \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.LEFT);
		g.swingSword(Direction.LEFT);
		
		assertEquals(""
			+ " !! P  \n"
			+ "       \n"
			, g.getBoardString()
		);

		g.swingSword(Direction.LEFT);
		g.swingSword(Direction.LEFT);
		
		g.movePlayer(Direction.LEFT);
		g.swingSword(Direction.LEFT);
		
		assertEquals(""
			+ " !!P   \n"
			+ "       \n"
			, g.getBoardString()
		);
		
	}

	/**
	 * If all enemies have been defeated when an enemy-destruction goal is
	 * specified, the player wins the game.
	 */
	@Test
	public void AC8() {
		Game g = Game.createGame(new EnemiesGoal(), ""
			+ "   !P !\n"
			, ""
			+ "     S \n"
		);
		
		assertFalse(g.getHasWon());
		
		g.swingSword(Direction.LEFT);
		
		assertFalse(g.getHasWon());
		
		g.movePlayer(Direction.RIGHT);
		g.swingSword(Direction.RIGHT);
		
		assertFalse(g.getHasWon());
		
		g.movePlayer(Direction.LEFT);
		
		assertEquals(""
			+ "   !P  \n"
			, g.getBoardString()
		);
		
		assertFalse(g.getHasWon());
		
		g.swingSword(Direction.LEFT);
		
		assertTrue(g.getHasWon());
	}
}
