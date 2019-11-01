package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

public class TestCompositeGoal {
	private static Goal createTreasureAndPuzzleGoal() {
		CompositeGoal treasureAndPuzzle = new CompositeGoal(CompositeGoal.and);
		treasureAndPuzzle.addChild(new TreasureGoal());
		treasureAndPuzzle.addChild(new PuzzleGoal());
		return treasureAndPuzzle;
	}

	private static Goal createTreasureOrPuzzleGoal() {
		CompositeGoal treasureAndPuzzle = new CompositeGoal(CompositeGoal.or);
		treasureAndPuzzle.addChild(new TreasureGoal());
		treasureAndPuzzle.addChild(new PuzzleGoal());
		return treasureAndPuzzle;
	}
	
	/**
	 * "Composite goals are loaded as defined in the map file. The player does
	 * not "win" until the composite expression is true."
	 */
	@Test
	public void AC1() {
		Game g1 = Game.createGame(createTreasureAndPuzzleGoal(), ""
			+ "_BPT\n"
		);
		
		assertFalse(g1.getHasWon());
		
		g1.movePlayer(Direction.RIGHT);
		
		assertFalse(g1.getHasWon());
		
		g1.movePlayer(Direction.LEFT);
		g1.movePlayer(Direction.LEFT);
		
		assertTrue(g1.getHasWon());
		
		
		Game g2 = Game.createGame(createTreasureAndPuzzleGoal(), ""
			+ "_BPT\n"
		);
		
		assertFalse(g2.getHasWon());

		g2.movePlayer(Direction.LEFT);
		
		assertFalse(g2.getHasWon());
		
		g2.movePlayer(Direction.RIGHT);
		g2.movePlayer(Direction.RIGHT);
		
		assertTrue(g2.getHasWon());
		
		
		Game g3 = Game.createGame(createTreasureOrPuzzleGoal(), ""
			+ "_BPT\n"
		);
		
		assertFalse(g3.getHasWon());
		
		g3.movePlayer(Direction.RIGHT);
		
		assertTrue(g3.getHasWon());
		
		Game g4 = Game.createGame(createTreasureOrPuzzleGoal(), ""
			+ "_BPT\n"
		);
		
		assertFalse(g4.getHasWon());
		
		g4.movePlayer(Direction.LEFT);
		
		assertTrue(g4.getHasWon());
		
		// TODO add composite of composite tests ...
	}
	
	// TODO: also test that the FloorSwitch puzzle condition is desatisfied
	// once a boulder is pressed off of a switch ... is this the behaviour we
	// want? I think that non-desatisfaction would make life much simpler.
	// 
	// It makes some sense that "once all floor-switches are pressed at once,
	// a mechanism activates. What matter when a switch is unpressed now that
	// the mechanism has already been activated?
}
