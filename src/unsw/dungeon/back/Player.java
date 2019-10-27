package unsw.dungeon.back;

public class Player implements Moveable {
	private Cell location;
	
	public Player() {
		
	}
	
	public void setLocation(Cell location) {
		this.location = location;
	}
	
	public Cell getLocation() {
		return this.location;
	}
	
	public void push(Direction d) {
		this.location.adjacent(d).push(this, d);
	}
	
	// public void swing(Direction d){ }
	

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
	public int getZ() {
		return 998;
	}

	@Override
	public char getTexture() {
		return 'P';
	}
}
