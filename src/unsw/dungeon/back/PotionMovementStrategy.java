package unsw.dungeon.back;

public class PotionMovementStrategy implements Enemy.MovementStrategy{
	@Override
	public Direction chooseMove(WorldState world) {
		int x = world.getMyX();
		int y = world.getMyY();

		int Dx = -(x - world.getGoalX());
		int Dy = -(y - world.getGoalY());

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
}
