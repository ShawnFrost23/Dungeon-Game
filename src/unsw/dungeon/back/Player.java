package unsw.dungeon.back;

public class Player implements MoveableEntity {
	private Cell location;
	
	public Player() {
		
	}
	
	// player has a pickup function ...
	
	
	@Override
	public Cell getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocation(Cell location) {
		this.location = location;
	}

	@Override
	public boolean canMove(Direction d) {
		return !this.location.adjacent(d).willPreventEntry(this, d);
	}

	@Override
	public void move(Direction d) {
		this.location.exit(this, d);
		this.location = this.location.adjacent(d);
		this.location.enter(this, d);
	}

	@Override
	public int getZ() {
		return 998;
	}

	@Override
	public char getTexture() {
		return 'P';
	}

	@Override
	public boolean willPreventEntry(MoveableEntity m, Direction d) {
		return false;
	}

	@Override
	public void onEnter(MoveableEntity m, Direction d) {
		// TODO Auto-generated method stub
		// if typeof m == enemy, die.
	}

	@Override
	public void onExit(MoveableEntity m, Direction d) {
	}
}
