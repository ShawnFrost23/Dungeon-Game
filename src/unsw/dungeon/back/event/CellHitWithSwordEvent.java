package unsw.dungeon.back.event;

/**
 * An event that is fired whenever a Player swings a sword in a particular
 * {@link unsw.dungeon.back.Cell Cell}.
 * Note: this event is fired in the cell that is being hit with the sword, not
 * the cell the player was in when they swung the sword.
 */
public class CellHitWithSwordEvent implements Event {

}