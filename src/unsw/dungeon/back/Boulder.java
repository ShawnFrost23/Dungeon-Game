package unsw.dungeon.back;

public class Boulder implements MoveableEntity {
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


	@Override
	public boolean willPreventEntry(MoveableEntity m, Direction d) {
		if (m instanceof Player) {
			// A player is allowed to move this boulder to the right if the
			// boulder is allowed to move to the right.
			return this.location.adjacent(d).willPreventEntry(this, d);
		} else {
			return true;	
		}
		
		
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
		// No, boulders cannot move on their own. Unfortunate name.
		return false;
	}

	@Override
	public void move(Direction d) {
		this.location.exit(this, d);
		this.location = this.location.adjacent(d);
		this.location.enter(this, d);
	}

	@Override
	public void onEnter(MoveableEntity m, Direction d) {
		// m must be a player.
		this.move(d);
		// furthermore, crush everything crushable on this.location;
	}

	@Override
	public void onExit(MoveableEntity m, Direction d) {
		throw new Error("Exited from boulder");
	}

}
