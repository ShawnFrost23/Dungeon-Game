package unsw.dungeon.back;

public class Wall implements Entity {
	@Override
	public int getZ() {
		return 0;
	}
	
	@Override
	public Texture getTexture() {
		return new Texture('W', "brick_brown_0.png");
	}
	
	@Override
	public boolean isCollidable() {
		return true;
	}
}