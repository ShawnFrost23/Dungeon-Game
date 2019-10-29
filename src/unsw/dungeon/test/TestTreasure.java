package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

public class TestTreasure {
	
	/**
	 * "If a player walks over every treasure when a puzzle-goal is
	 * specified, they win the game (win = the map is reloaded)."
	 */
	@Test
	public void AC3() {
		Game g1 = Game.createGame(new TreasureGoal(), ""
			+ " PT  \n"
		);
		
		assertFalse(g1.getHasWon());
		
		g1.movePlayer(Direction.RIGHT);
		
		assertTrue(g1.getHasWon());
		
		Game g2 = Game.createGame(new TreasureGoal(), ""
				+ " PT  \n"
				+ " TT  \n"
				+ "     \n"
			);
			
		assertFalse(g2.getHasWon());
			
		g2.movePlayer(Direction.RIGHT);
			
		assertFalse(g2.getHasWon());
			
		g2.movePlayer(Direction.DOWN);

		assertFalse(g2.getHasWon());
			
		assertEquals(""
				+ "   T \n"
				+ " TP  \n"
				+ "  T  \n"
				, g2.getBoardString()
		);
			
		g2.movePlayer(Direction.LEFT);
			
		assertTrue(g2.getHasWon());
}
