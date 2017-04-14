package lz.renatkaitmazov.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lz.renatkaitmazov.tictactoe.model.Grid;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public final class GridTest {

    private final short length = 3;
    private final Grid grid = new Grid(length);

    @Test
    public final void testWrongValueFails() {
        final byte value = 120; // Must be within the range of [-1, +1]
        final short row = 0;
        final short column = 0;
        boolean hasFailed = false;

        try {
            grid.setAt(row, column, value);
        } catch (Exception e) {
            hasFailed = true;
        }

        assertTrue(hasFailed);
    }

    @Test
    public final void testCorrectValueDoesNotFail() {
        final byte[] values = {-1, 0, +1};
        final short row = 0;
        final short column = 0;
        boolean hasFailed = false;

        try {
            for (byte value : values) {
                grid.setAt(row, column, value);
            }
        } catch (Exception e) {
            hasFailed = true;
        }

        assertFalse(hasFailed);
    }

    @Test
    public final void testWrongIndicesFail() {
        boolean hasFailed = false;
        try {
            grid.getAt(length, length);
        } catch (Exception e) {
            hasFailed = true;
        }

        assertTrue(hasFailed);
    }

    @Test
    public final void testCorrectIndicesDoNotFail() {
        final short[] indices = new short[length];
        for (short i = 0; i < length; ++i) {
            indices[i] = i;
        }

        boolean hasFailed = false;

        try {
            for (short i = 0; i < length; ++i) {
                grid.getAt(i, i);
            }
        } catch (Exception e) {
            hasFailed = true;
        }

        assertFalse(hasFailed);
    }

    @Test
    public final void testSetAndGet() throws Exception {
        final byte expectedValue = +1;
        final short row, column;
        row = column = length - 1;

        grid.setAt(row, column, expectedValue);
        final short actualValue = grid.getAt(row, column);
        final String msg = String.format("Should be %d, but was %d", expectedValue, actualValue);
        assertEquals(msg, expectedValue, actualValue);
    }

    @Test
    public final void testReset() throws Exception {
        final byte value = -1; // It can also be +1

        for (short i = 0; i < length; ++i) {
            for (short j = 0; j < length; ++j) {
                grid.setAt(i, j, value);
            }
        }

        // Check that all entries are not equal to zero.
        for (short i = 0; i < length; ++i) {
            for (short j = 0; j < length; ++j) {
                final byte actualValue = grid.getAt(i, j);
                assertNotEquals(0, actualValue);
            }
        }

        grid.resetGame();

        for (short i = 0; i < length; ++i) {
            for (short j = 0; j < length; ++j) {
                final byte actualValue = grid.getAt(i, j);
                assertEquals(0, actualValue);
            }
        }
    }

    @Test
    public final void testFirstRowHasMatch() throws Exception {
        final short row = 0;
        final byte playerId = 1;

        grid.resetGame();
        assertFalse(grid.playerHasWon(playerId));

        for (short i = 0; i < length; ++i) {
            grid.setAt(row, i, playerId);
        }
        assertTrue(grid.playerHasWon(playerId));
    }

    @Test
    public final void testSecondRowHasMatch() throws Exception {
        final short row = 1;
        final byte playerId = -1;

        grid.resetGame();
        assertFalse(grid.playerHasWon(playerId));

        for (short i = 0; i < length; ++i) {
            grid.setAt(row, i, playerId);
        }
        assertTrue(grid.playerHasWon(playerId));
    }

    @Test
    public final void testThirdRowHasMatch() throws Exception {
        final short row = 2;
        final byte playerId = -1;

        grid.resetGame();
        assertFalse(grid.playerHasWon(playerId));

        for (short i = 0; i < length; ++i) {
            grid.setAt(row, i, playerId);
        }
        assertTrue(grid.playerHasWon(playerId));
    }

    @Test
    public final void testFirstColumnHasMatch() throws Exception {
        final short column = 0;
        final byte playerId = -1;

        grid.resetGame();
        assertFalse(grid.playerHasWon(playerId));

        for (short i = 0; i < length; ++i) {
            grid.setAt(i, column, playerId);
        }
        assertTrue(grid.playerHasWon(playerId));
    }

    @Test
    public final void testSecondColumnHasMatch() throws Exception {
        final short column = 1;
        final byte playerId = -1;

        grid.resetGame();
        assertFalse(grid.playerHasWon(playerId));

        for (short i = 0; i < length; ++i) {
            grid.setAt(i, column, playerId);
        }
        assertTrue(grid.playerHasWon(playerId));
    }

    @Test
    public final void testThirdColumnHasMatch() throws Exception {
        final short column = 2;
        final byte playerId = -1;

        grid.resetGame();
        assertFalse(grid.playerHasWon(playerId));

        for (short i = 0; i < length; ++i) {
            grid.setAt(i, column, playerId);
        }
        assertTrue(grid.playerHasWon(playerId));
    }

    @Test
    public final void testLeftToRightDiagonalHasMatch() throws Exception {
        final byte playerId = 1;

        grid.resetGame();
        assertFalse(grid.playerHasWon(playerId));

        for (short i = 0; i < length; ++i) {
            grid.setAt(i, i, playerId);
        }
        assertTrue(grid.playerHasWon(playerId));
    }

    @Test
    public final void testRightToLeftDiagonalHasMatch() throws Exception {
        final byte playerId = -1;

        grid.resetGame();
        assertFalse(grid.playerHasWon(playerId));

        for (short i = 0; i < length; ++i) {
            grid.setAt(i, (short) (2 - i), playerId);
        }
        assertTrue(grid.playerHasWon(playerId));
    }
}
