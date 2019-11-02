package unsw.dungeon.back;

import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;
import unsw.dungeon.back.event.CellPushedEvent;

public class Boulder implements Moveable, Collidable, Observer {
	private Cell location;
	
	/**
	 * Construct a new Boulder object.
	 * @param cell cell in which the Boulder is located
	 */
	public Boulder(Cell cell) {
		this.location = cell;
	}

	@Override
	public int getZ() {
		return 999;
	}

	@Override
	public char getTexture() {
		return 'B';
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
		}
	}
	
	private void onPush(CellPushedEvent event) {
		Direction d = event.getDirectionPushed();
		if (this.canMove(d)) {
			this.move(d);
		}
	}
}
