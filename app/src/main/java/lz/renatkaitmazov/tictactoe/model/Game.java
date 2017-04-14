package lz.renatkaitmazov.tictactoe.model;

/**
 * @author Renat Kaitmazov
 */

public interface Game {
    short boardLength();
    byte getAt(short row, short column);
    void setAt(short row, short column, byte value);
    void resetGame();
}
