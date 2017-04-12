package lz.renatkaitmazov.tictactoe.model;

/**
 * The underlined data structure.
 * Keeps track of users' moves.
 * This is a square grid, that is the amount
 * of length and the amount of rows are equal.
 *
 * @author Renat Kaitmazov
 */

public final class Grid {

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

    public final short length() {
        return length;
    }

    public final byte getAt(short row, short column) {
        checkIndices(row, column);
        final int index = (row * length) + column;
        return grid[index];
    }

    public final void setAt(short row, short column, byte playerId) {
        checkIndices(row, column);
        checkValue(playerId);
        final int index = (row * length) + column;
        grid[index] = playerId;
    }

    public final void reset() {
        final int size = length * length;
        for (int i = 0; i < size; ++i) {
            grid[i] = 0;
        }
    }

    public final boolean playerHasWon(byte playerId) {
        return hasMatchInRows(playerId) || hasMatchInColumns(playerId) || hasMathInDiagonals(playerId);
    }

    /** Helper methods **/

    private void checkIndices(short row, short column) {
        if ((row < 0 && row >= length) || (column < 0 && column >= length)) {
            final String msg = String.format("Row: %d or column: %d is out of bounds", row, column);
            throw new IndexOutOfBoundsException(msg);
        }
    }

    private void checkValue(byte value) {
        if (value < -1 || value > 1) {
            final String msg = String.format("Value: %d. Values should be in [-1, +1]", value);
            throw new IllegalArgumentException(msg);
        }
    }

    private boolean hasMatchInRows(byte playerId) {
        outer:
        for (int i = 0; i < length; ++i) {
            boolean hasMatch = false;
            for (int j = 1; j < length; ++j) {
                final int currentIndex = (i * length) + j;
                final int previousIndex = currentIndex - 1;
                final byte currentPlayerId = grid[currentIndex];
                final byte previousPlayerId = grid[previousIndex];
                if ((currentPlayerId != playerId) || (currentPlayerId != previousPlayerId)) {
                    // If the current entry in the grid is not equal the given player id
                    // or if there is at least one entry in the row that is different from
                    // the rest in the same row, then just skip this iteration and start
                    // checking the next row.
                    continue outer;
                } else {
                    hasMatch = true;
                }
            }
            if (hasMatch) return true;
        }
        return false;
    }

    private boolean hasMatchInColumns(byte playerId) {
        outer:
        for (int i = 0; i < length; ++i) {
            boolean hasMatch = false;
            for (int j = 1; j < length; ++j) {
                final int currentIndex = (j * length) + i;
                final int previousIndex = currentIndex - length;
                final byte currentPlayerId = grid[currentIndex];
                final byte previousPlayerId = grid[previousIndex];
                if ((currentPlayerId != playerId) || (currentPlayerId != previousPlayerId)) {
                    continue outer;
                } else {
                    hasMatch = true;
                }
            }
            if (hasMatch) return true;
        }
        return false;
    }

    private boolean hasMathInDiagonals(byte playerId) {
        boolean hasMatch = false;
        // Left to right diagonal
        final int leftStep = length + 1;
        for (int i = 1; i < length; ++i) {
            final int currentIndex = i * leftStep;
            final int previousIndex = currentIndex - leftStep;
            final byte currentPlayerId = grid[currentIndex];
            final byte previousPlayerId = grid[previousIndex];
            if ((currentPlayerId != playerId) || (currentPlayerId != previousPlayerId)) {
                hasMatch = false;
                break;
            } else {
                hasMatch = true;
            }
        }

        if (hasMatch) return true;

        // Right to left diagonal
        final int rightStep = length - 1;
        for (int i = 1; i < length; ++i) {
            final int currentIndex = (i + 1) * rightStep;
            final int previousIndex = currentIndex - rightStep;
            final byte currentPlayerId = grid[currentIndex];
            final byte previousPlayerId = grid[previousIndex];
            if ((currentPlayerId != playerId) || (currentPlayerId != previousPlayerId)) {
                hasMatch = false;
                break;
            } else {
                hasMatch = true;
            }
        }

        return hasMatch;
    }
}
