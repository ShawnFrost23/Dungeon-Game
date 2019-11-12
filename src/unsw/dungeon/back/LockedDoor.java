package unsw.dungeon.back;

import unsw.dungeon.back.event.CellPushedEvent;

/**
 * The locked state of a {@link Door}.
 */
public class LockedDoor implements Door.State {
	private Door door;
	
	public LockedDoor(Door door) {
		this.door = door;
	}
	
	@Override
	public int getZ() {
		return 998;
	}

	@Override
	public Texture getTexture() {
		return new Texture('#', "closed_door.png");
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public void onPush(CellPushedEvent event) {
		Player p = event.getWhoPushed();
		if (p.hasKey(this.door.getID())) {
			p.consumeHeldKey();
			this.door.setState(new UnlockedDoor(this.door));
		}
	}

}
