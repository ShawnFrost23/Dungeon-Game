package unsw.dungeon.back;

/**
 * If an entity listens for movement, it will be notified whenever a Moveable
 * steps into or out of the Cell that this Entity occupies.
 */
public interface ListenForMovement extends Entity {
	/**
	 * This event is triggered when a moveable entity enters the Cell which we
	 * are currently on.
	 * @param m Moveable that has entered our Cell
	 */
	public void onEnter(Moveable m);
	/**
	 * This event is triggered when a moveable entity exits the Cell which we
	 * are currently on.
	 * @param m Moveable that has exited our Cell
	 */
	public void onExit(Moveable m);
}
