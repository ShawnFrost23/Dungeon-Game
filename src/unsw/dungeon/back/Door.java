package unsw.dungeon.back;

import unsw.dungeon.back.event.CellPushedEvent;
import unsw.dungeon.back.event.Event;
import unsw.dungeon.back.event.Observer;

public class Door implements Observer, Entity, Collidable {
    Cell location;

    public Door(Cell location) {
        this.location = location;
    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public char getTexture() {
        return '#';
    }

    @Override
    public void notifyOf(Event event) {
        if (event instanceof CellPushedEvent) {
            this.onPush((CellPushedEvent) event);
        }
    }

    private void onPush(CellPushedEvent event) {
        Direction d = event.getDirectionPushed();
        Player p = event.getWhoPushed();
        if (p.hasKey()) {
            this.location.removeEntity(this);
            this.location.addEntity(new OpenDoor());
        }
    }
}
