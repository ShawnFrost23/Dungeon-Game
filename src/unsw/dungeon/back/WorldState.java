package unsw.dungeon.back;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

/**
 * A world that {@link Enemy.MovementStrategy} can query to decide what move to
 * make.
 * 
 * <br />
* <br />
 * 
 * This is functionally just a version of {@link Board} with methods that
 * produce side-effects being hidden, and there being a coordinate access method
 * to check whether a cell is collidable or not
 * ({@link #getIsCollidable(int, int)}). The user of this class can treat the
 * world like a maze -- they know their own position, the position of the goal,
 * and what obstructions there are inbetween.
 */
public class WorldState {
	private Cell[][] worldState;
	private int height;
	private int width;
	private Cell myLocation;
	private Cell goalLocation;
	
	/**
	 * Construct a new WorldState object.
	 * @param worldState a 2d array of Cells that are in the world, of size
	 * [<b>width</b>][<b>height</b>] 
	 * @param height height of the provided <b>worldState</b> array 
	 * @param width width of the provided <b>worldState</b> array 
	 * @param myLocation location of the enemy who is planning their move
	 * @param goalLocation the location of the 'goal' (player) object the enemy
	 * is trying to reach (or avoid).
	 */
	public WorldState(Cell[][] worldState, int height, int width, Cell myLocation, Cell goalLocation) {
		this.worldState = worldState;
		this.height = height;
		this.width = width;
		this.myLocation = myLocation;
		this.goalLocation = goalLocation;
	}

	/**
	 * Get whether the cell at worldState<b>[x][y]</b> is collidable
	 * @param x x-coordinate to check
	 * @param y y-coordinate to check
	 * @return true if the cell at <b>[x][y]</b> is collidable or out of bounds
	 */
	public boolean getIsCollidable(int x, int y) {
		if (x < 0 || y < 0 || x >= this.width || y >= this.height) {
			return true;
		}
		return worldState[x][y].isCollidable();
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getMyX() {
		return this.myLocation.getX();
	}

	public int getMyY() {
		return this.myLocation.getY();
	}

	public int getGoalX() {
		return this.goalLocation.getX();
	}
	
	public int getGoalY() {
		return this.goalLocation.getY();
	}
}
