package unsw.dungeon.back;

public class OpenDoor implements Entity {
    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public char getTexture() {
        return '|';
    }
}
