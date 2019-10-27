package unsw.dungeon.back;

public class NaiveEnemyMovementStrategy implements Enemy.MovementStrategy {
	@Override
	public Direction chooseMove(Enemy me, WorldState world) {
		int Dx = world.getMyX() - world.getGoalX();
		int Dy = world.getMyY() - world.getGoalY();
		
		if (Math.abs(Dx) > Math.abs(Dy)) {
			if (Dx > 0 ) {
				if (me.canMove(Direction.LEFT)) {
					return Direction.LEFT;
				}
			} else if (Dx < 0) {
				if (me.canMove(Direction.RIGHT)) {
					return Direction.RIGHT;
				}
			}
		}
		
		if (Dy > 0) {
			if (me.canMove(Direction.UP)) {
				return Direction.UP;
			}
		} else if (Dy < 0){
			if (me.canMove(Direction.DOWN)) {
				return Direction.DOWN;
			}
		}
		
		if (Dx > 0 ) {
			if (me.canMove(Direction.LEFT)) {
				return Direction.LEFT;
			}
		} else if (Dx < 0) {
			if (me.canMove(Direction.RIGHT)) {
				return Direction.RIGHT;
			}
		}
		
		return null;
	}
}
