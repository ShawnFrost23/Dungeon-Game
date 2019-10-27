package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.PlayerKilledEvent;

/**
 * Tests for the Enemies user story.
 * @note Many of these tests tests are rendered obsolete by the Smart Enemies
 * user story. The old behaviour should still be kept and tested with a spoof
 * DumbEnemy entity.
 */
public class TestEnemies {
	/**
	 * "Enemies are loaded from the map file and rendered in their correct
	 * position."
	 */
	@Test
	public void AC1() {
		Game g = Game.createGame(""
			+ " P  \n"
			+ "  !!\n"
			+ " !  \n"
		);
		
		assertEquals(g.getBoardString(),""
			+ " P  \n"
			+ "  !!\n"
			+ " !  \n"
		);
	}
	
	/**
	 * "Enemies move toward the player at one tile per half second (left, right,
	 * up, down)."
	 * @note The "timer" part is untested; this test should pass if the enemies
	 * move at only one tile per "tick".
	 */
	@Test
	public void AC2() {
		Game g = Game.createGame(""
			+ "!   P   ! \n"
		);
		
		g.moveEnemies();
		
		assertEquals(g.getBoardString(),""
			+ " !  P  !  \n"
		);
		
		g.moveEnemies();
		
		assertEquals(g.getBoardString(),""
			+ "  ! P !   \n"
		);
		
		g.moveEnemies();
		
		assertEquals(g.getBoardString(),""
			+ "   !P!    \n"
		);
	}
	
	/**
	 * "Enemies do not move through walls, push boulders. Every tick, they
	 * choose their move (or stay still) in a way that minimises their distance
	 * (L2) to the player's current position."
	 */
	@Test
	public void AC3() {
		Game g1 = Game.createGame(""
			+ " P        ! \n"
		);
		
		g1.moveEnemies();
		
		assertEquals(g1.getBoardString(),""
			+ " P       !  \n"
		);
		
		g1.moveEnemies();
		
		assertEquals(g1.getBoardString(),""
			+ " P      !   \n"
		);
		
		g1.moveEnemies();
		
		assertEquals(g1.getBoardString(),""
			+ " P     !    \n"
		);
		
		Game g2 = Game.createGame(""
			+ " !W  P  B! \n"
			+ "           \n"
			+ "     !     \n"
		);
		
		g2.moveEnemies();
		
		assertEquals(g2.getBoardString(), ""
			+ " !W  P  B! \n"
			+ "     !     \n"
			+ "           \n"
		);

		Game g3 = Game.createGame(""
			+ "      \n"
			+ " P    \n"
			+ "  W  !\n"
			+ "  W   \n"
			+ "  !   \n"
		);
		
		g3.moveEnemies();
		
		assertEquals(g3.getBoardString(), ""
			+ "      \n"
			+ " P    \n"
			+ "  W ! \n"
			+ "  W   \n"
			+ " !    \n"
		);
		
		g3.moveEnemies();
		
		assertEquals(g3.getBoardString(), ""
			+ "      \n"
			+ " P    \n"
			+ "  W!  \n"
			+ " !W   \n"
			+ "      \n"
		);

		g3.moveEnemies();
		
		assertEquals(g3.getBoardString(), ""
			+ "      \n"
			+ " P !  \n"
			+ " !W   \n"
			+ "  W   \n"
			+ "      \n"
		);
		
		g3.movePlayer(Direction.UP);
		g3.movePlayer(Direction.LEFT);
		
		assertEquals(g3.getBoardString(), ""
			+ "P     \n"
			+ "   !  \n"
			+ " !W   \n"
			+ "  W   \n"
			+ "      \n"
		);
		
		g3.moveEnemies();
		
		assertEquals(g3.getBoardString(), ""
			+ "P     \n"
			+ " !!   \n"
			+ "  W   \n"
			+ "  W   \n"
			+ "      \n"
		);
		
		g3.movePlayer(Direction.DOWN);
		g3.movePlayer(Direction.DOWN);
		g3.movePlayer(Direction.DOWN);
		g3.movePlayer(Direction.DOWN);
		g3.movePlayer(Direction.RIGHT);
		g3.movePlayer(Direction.RIGHT);
		
		assertEquals(g3.getBoardString(), ""
			+ "      \n"
			+ " !!   \n"
			+ "  W   \n"
			+ "  W   \n"
			+ "  P   \n"
		);
		
		g3.moveEnemies();
		
		assertEquals(g3.getBoardString(), ""
			+ "      \n"
			+ "  !   \n"
			+ " !W   \n"
			+ "  W   \n"
			+ "  P   \n"
		);
		
		g3.moveEnemies();
		
		assertEquals(g3.getBoardString(), ""
			+ "      \n"
			+ "  !   \n"
			+ "  W   \n"
			+ " !W   \n"
			+ "  P   \n"
		);
		
		g3.movePlayer(Direction.RIGHT);
		
		assertEquals(g3.getBoardString(), ""
			+ "      \n"
			+ "  !   \n"
			+ "  W   \n"
			+ " !W   \n"
			+ "   P  \n"
		);
		
		g3.moveEnemies();
		
		assertEquals(g3.getBoardString(), ""
			+ "      \n"
			+ "   !  \n"
			+ "  W   \n"
			+ "  W   \n"
			+ " ! P  \n"
		);
		
		g3.moveEnemies();
		
		assertEquals(g3.getBoardString(), ""
			+ "      \n"
			+ "      \n"
			+ "  W!  \n"
			+ "  W   \n"
			+ "  !P  \n"
		);
	}
	
	/**
	 * "Enemies will not stand on top of one another."
	 */
	@Test
	public void AC4() {
		Game g1 = Game.createGame(""
			+ "P W!! \n"
		);
		
		g1.moveEnemies();
		
		assertEquals(g1.getBoardString(), ""
			+ "P W!! \n"
		);
		
		Game g2 = Game.createGame(""
			+ "  W!  \n"
			+ " P  ! \n"
		);
		
		g2.moveEnemies();
		
		if (
			g2.getBoardString().equals(""
			+ "  W!  \n"
			+ " P !  \n"
		)) {
			// pass: the bottom enemy moved first 
		} else if (
			g2.getBoardString().equals(""
			+ "  W   \n"
			+ " P !! \n"
		)) {
			// pass: the top enemy moved first
		} else {
			fail("Enemies did not block one another.");
		}
	}
	
	private static class KillListener {
		static boolean hasBeenKilled;
	}
	
	/**
	 * "If enemies collide with the player (they walk into the player or the
	 * player walks into them), the player dies. It is sufficient here that
	 * death = the map resets."
	 */
	@Test
	public void AC5() {
		Game g1 = Game.createGame(""
			+ " P ! \n"
		);

		KillListener.hasBeenKilled = false;
		g1.getPlayer().attachListener(
			(Event event) -> {
				if (event instanceof PlayerKilledEvent) {
					KillListener.hasBeenKilled = true;
				}
			}
		);
		
		g1.moveEnemies();
		
		assertEquals(g1.getBoardString(), ""
			+ " P!  \n"
		);
		
		g1.moveEnemies();
		
		if (!KillListener.hasBeenKilled) {
			fail("Player was not killed when an enemy walked into them.");
		}
		
		Game g2 = Game.createGame(""
			+ " P ! \n"
		);
		
		KillListener.hasBeenKilled = false;
		g2.getPlayer().attachListener(
			(Event event) -> {
				if (event instanceof PlayerKilledEvent) {
					KillListener.hasBeenKilled = true;
				}
			}
		);
		
		g2.movePlayer(Direction.RIGHT);
		
		assertEquals(g2.getBoardString(), ""
			+ "  P! \n"
		);
		
		g2.movePlayer(Direction.RIGHT);
		
		if (!KillListener.hasBeenKilled) {
			fail("Player was not killed when they pushed an enemy");
		}
		
	}

	/**
	 * "Enemies do not start moving until the player presses makes their first
	 * move -- they are 'paused' until the player is ready to start."
	 * 
	 * @note Doesn't actually test for anything, this is concerned with timers.
	 */
	@Test
	public void AC6() {
		// Test passed!
	}
}
