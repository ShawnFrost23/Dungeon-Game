package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

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
 * ({@link #shouldVisit(int, int)}). The user of this class can treat the
 * world like a maze -- they know their own position, the position of the goal,
 * and what obstructions there are inbetween.
 */
public class WorldState {
	private Cell[][] worldState;
	private int height;
	private int width;
	private Cell myLocation;
	private Cell goalLocation;
	
	private int depth;
	/**
	 * Which direction the enemy started exploring this WorldState in.
	 */
	private Direction startDirection;
	
	private WorldState() {
		
	}
	
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
		this.depth = 0;
		this.startDirection = null;
	}
	
	/**
	 * Get whether the cell at worldState<b>[x][y]</b> is worth visiting.
	 * @param x x-coordinate to check
	 * @param y y-coordinate to check
	 * @return true if the cell at <b>[x][y]</b> isn't out of bounds, and isn't
	 * collidable (or it is an enemy whose collidability is transient).
	 */
	public boolean shouldVisit(int x, int y) {
		if (x < 0 || y < 0 || x >= this.width || y >= this.height) {
			return false;
		}
		if (worldState[x][y].hasEnemy()) {
			return true;
		}
		return !worldState[x][y].isCollidable();
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
	
	public int getDepth() {
		return this.depth;
	}
	
	public int L1() {
		int Dx = this.getMyX() - this.getGoalX();
		int Dy = this.getMyY() - this.getGoalY();
		return Math.abs(Dx) + Math.abs(Dy); 
	}
	
	public double L2() {
		int Dx = this.getMyX() - this.getGoalX();
		int Dy = this.getMyY() - this.getGoalY();
		return Math.sqrt(Math.pow(Dx, 2) + Math.pow(Dy, 2)); 
	}
	
	/**
	 * Generate a new world-state as though the enemy has moved once in
	 * direction d.
	 * @param d direction to move enemy in
	 * @param canUsePortals can the enemy use portals
	 * @return new world-state representing the change in enemy position, or
	 * <code>null</code> if the move is impossible
	 */
	public WorldState transition(Direction d, boolean canUsePortals) {
		int myNewX;
		int myNewY;
		if (canUsePortals) {
			myNewX = this.myLocation.adjacent(d).getX();
			myNewY = this.myLocation.adjacent(d).getY();
		} else {
			myNewX = this.getMyX();
			myNewY = this.getMyY();
			if (d == Direction.LEFT) {
				myNewX -= 1;
			} else if (d == Direction.RIGHT) {
				myNewX += 1;
			} else if (d == Direction.UP) {
				myNewY -= 1;
			} else if (d == Direction.DOWN) {
				myNewY += 1;
			}
		}
		if (!this.shouldVisit(myNewX, myNewY)) {
			return null;
		}
		
		WorldState state = new WorldState();
		state.worldState = this.worldState;
		state.height = this.height;
		state.width = this.width;
		state.myLocation = this.myLocation.adjacent(d);
		state.goalLocation = this.goalLocation;
		state.depth = this.depth + 1;
		state.startDirection = this.startDirection != null ? this.startDirection : d;
		return state;
	}
	public Direction getStartDirection() {
		return this.startDirection;
	}
}
