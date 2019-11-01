package unsw.dungeon.back;

public class Portal implements Entity, Collidable {

    private Cell location;
    private Portal pairPortal;

    // Constructor for Portal class.
    public Portal(Cell location) {
        this.location = location;

    }

    // Getters and Setters
    //===============================================
    public Cell getLocation() {
        return location;
    }

    public void setLocation(Cell location) {
        this.location = location;
    }

    public Portal getPairPortal() {
        return pairPortal;
    }

    public void setPairPortal(Portal pairPortal) {
        this.pairPortal = pairPortal;
    }
    //===============================================


    //Over ridden funtions of entity Interface.
    //===============================================
    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public char getTexture() {
        return 'O';
    }
    //===============================================
}
