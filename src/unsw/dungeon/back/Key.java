package unsw.dungeon.back;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

public class Key implements Observer, Entity  {
    private int ID;

    /**
     * Construct a new Key.
     * @param ID ID of the key. This key will open {@link Door}s that share
     * this value.
     */
    public Key(int ID) {
        this.ID = ID;
    }
    
    /**
     * Get this key's ID.
     * @return the key's ID
     */
    public int getID() {
        return this.ID;
    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public char getTexture() {
        return '~';
    }
    
	@Override
	public boolean isCollidable() {
		return false;
	}

    @Override
    public void notifyOf(Event event) {
        if (event instanceof CellEnteredEvent) {
            this.onEnter((CellEnteredEvent) event);
        }
    }

    private void onEnter(CellEnteredEvent event) {
        Moveable who = event.getWhoEntered();
        if (who instanceof Player) {
            Player p = (Player) who;
            if (!p.isHoldingKey()) {
            	p.take(this);
            }
        }
    }
}
