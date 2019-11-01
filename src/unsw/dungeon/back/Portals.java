package unsw.dungeon.back;

public class Portals implements Entity, Collidable {

    private Cell location;
    private Portals pairPortal;

    // Constructor for Portal class.
    public Portals(Cell location) {
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

    public Portals getPairPortal() {
        return pairPortal;
    }

    public void setPairPortal(Portals pairPortal) {
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
