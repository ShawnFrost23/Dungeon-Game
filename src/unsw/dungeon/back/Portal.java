package unsw.dungeon.back;

public class Portal implements Entity {
    private Cell location;
    private Portal pairedPortal;

    /**
     * Create a new Portal at a given location.
     * @param location cell that the portal is located at
     */
    public Portal(Cell location) {
        this.location = location;
    }

    /**
     * Get the cell that is adjacent in direction <b>d</b> to this portal's
     * other end.
     * @param d direction to check adjacency in
     * @return the cell that is adjacent to this portal's other end 
     */
    public Cell getPairedAdjacent(Direction d) {
    	return this.pairedPortal.location.adjacent(d);
    }

    /**
     * Link this portal to another one.
     * @param pairedPortal portal to link to
     */
    public void setPairedPortal(Portal pairedPortal) {
        this.pairedPortal = pairedPortal;
    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public Texture getTexture() {
		return new Texture('O', "portal.png");
    }
    
	@Override
	public boolean isCollidable() {
		return true;
	}
}
