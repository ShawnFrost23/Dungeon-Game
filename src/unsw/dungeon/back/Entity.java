package unsw.dungeon.back;

import javafx.scene.image.Image;

/**
 * Something that can be put into a Cell.
 */
public interface Entity {
	/**
	 * Get the z-index for this Entity. An Entity with a larger z-index will be
	 * drawn on top of an Entity with a smaller z-index.
	 * @return the z-index for this Entity
	 * @see {@link Cell#getTexture()}
	 */
	public int getZ();
	/**
	 * Get the texture this Entity should be displayed with.
	 * @return texture to display this Entity with
	 * @see {@link Cell#getTexture()}
	 */
	public Texture getTexture();

	/**
	 * Get whether this Entity should be collidable -- whether it should prevent
	 * the entry of {@link Moveables} into its cell.
	 * @return true if the entity is collidable
	 * @see {@link Cell#isCollidable()}
	 */
	public boolean isCollidable();
}
