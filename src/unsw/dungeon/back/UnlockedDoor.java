package unsw.dungeon.back;

import unsw.dungeon.back.event.CellPushedEvent;

/**
 * The unlocked state of a {@link Door}.
 */
public class UnlockedDoor implements Door.State {
	private Door door;
	
	public UnlockedDoor(Door door) {
		this.door = door;
	}
	
    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public Texture getTexture() {
    	return new Texture('|', "open_door.png");
    }
    
	@Override
	public boolean isCollidable() {
		return false;
	}
	@Override
	public void onPush(CellPushedEvent event) {
		// Do nothing.
	}
}
