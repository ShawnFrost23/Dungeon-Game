package unsw.dungeon.back;

import unsw.dungeon.back.event.CellEvent;
import unsw.dungeon.back.event.CellPushedEvent;

public class Boulder implements Moveable, Collidable, ObserveCell {
	private Cell location;
	
	public Boulder() {
		
	}
	
	public void setLocation(Cell location) {
		this.location = location;
	}
	
	private void onPush(CellPushedEvent event) {
		Direction d = event.getDirectionPushed();
		if (this.canMove(d)) {
			this.move(d);
		}
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
	public void notifyOf(CellEvent event) {
		if (event instanceof CellPushedEvent) {
			this.onPush((CellPushedEvent) event);
		}
	}
}
