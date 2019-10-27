package unsw.dungeon.back;

// TODO: major refactoring here and in Cell. Will do soon. Try to implement
// heuristic and a spoofBoard to simulate moves on.

public class Enemy implements Moveable, Collidable, ObserveCell {
	public interface MovementStrategy {
		public Direction chooseMove(Enemy e, Player p);
	}
	
	public static MovementStrategy Naive = (
		(Enemy e, Player p) -> {
			int Dx = e.getLocation().getX() - p.getLocation().getX();
			int Dy = e.getLocation().getY() - p.getLocation().getY();

			
			if (Math.abs(Dx) > Math.abs(Dy)) {
				if (Dx > 0 ) {
					if (e.canMove(Direction.LEFT)) {
						return Direction.LEFT;
					}
				} else if (Dx < 0) {
					if (e.canMove(Direction.RIGHT)) {
						return Direction.RIGHT;
					}
				}
			}
			
			if (Dy > 0) {
				if (e.canMove(Direction.UP)) {
					return Direction.UP;
				}
			} else if (Dy < 0){
				if (e.canMove(Direction.DOWN)) {
					return Direction.DOWN;
				}
			}
			
			if (Dx > 0 ) {
				if (e.canMove(Direction.LEFT)) {
					return Direction.LEFT;
				}
			} else if (Dx < 0) {
				if (e.canMove(Direction.RIGHT)) {
					return Direction.RIGHT;
				}
			}
			
			return null;
		}
	);
	
	private Cell location;
	private MovementStrategy movementStrategy;
	
	public Direction chooseMove(Player p) {
		return this.movementStrategy.chooseMove(this, p);
	}
	
	public void setLocation(Cell location) {
		this.location = location;
	}
	
	public Cell getLocation() {
		return this.location;
	}
	
	public void setMovementStrategy(MovementStrategy movementStrategy) {
		this.movementStrategy = movementStrategy;
	}
	
	public void onPush(CellPushedEvent event) {
		Player p = event.getWhoPushed();
		p.touchEnemy();
	}
	
	@Override
	public int getZ() {
		return 999;
	}

	@Override
	public char getTexture() {
		return '!';
	}

	@Override
	public boolean canMove(Direction d) {
		return !this.location.adjacent(d).isCollidable();
	}

	@Override
	public void move(Direction d) {
		this.location.exit(this);
		this.location = this.location.adjacent(d);
		this.location.enter(this);
	}

	@Override
	public void notifyOf(CellEvent event) {
		if (event instanceof CellPushedEvent) {
			this.onPush((CellPushedEvent) event);
		}
	}
}
