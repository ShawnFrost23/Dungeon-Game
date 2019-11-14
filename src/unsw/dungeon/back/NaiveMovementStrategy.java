package unsw.dungeon.back;


/**
 * A naive movement strategy whereby Enemies move in such a way to minimise the
 * L2 norm to the player, getting stuck on any obstacles inbetween.
 * 
 * They will run away from the player if the "seek" flag is set to false.
 */
public class NaiveMovementStrategy implements Enemy.MovementStrategy {

	@Override
	public Direction chooseMove(WorldState world, boolean seek) {
		int x = world.getMyX();
		int y = world.getMyY();

		int Dx = x - world.getGoalX();
		int Dy = y - world.getGoalY();
		
		if (!seek) {
			Dx = -Dx;
			Dy = -Dy;
		}

		if (Math.abs(Dx) > Math.abs(Dy)) {
			if (Dx > 0) {
				if (world.shouldVisit(x - 1, y)) {
					return Direction.LEFT;
				}
			} else if (Dx < 0) {
				if (world.shouldVisit(x + 1, y)) {
					return Direction.RIGHT;
				}
			}
		}

		if (Dy > 0) {
			if (world.shouldVisit(x, y - 1)) {
				return Direction.UP;
			}
		} else if (Dy < 0) {
			if (world.shouldVisit(x, y + 1)) {
				return Direction.DOWN;
			}
		}

		if (Dx > 0) {
			if (world.shouldVisit(x - 1, y)) {
				return Direction.LEFT;
			}
		} else if (Dx < 0) {
			if (world.shouldVisit(x + 1, y)) {
				return Direction.RIGHT;
			}
		}

		return null;
	}
	
}




