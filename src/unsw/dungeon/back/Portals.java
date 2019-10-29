package unsw.dungeon.back;

public class Portals implements Entity {

    private Cell location;
    private Portals pairPortal;







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
