package unsw.dungeon.back;

/**
 * Interface for and collection of methods that {@link Enemy Enemies} use to
 * decide their move.
 */
public interface EnemyMovementStrategy {
	public Direction chooseMove(WorldState world);

	/**
	 * A naive movement strategy whereby Enemies move in such a way to minimise
	 * the L2 norm to the player, getting stuck on any obstacles inbetween.
	 */
	public static EnemyMovementStrategy Naive = (
		(WorldState world) -> {
			int x = world.getMyX();
			int y = world.getMyY();

			int Dx = x - world.getGoalX();
			int Dy = y - world.getGoalY();

			if (Math.abs(Dx) > Math.abs(Dy)) {
				if (Dx > 0) {
					if (!world.getIsCollidable(x - 1, y)) {
						return Direction.LEFT;
					}
				} else if (Dx < 0) {
					if (!world.getIsCollidable(x + 1, y)) {
						return Direction.RIGHT;
					}
				}
			}

			if (Dy > 0) {
				if (!world.getIsCollidable(x, y - 1)) {
					return Direction.UP;
				}
			} else if (Dy < 0) {
				if (!world.getIsCollidable(x, y + 1)) {
					return Direction.DOWN;
				}
			}

			if (Dx > 0) {
				if (!world.getIsCollidable(x - 1, y)) {
					return Direction.LEFT;
				}
			} else if (Dx < 0) {
				if (!world.getIsCollidable(x + 1, y)) {
					return Direction.RIGHT;
				}
			}

			return null;
		}
	);
}



