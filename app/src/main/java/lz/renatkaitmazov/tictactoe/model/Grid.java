package lz.renatkaitmazov.tictactoe.model;

import java.util.Locale;

/**
 * The underlined data structure.
 * Keeps track of users' moves.
 * This is a square grid, that is the amount
 * of length and the amount of rows are equal.
 *
 * @author Renat Kaitmazov
 */

public final class Grid implements Game {

    /** Instance variables **/

    // Represented by a one-dimensional array.
    // Set of values held by the array {-1, 0, +1}
    private final byte[] grid;

    // Amount of rows and columns where rows = columns.
    private final short length;

    /** Constructors **/

    public Grid(short length) {
        this.length = length;
        this.grid = new byte[length * length];
    }

    /** API **/

    @Override
    public final byte[] getPlayersMoves() {
        final int size = grid.length;
        final byte[] playersMoves = new byte[size];
        System.arraycopy(grid, 0, playersMoves, 0, size);
        return playersMoves;
    }

    @Override
    public final byte getAt(short row, short column) {
        checkIndices(row, column);
        final int index = (row * length) + column;
        return grid[index];
    }

    @Override
    public final boolean setAt(short row, short column, byte playerId) {
        checkIndices(row, column);
        checkValue(playerId);
        final int index = (row * length) + column;
        if (grid[index] != 0) return false;
        grid[index] = playerId;
        return true;
    }

    @Override
    public final void resetGame() {
        final int size = length * length;
        for (int i = 0; i < size; ++i) {
            grid[i] = 0;
        }
    }

    /*
    * 0|1|2
    * -----
    * 3|4|5
    * -----
    * 6|7|8
     */

    @Override
    public final boolean hasMatchInFirstRow(byte playerId) {
        return grid[0] == playerId && grid[1] == playerId && grid[2] == playerId;
    }

    @Override
    public final boolean hasMatchInSecondRow(byte playerId) {
        return grid[3] == playerId && grid[4] == playerId && grid[5] == playerId;
    }

    @Override
    public final boolean hasMatchInThirdRow(byte playerId) {
        return grid[6] == playerId && grid[7] == playerId && grid[8] == playerId;
    }

    @Override
    public final boolean hasMatchInFirstColumn(byte playerId) {
        return grid[0] == playerId && grid[3] == playerId && grid[6] == playerId;
    }

    @Override
    public final boolean hasMatchInSecondColumn(byte playerId) {
        return grid[1] == playerId && grid[4] == playerId && grid[7] == playerId;
    }

    @Override
    public final boolean hasMatchInThirdColumn(byte playerId) {
        return grid[2] == playerId && grid[5] == playerId && grid[8] == playerId;
    }

    @Override
    public final boolean hasMatchInLeftToRightDiagonal(byte playerId) {
        return grid[0] == playerId && grid[4] == playerId && grid[8] == playerId;
    }

    @Override
    public final boolean hasMatchInRightToLeftDiagonal(byte playerId) {
        return grid[2] == playerId && grid[4] == playerId && grid[6] == playerId;
    }

    /** Helper methods **/

    private void checkIndices(short row, short column) {
        if ((row < 0 && row >= length) || (column < 0 && column >= length)) {
            final Locale l = Locale.getDefault();
            final String msg = String.format(l, "Row: %d or column: %d is out of bounds", row, column);
            throw new IndexOutOfBoundsException(msg);
        }
    }

    private void checkValue(byte value) {
        if (value < -1 || value > 1) {
            final Locale l = Locale.getDefault();
            final String msg = String.format(l, "Value: %d. Values should be in [-1, +1]", value);
            throw new IllegalArgumentException(msg);
        }
    }
}
