package unsw.dungeon.back;

import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.CellPushedEvent;

public class Enemy implements Moveable, Collidable, Observer {
	/** 
	 * Classes that implement this interface can be used to decide which move
	 * an {@link Enemy} will make.
	 * @see {@link Enemy#chooseMove(WorldState)}
	 */
	public interface MovementStrategy {
		/**
		 * Decide which move the enemy would like to make.
		 * @param world
		 * @return direction to move in or <code>null</code> if the Enemy does
		 * not wish to move
		 * @see {@link Enemy#chooseMove(WorldState)}
		 */
		public Direction chooseMove(WorldState world);
	}
	
	private Cell location;
	private MovementStrategy movementStrategy;

	/**
	 * Consult this enemy's {@link MovementStrategy} for what move the Enemy
	 * would like to make.
	 * @param world WorldState instance representing the current world state
	 * @return direction to move in or <code>null</code> if the Enemy does not
	 * wish to move 
	 */
	public Direction chooseMove(WorldState world) {
		return this.movementStrategy.chooseMove(world);
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
	public void setMovementStrategy(NaiveMovementStrategy movementStrategy) {
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
