package unsw.dungeon.back;

import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.Subject;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.back.event.CellHitWithSwordEvent;
import unsw.dungeon.back.event.CellPushedEvent;
import unsw.dungeon.back.event.EnemyKilledEvent;

public class Enemy implements Moveable, Collidable, Observer, Subject {
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
	private List<Observer> observers;

	/**
	 * Consruct a new Enemy instance.
	 * @param cell cell in which the Enemy is located 
	 * @param movementStrategy {@link MovementStrategy} that the enemy will use
	 * to choose its moves
	 */
	public Enemy(Cell cell, MovementStrategy movementStrategy) {
		this.location = cell;
		this.movementStrategy = movementStrategy;
		this.observers = new ArrayList<Observer>();
	}
	
	/**
	 * Consult this enemy's {@link MovementStrategy} for what move the Enemy
	 * would like to make.
	 * @param world {@link WorldState} instance representing the current world state
	 * @return direction to move in or <code>null</code> if the Enemy does not
	 * wish to move 
	 */
	public Direction chooseMove(WorldState world) {
		return this.movementStrategy.chooseMove(world);
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
	 * @param movementStrategy {@link MovementStrategy} to set
	 */
	public void setMovementStrategy(NaiveMovementStrategy movementStrategy) {
		this.movementStrategy = movementStrategy;
	}
	
	/**
	 * Kill this enemy -- remove it from the map and generate an
	 * {@link unsw.dungeon.back.event.EnemyKilledEvent EnemyKilledEvent}.
	 */
	public void kill() {
		this.location.removeEntity(this);
		this.notifyAllOf(new EnemyKilledEvent(this));
	}

	@Override
	public void attachListener(Observer observer) {
		this.observers.add(observer);
	}

	@Override
	public void detachListener(Observer observer) {
		this.observers.remove(observer);
		
	}

	@Override
	public void notifyAllOf(Event event) {
		for (Observer observer : new ArrayList<Observer>(this.observers)) {
			observer.notifyOf(event);
		}
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
		} else if (event instanceof CellHitWithSwordEvent) {
			this.onHitWithSword((CellHitWithSwordEvent) event);
		}
	}

	private void onPush(CellPushedEvent event) {
		Player p = event.getWhoPushed();
		p.touchEnemy(this);
	}
	
	
	private void onHitWithSword(CellHitWithSwordEvent event) {
		this.kill();
	}
}
