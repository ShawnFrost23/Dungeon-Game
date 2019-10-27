package unsw.dungeon.back;

/**
 * A world that 
 */
public class WorldState {
	private Cell[][] worldState;
	private int height;
	private int width;
	private Cell myLocation;
	private Cell goalLocation;
	
	public WorldState(Cell[][] worldState, int height, int width, Cell myLocation, Cell goalLocation) {
		this.worldState = worldState;
		this.height = height;
		this.width = width;
		this.myLocation = myLocation;
		this.goalLocation = goalLocation;
	}

	public boolean getIsCollidable(int x, int y) {
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
