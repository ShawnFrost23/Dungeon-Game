package unsw.dungeon.back;

public interface MoveableEntity extends Entity {
    public Cell getLocation(); // do these two really need to be interface?
    public void setLocation(Cell location); //

    public boolean canMove(Direction d);
    public void move(Direction d);
}
