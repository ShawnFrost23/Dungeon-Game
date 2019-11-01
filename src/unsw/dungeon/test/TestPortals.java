package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.Direction;
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
	 * "Entering a portal behaves as though the player was initially standing
	 * on the other portal tile and is moving out of it, so long as it is
	 * possible to do so ..."
	 */ 
	@Test
	public void AC2() {
		Game g1 = Game.createGame(new ImpossibleGoal(), ""
			+ "PO O \n"
		);

		g1.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ " O OP\n"
			, g1.getBoardString()
		);

		Game g2 = Game.createGame(new ImpossibleGoal(), ""
			+ "PO OW\n"
		);
		
		g2.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "PO OW\n"
			, g2.getBoardString()
		);
		
		Game g3 = Game.createGame(new ImpossibleGoal(), ""
			+ "PBO O  \n"
		);
		
		g3.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ " PO OB \n"
			, g3.getBoardString()
		);
		
		g3.movePlayer(Direction.RIGHT);

		assertEquals(""
			+ "  O OPB\n"
			, g3.getBoardString()
		);
		
		Game g4 = Game.createGame(new ImpossibleGoal(), ""
			+ "PO O!\n"
		);
		
		assertFalse(g4.getHasLost());
		
		g4.movePlayer(Direction.RIGHT);
		
		assertTrue(g4.getHasLost());
		
		Game g5 = Game.createGame(new ImpossibleGoal(), ""
			+ "SO O!\n"
			, ""
			+ "P    \n"
		);
		
		g5.swingSword(Direction.RIGHT);
		
		assertEquals(""
			+ "PO O \n"
			, g5.getBoardString()
		);

		Game g6 = Game.createGame(new ImpossibleGoal(), ""
			+ "~PO O# \n"
		);
		
		g6.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "~PO O# \n"
			, g6.getBoardString()
		);
		
		g6.movePlayer(Direction.LEFT);
		g6.movePlayer(Direction.RIGHT);
		g6.movePlayer(Direction.RIGHT);
		g6.movePlayer(Direction.RIGHT);

		assertEquals(""
			+ "  O O|P\n"
			, g6.getBoardString()
		);
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
	 * "Portals come in pairs and can be used from either end."
	 * 
	 * Furthermore, test verifies that portals can be chained.
	 */
	@Test
	public void AC4() {
		Game g = Game.createGame(new ImpossibleGoal(), ""
			+ "P            \n"
			+ " O         O \n"
			+ "             \n"
			+ "             \n"
			+ "             \n"
			, ""
			+ "             \n"
			+ "        O    \n"
			+ " O           \n"
			+ "             \n"
			+ "             \n"
			, ""
			+ "             \n"
			+ "             \n"
			+ "             \n"
			+ "       OO    \n"
			+ "             \n"
		);
		
		assertEquals(""
			+ "P            \n"
			+ " O      O  O \n"
			+ " O           \n"
			+ "       OO    \n"
			+ "             \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.RIGHT);
		g.movePlayer(Direction.DOWN);

		assertEquals(""
			+ "             \n"
			+ " O      O  O \n"
			+ " O         P \n"
			+ "       OO    \n"
			+ "             \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.LEFT);
		g.movePlayer(Direction.DOWN);

		assertEquals(""
			+ "             \n"
			+ " O      O  O \n"
			+ " O           \n"
			+ "       OO    \n"
			+ "       P     \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.UP);
		
		assertEquals(""
			+ "             \n"
			+ " O      O  O \n"
			+ " O      P    \n"
			+ "       OO    \n"
			+ "             \n"
			, g.getBoardString()
		);
		
		g.movePlayer(Direction.UP);

		assertEquals(""
			+ "           P \n"
			+ " O      O  O \n"
			+ " O           \n"
			+ "       OO    \n"
			+ "             \n"
			, g.getBoardString()
		);
	}
}
