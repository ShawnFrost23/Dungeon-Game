package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.Game;
import unsw.dungeon.spoof.ImpossibleGoal;


public class TestPortals {
	/**
	 * "Portals are loaded from the map file and rendered correctly."
	 */ 
	@Test
	public void AC1() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "   O \n"
			+ " OP  \n"
			+ "     \n"
			, ""
			+ "  O  \n"
			+ "     \n"
			+ " O   \n"
		);
		
		assertEquals(""
			+ "  OO \n"
			+ " OP  \n"
			+ " O   \n"
			, g.getBoardString()
		);
		
	}
	
	/**
	 * "... That is, entering a portal behaves as though the player was
	 * initially standing on the other portal tile and is moving out of
	 * it, so long as it is possible to do so. "
	 */ 
	@Test
	public void AC2() {
		fail("Test not implemented");
	}
	
	/**
	 * "Enemies will treat portals like walls and not attempt to use them."
	 */
	@Test
	public void AC3() {
		Game g1 = Game.createGame(new ImpossibleGoal(), ""
			+ "P OWO !\n"
		);
		
		g1.moveEnemies();
		
		assertEquals(""
			+ "P OWO! \n"
			, g1.getBoardString()
		);
		
		g1.moveEnemies();
		
		assertEquals(""
			+ "P OWO! \n"
			, g1.getBoardString()
		);
		
		Game g2 = Game.createGame(new ImpossibleGoal(), ""
			+ "P OW!  \n"
			+ "   W  O\n"
		);
		
		g2.moveEnemies();
		
		assertEquals(""
			+ "P OW!  \n"
			+ "   W  O\n"
			, g2.getBoardString()
		);
	}
	
	/**
	 * Portals come in pairs and can be used from either end.
	 */
	@Test
	public void AC4() {
		fail("Test not implemented");
	}
}
