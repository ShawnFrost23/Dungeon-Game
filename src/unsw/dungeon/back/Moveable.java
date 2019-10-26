package unsw.dungeon.back;

/**
 * A type of Entity that is able to move from one Cell to another.
 */
public interface Moveable extends Entity {
	/**
	 * True if this entity is allowed to move in the given direction.
	 * @param d direction to check if movement is possible in
	 * @return true if the movement is allowed
	 */
    public boolean canMove(Direction d);
    
	/**
	 * Move this entity one step in the given direction.
	 * @param d direction to move in
	 */
    public void move(Direction d);
}
