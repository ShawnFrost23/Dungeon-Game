package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.back.*;
import unsw.dungeon.spoof.ImpossibleGoal;

/**
 * Tests for the Keys and Doors user story
 */
public class TestKeysAndDoors {
    /**
     * "Keys and doors are loaded from the map file and rendered in their correct
     * position."
     */
    @Test
    public void test01() {
        Game g = Game.createGame(new ImpossibleGoal(), ""
                + "P ~ W \n"
                + "    # \n"
                + "    W \n"
        );

        assertEquals(g.getBoardString(),""
                + "P ~ W \n"
                + "    # \n"
                + "    W \n"
        );
    }

    /**
     * "Can Pick up keys by simply walking over them"
     */
    @Test
    public void test02() {
        Game g = Game.createGame(new ImpossibleGoal(), ""
                + " P    \n"
                + "      \n"
                + "  ~   \n"
        );

        g.movePlayer(Direction.RIGHT);
        g.movePlayer(Direction.DOWN);

        assertEquals(g.getBoardString(),""
                + "      \n"
                + "  P   \n"
                + "  ~   \n"
        );

        g.movePlayer(Direction.DOWN);

        assertEquals(g.getBoardString(),""
                + "      \n"
                + "      \n"
                + "  P   \n"
        );

        g.movePlayer(Direction.RIGHT);

        assertEquals(g.getBoardString(),""
                + "      \n"
                + "      \n"
                + "   P  \n"
        );
    }

    /**
     * "Can Pick up keys by simply walking over them"
     */
    @Test
    public void test03() {
        Game g = Game.createGame(new ImpossibleGoal(), ""
                + " P  W  \n"
                + " ~  #  \n"
                + "    W  \n"
        );

        g.movePlayer(Direction.DOWN);
        assertEquals(g.getBoardString(),""
                + "    W  \n"
                + " P  #  \n"
                + "    W  \n"
        );

        g.movePlayer(Direction.RIGHT);
        g.movePlayer(Direction.RIGHT);
        assertEquals(g.getBoardString(),""
                + "    W  \n"
                + "   P#  \n"
                + "    W  \n"
        );

        g.movePlayer(Direction.RIGHT);
        assertEquals(g.getBoardString(),""
                + "    W  \n"
                + "    P  \n"
                + "    W  \n"
        );

        g.movePlayer(Direction.RIGHT);
        assertEquals(g.getBoardString(),""
                + "    W  \n"
                + "    |P \n"
                + "    W  \n"
        );

    }

    /**
     * "Player cannot cross the door without a key"
     */
    @Test
    public void test04() {
        Game g = Game.createGame(new ImpossibleGoal(), ""
                + " P  W  \n"
                + "    #  \n"
                + "  ~ W  \n"
        );

        g.movePlayer(Direction.DOWN);
        assertEquals(g.getBoardString(),""
                + "    W  \n"
                + " P  #  \n"
                + "  ~ W  \n"
        );

        g.movePlayer(Direction.RIGHT);
        g.movePlayer(Direction.RIGHT);
        g.movePlayer(Direction.RIGHT);
        g.movePlayer(Direction.RIGHT);

        assertEquals(g.getBoardString(),""
                + "    W  \n"
                + "   P#  \n"
                + "  ~ W  \n"
        );
    }
    /**
     * "Enemies will treat closed doors as walls and will not
     * pick up the keys"
     */
    @Test
    public void test05() {
        Game g = Game.createGame(new ImpossibleGoal(), ""
                +"P   #  ~!\n"
        );
        g.moveEnemies();
        assertEquals(g.getBoardString(),""
                +"P   #  ! \n"
        );
        g.moveEnemies();
        assertEquals(g.getBoardString(),""
                +"P   # !~ \n"
        );
        g.moveEnemies();
        g.moveEnemies();
        g.moveEnemies();
        assertEquals(g.getBoardString(),""
                +"P   #! ~ \n"
        );

    }

}
