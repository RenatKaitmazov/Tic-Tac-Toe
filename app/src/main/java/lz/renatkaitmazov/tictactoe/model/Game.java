package lz.renatkaitmazov.tictactoe.model;

/**
 * @author Renat Kaitmazov
 */

public interface Game {
    short boardLength();
    byte getAt(short row, short column);
    boolean setAt(short row, short column, byte value);
    void resetGame();
    boolean playerHasWon(byte playerId);
    byte[] getPlayersMoves();
}
