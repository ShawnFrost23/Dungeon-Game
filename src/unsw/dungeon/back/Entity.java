package unsw.dungeon.back;

public interface Entity {
	public int getZ();
	public char getTexture();
	public boolean willPreventEntry(MoveableEntity m, Direction d);
	
	
	// Exit = m is exiting this from direction d. How does that change this entity?
	public void onExit(MoveableEntity m, Direction d);
	// Enter = m is entering this from direction d. How does that change this entity?
	public void onEnter(MoveableEntity m, Direction d);
}
