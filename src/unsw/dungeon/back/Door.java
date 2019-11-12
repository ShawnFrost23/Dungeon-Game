package unsw.dungeon.back;

import unsw.dungeon.back.event.CellPushedEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

public class Door implements Observer, Entity {
	public interface State {
		public int getZ();
		public char getTexture();
		public boolean isCollidable();
		public void onPush(CellPushedEvent event);
	}

	private State state;
	private int ID;

	/**
	 * Construct a new Door.
	 * @param ID ID of the door. This door will open be opened by {@link Keys}s
	 * that share this value.
	 */
	public Door(int ID) {
		this.ID = ID;
		this.state = new LockedDoor(this);
	}
	

	/**
	 * Get this door's ID.
	 * @return the door's ID
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * Open the door -- replace the Door entity with an OpenDoor one at the same
	 * location.
	 */
	public void setState(State state) {
		this.state = state;
	}

	@Override
	public int getZ() {
		return this.state.getZ();
	}

	@Override
	public char getTexture() {
		return this.state.getTexture();
	}
	
	@Override
	public boolean isCollidable() {
		return this.state.isCollidable();
	}

	@Override
	public void notifyOf(Event event) {
		if (event instanceof CellPushedEvent) {
			this.onPush((CellPushedEvent) event);
		}
	}

	private void onPush(CellPushedEvent event) {
		this.state.onPush(event);
	}
}
