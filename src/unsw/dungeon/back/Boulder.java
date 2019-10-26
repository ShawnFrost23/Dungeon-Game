package unsw.dungeon.back;

public class Boulder implements Moveable, Pushable, Collidable {
	private Cell location;
	
	
	public Boulder() {
		
	}
	
	@Override
	public int getZ() {
		return 999;
	}

	@Override
	public char getTexture() {
		return 'B';
	}


	public void setLocation(Cell location) {
		this.location = location;
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
	public void push(Player p, Direction d) {
		if (this.canMove(d)) {
			this.move(d);
		}
	}
}
