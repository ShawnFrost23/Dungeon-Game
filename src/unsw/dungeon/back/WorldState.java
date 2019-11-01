package unsw.dungeon.back;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

/**
 * A world that {@link Enemy.MovementStrategy} can query to decide what move to
 * make.
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

    public static class Treasure implements Entity, Observer {

        private Cell location;

        public Treasure(Cell c) {

            // treasure on given cell
            this.location = c;
        }

        public void pickUp(Player player) {
            // Add to player list
            player.attachListener(this);
            // Remove from Board cell
            this.location.removeEntity(this);
        }

        @Override
        public int getZ() {
            return 0;
        }

        @Override
        public char getTexture() {
            return '_';
        }

        @Override
        public void notifyOf(Event event) {
            if (event instanceof CellEnteredEvent) {
                this.onEnter((CellEnteredEvent) event);
            }

        }
        private void onEnter(CellEnteredEvent event) {
            if (event.getWhoEntered() instanceof Player) {
                //Calls pickUp
              //  this.pickUp(event.getWhoEntered());
            }
        }

    }
}
