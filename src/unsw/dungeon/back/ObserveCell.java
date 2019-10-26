package unsw.dungeon.back;

/**
 * Entities that implement this interface will be notified whenever a
 * {@link CellEvent} in the cell they belong to.
 */
public interface ObserveCell extends Entity {
	/**
	 * Notify the observer that a CellEvent has been fired.
	 * @param event event that has been fired
	 */
	public void notifyOf(CellEvent event);
}
