package unsw.dungeon.back;

public class Wall implements Entity {
	@Override
	public int getZ() {
		return 0;
	}
	
	@Override
	public char getTexture() {
		return 'W';
	}
	
	@Override
	public boolean isCollidable() {
		return true;
	}
}