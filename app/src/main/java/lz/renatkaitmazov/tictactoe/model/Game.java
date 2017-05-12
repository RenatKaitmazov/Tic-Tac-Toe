package lz.renatkaitmazov.tictactoe.model;

/**
 * @author Renat Kaitmazov
 */

public interface Game {
    byte getAt(short row, short column);
    boolean setAt(short row, short column, byte value);
    void resetGame();
    byte[] getPlayersMoves();

    // Check to see if we a winner in any row or column or diagonal
    boolean hasMatchInFirstRow(byte playerId);
    boolean hasMatchInSecondRow(byte playerId);
    boolean hasMatchInThirdRow(byte playerId);
    boolean hasMatchInFirstColumn(byte playerId);
    boolean hasMatchInSecondColumn(byte playerId);
    boolean hasMatchInThirdColumn(byte playerId);
    boolean hasMatchInLeftToRightDiagonal(byte playerId);
    boolean hasMatchInRightToLeftDiagonal(byte playerId);
}
