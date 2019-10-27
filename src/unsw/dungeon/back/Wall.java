package unsw.dungeon.back;

public class Wall implements Collidable {
	@Override
	public int getZ() {
		return 0;
	}
	
	@Override
	public char getTexture() {
		return 'W';
	}
}