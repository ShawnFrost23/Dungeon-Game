package unsw.dungeon.back;

public class Wall implements Entity {
	public Wall() {
		
	}
	
	@Override
	public int getZ() {
		return 0;
	}
	
	@Override
	public char getTexture() {
		return 'W';
	}

	@Override
	public boolean willPreventEntry(MoveableEntity m, Direction d) {
		return true;
	}

	@Override
	public void onEnter(MoveableEntity m, Direction d) {
		throw new Error("Entered wall");
	}

	@Override
	public void onExit(MoveableEntity m, Direction d) {
		throw new Error("Exited wall");
	}
}

// Swords will have
// public void enter(Moveable m, Direction d) {
//     if m instanceof boulder
//        this.destroy();