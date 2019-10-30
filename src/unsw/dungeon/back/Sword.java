package unsw.dungeon.back;

public class Sword implements Entity {
	@Override
	public int getZ() {
		return 250;
	}

	@Override
	public char getTexture() {
		return 'S';
	}
}
