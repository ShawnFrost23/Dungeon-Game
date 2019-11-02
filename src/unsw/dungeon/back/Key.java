package unsw.dungeon.back;

import unsw.dungeon.back.event.CellEnteredEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

public class Key implements Observer, Entity  {
    private int ID;

    public Key(int ID) {
        this.ID = ID;
    }
    
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
    public void notifyOf(Event event) {
        if (event instanceof CellEnteredEvent) {
            this.onEnter((CellEnteredEvent) event);
        }
    }

    private void onEnter(CellEnteredEvent event) {
        Moveable who = event.getWhoEntered();
        if (who instanceof Player) {
            Player p = (Player) who;
            p.pickUp(this);
        }
    }
}
