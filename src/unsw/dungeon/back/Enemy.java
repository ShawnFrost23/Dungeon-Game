package unsw.dungeon.back;

public class Enemy implements MoveableEntity {
	private Cell location;
	
	@Override
	public int getZ() {
		return 999;
	}

	@Override
	public char getTexture() {
		return '!';
	}

	@Override
	public boolean willPreventEntry(MoveableEntity m, Direction d) {
		if (m instanceof Enemy) {
			return true; // enemies cannot stack
		} else if (m instanceof Boulder) {
			return true; // boulders cannot be pushed into enemies
		}
		return false;
	}

	@Override
	public Cell getLocation() {
		return this.location;
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
	public void onEnter(MoveableEntity m, Direction d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onExit(MoveableEntity m, Direction d) {
		// TODO Auto-generated method stub
		
	}

}
