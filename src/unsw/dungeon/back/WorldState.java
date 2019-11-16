package unsw.dungeon.back;

/**
 * A world that {@link Enemy.MovementStrategy} can query to decide what move to
 * make.
 * 
 * <br />
 * <br />
 * 
 * This is functionally just a version of {@link Board} with methods that
 * produce side-effects being hidden, and with cells being directly addressable
 * by coordinates.
 */
public class WorldState {
	private Cell[][] worldState;
	private int height;
	private int width;
	private Cell myLocation;
	private Cell goalLocation;
	private int depth;
	private Direction startDirection;
	
	private WorldState() {
		
	}
	
	/**
	 * Construct a new WorldState root node.
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
	 * Get whether the cell at worldState[<b>x</b>][<b>y</b>] is worth visiting
	 * -- whether a {@link #transition(Direction, boolean)} should be generated
	 * into this cell. Viz., true if this cell should be used as a viable
	 * intermediary in path planning.
	 * @param x x-coordinate to check
	 * @param y y-coordinate to check
	 * @return true if the cell at [<b>x</b>][<b>y</b>] isn't out of bounds,
	 * and isn't collidable (or it is an enemy whose collidability is
	 * transient).
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

	/**
	 * Get the height of the game this WorldState represents.
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the width of the game this WorldState represents.
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the "my" x-coordinate -- the x-coordinate of the entity using this
	 * WorldState to plan their moves.
	 * @return the planner's x-coordinate 
	 */
	public int getMyX() {
		return this.myLocation.getX();
	}

	/**
	 * Get the "my" y-coordinate -- the x-coordinate of the entity using this
	 * WorldState to plan their moves.
	 * @return the planner's y-coordinate 
	 */
	public int getMyY() {
		return this.myLocation.getY();
	}

	/**
	 * Get the x-coordinate of this WorldState's goal.
	 * @return the x-coordinate of the goal
	 */
	public int getGoalX() {
		return this.goalLocation.getX();
	}

	/**
	 * Get the y-coordinate of this WorldState's goal.
	 * @return the y-coordinate of the goal
	 */
	public int getGoalY() {
		return this.goalLocation.getY();
	}
	
	/**
	 * Get how many times {@link #transition(Direction, boolean)} has been
	 * applied in order to reach this WorldState from a root node.
	 * @return the number of transitions applied to reach this state
	 */
	public int getDepth() {
		return this.depth;
	}
	
	/**
	 * Get the L1 distance from "my" current position to the goal.
	 * @return the L1 distance to the goal
	 */
	public int L1() {
		int Dx = this.getMyX() - this.getGoalX();
		int Dy = this.getMyY() - this.getGoalY();
		return Math.abs(Dx) + Math.abs(Dy); 
	}

	/**
	 * Get the L2 distance from "my" current position to the goal.
	 * @return the L2 distance to the goal
	 */
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
	
	/**
	 * Get the "start" direction of this branch, e.g.
	 * <p>
	 *   <code>
	 *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Root&nbsp;WorldState
	 *     <br />
	 *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;\&nbsp;&nbsp;&nbsp;&nbsp;\
	 *     <br />
	 *     &nbsp;&nbsp;&nbsp;LEFT&nbsp;&nbsp;DOWN&nbsp;&nbsp;&nbsp;UP&nbsp;&nbsp;RIGHT
	 *     <br />
	 *     &nbsp;&nbsp;&nbsp;//\\&nbsp;&nbsp;//\\&nbsp;&nbsp;//\\&nbsp;//\\
	 *     <br />
	 *     &nbsp;&nbsp;&nbsp;....&nbsp;&nbsp;....&nbsp;&nbsp;....&nbsp;....
	 *     <br />
	 *   </code>
	 * </p>
	 * All descendants of the left group will have start direction of LEFT.
	 * All descendants of the second group, DOWN, will have start direction of
	 * DOWN. Similarly for the third and fourth group.
	 * @return the start direction of this branch, or <code>null</code> if this
	 * is a root WorldState that was not generated from a transition
	 */
	public Direction getStartDirection() {
		return this.startDirection;
	}
}
