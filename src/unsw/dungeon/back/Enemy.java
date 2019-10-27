package unsw.dungeon.back;

import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.CellPushedEvent;

// TODO: major refactoring here and in Cell. Will do soon. Try to implement
// heuristic and a spoofBoard to simulate moves on.

public class Enemy implements Moveable, Collidable, Observer {
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

	// TODO: take in EffectiveWorldState instead (with no side effects).
	/**
	 * Consult this enemy's {@link MovementStrategy} for what move the Enemy
	 * would like to make.
	 * @param p the Player that this Enemy is targeting
	 * @return direction to move in or <code>null</code> if the Enemy does not
	 * wish to move 
	 */
	public Direction chooseMove(Player p) {
		return this.movementStrategy.chooseMove(this, p);
	}
	
	/**
	 * Set the location of this Enemy.
	 * @param location location to set
	 */
	public void setLocation(Cell location) {
		this.location = location;
	}
	
	/**
	 * Get the location of this enemy.
	 * @return this enemy's location
	 */
	public Cell getLocation() {
		return this.location;
	}
	
	/**
	 * Set the strategy that this Enemy will use to choose its moves.
	 * @param movementStrategy strategy to use
	 */
	public void setMovementStrategy(MovementStrategy movementStrategy) {
		this.movementStrategy = movementStrategy;
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
	public void notifyOf(Event event) {
		if (event instanceof CellPushedEvent) {
			this.onPush((CellPushedEvent) event);
		}
	}
	
	private void onPush(CellPushedEvent event) {
		Player p = event.getWhoPushed();
		p.touchEnemy(this);
	}
}
