package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

/**
 * Tests for the Composite Goal user story.
 */
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
	
	private static Goal createMazeAndEnemiesGoal() {
		CompositeGoal mazeAndEnemies = new CompositeGoal(CompositeGoal.and);
		mazeAndEnemies.addChild(new MazeGoal());
		mazeAndEnemies.addChild(new EnemiesGoal());
		return mazeAndEnemies;
	}
	
	public static Goal[] createEverythingGoals() {
		//
		//   [ & ]    <--- rootGoal1
		//  / / \ \
		//  P T E M
		//
		CompositeGoal rootGoal1 = new CompositeGoal(CompositeGoal.and);
		rootGoal1.addChild(new PuzzleGoal());
		rootGoal1.addChild(new TreasureGoal());
		rootGoal1.addChild(new EnemiesGoal());
		rootGoal1.addChild(new MazeGoal());
		
		
		CompositeGoal subGoal1 = new CompositeGoal(CompositeGoal.and);
		subGoal1.addChild(new EnemiesGoal());
		subGoal1.addChild(new TreasureGoal());
		subGoal1.addChild(new MazeGoal());
		
		CompositeGoal subGoal2 = new CompositeGoal(CompositeGoal.and);
		subGoal2.addChild(new PuzzleGoal());
		subGoal2.addChild(new TreasureGoal());
		subGoal2.addChild(subGoal1);
		
		//    [ | ]         <--- rootGoal2
		//    /   \
		//   I   [ & ]      <---- subGoal2
		//       / | \
		//      P  T [ & ]  <---- subGoal1
		//            /|\
		//           E T M
		CompositeGoal rootGoal2 = new CompositeGoal(CompositeGoal.or);
		rootGoal2.addChild(new ImpossibleGoal());
		rootGoal2.addChild(subGoal2);

		return new Goal[]{ rootGoal1, rootGoal2 };
	}
	
	/**
	 * "Composite goals are loaded as defined in the map file. The player does
	 * not "win" until the composite expression is true."
	 * 
	 * This tests very simple composite expressions.
	 */
	@Test
	public void AC1TestSimple() {
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
	}
	
	/**
	 * "Composite goals are loaded as defined in the map file. The player does
	 * not "win" until the composite expression is true."
	 * 
	 * This tests a more complicated composite expression, as well as that goals
	 * can be "desatisfied" -- FloorSwitches must remain pushed down, and the
	 * player must be standing on top of the exit to win.
	 */
    @ParameterizedTest
    @MethodSource(value = "createEverythingGoals")
	public void AC1MultiLevelled(Goal everythingGoal) {
		Game g1 = Game.createGame(everythingGoal, ""
			+ "PS!    \n"
			+ "TE B_  \n"
		);
		
		g1.movePlayer(Direction.DOWN);
		g1.movePlayer(Direction.UP);
		g1.movePlayer(Direction.RIGHT);
		
		g1.swingSword(Direction.RIGHT);
		
		g1.movePlayer(Direction.DOWN);
		g1.movePlayer(Direction.RIGHT);
		g1.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "       \n"
			+ " E PB  \n"
			, g1.getBoardString()
		);
		
		assertFalse(g1.getHasWon());
		
		g1.movePlayer(Direction.LEFT);
		g1.movePlayer(Direction.LEFT);
		
		assertTrue(g1.getHasWon());
		
		Game g2 = Game.createGame(everythingGoal, ""
			+ "PS!    \n"
			+ "TE B_ E\n"
		);
		
		g2.movePlayer(Direction.DOWN);
		g2.movePlayer(Direction.UP);
		g2.movePlayer(Direction.RIGHT);
		
		g2.swingSword(Direction.RIGHT);
		
		g2.movePlayer(Direction.DOWN);
		g2.movePlayer(Direction.RIGHT);
		g2.movePlayer(Direction.RIGHT);
		g2.movePlayer(Direction.RIGHT);
		
		assertEquals(""
			+ "       \n"
			+ " E  PBE\n"
			, g2.getBoardString()
		);
		
		g2.movePlayer(Direction.LEFT);
		g2.movePlayer(Direction.LEFT);
		g2.movePlayer(Direction.LEFT);
		
		assertEquals(""
			+ "       \n"
			+ " P  _BE\n"
			, g2.getBoardString()
		);
		
		assertFalse(g2.getHasWon());

		g2.movePlayer(Direction.UP);
		g2.movePlayer(Direction.RIGHT);
		g2.movePlayer(Direction.RIGHT);
		g2.movePlayer(Direction.RIGHT);
		g2.movePlayer(Direction.RIGHT);
		g2.movePlayer(Direction.RIGHT);
		g2.movePlayer(Direction.DOWN);
		g2.movePlayer(Direction.LEFT);

		assertEquals(""
			+ "       \n"
			+ " E  BPE\n"
			, g2.getBoardString()
		);
		
		assertFalse(g2.getHasWon());

		g2.movePlayer(Direction.RIGHT);
		
		assertTrue(g2.getHasWon());
	
		Game g3 = Game.createGame(everythingGoal, ""
			+ "S! \n"
			+ "BE \n"
			+ "_T \n"
			, ""
			+ "P  \n"
			+ "   \n"
			+ "   \n"
		);
		
		g3.swingSword(Direction.RIGHT);
		
		g3.movePlayer(Direction.DOWN);
		g3.movePlayer(Direction.RIGHT);
		g3.movePlayer(Direction.DOWN);
		
		assertFalse(g3.getHasWon());
		
		g3.movePlayer(Direction.UP);
		
		assertTrue(g3.getHasWon());
    }
	
	/**
	 * "If a player is standing on an exit when it opens, they automatically
	 * enter and win."
	 */
	@Test
	public void AC2() {
		Game g1 = Game.createGame(createMazeAndEnemiesGoal(), ""
			+ "PSE!\n"
		);
		
		g1.movePlayer(Direction.RIGHT);
		g1.movePlayer(Direction.RIGHT);

		assertFalse(g1.getHasWon());
		
		g1.swingSword(Direction.RIGHT);
		
		assertTrue(g1.getHasWon());
		
		Game g2 = Game.createGame(createMazeAndEnemiesGoal(), ""
			+ "PSEEE!\n"
		);
		
		g2.movePlayer(Direction.RIGHT);
		g2.movePlayer(Direction.RIGHT);
		g2.movePlayer(Direction.RIGHT);
		g2.movePlayer(Direction.RIGHT);

		assertFalse(g2.getHasWon());
		
		g2.swingSword(Direction.RIGHT);
		
		assertTrue(g2.getHasWon());
		
	}
}
