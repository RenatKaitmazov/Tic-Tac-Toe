package lz.renatkaitmazov.tictactoe.main;

import lz.renatkaitmazov.tictactoe.base.MvpView;

/**
 * @author Renat Kaitmazov
 */

public interface MainMvpView extends MvpView {
    void onGameLoaded(byte[] playersMoves);
    void onPlayerMoved(int playerId, int index);
    void onGameIsOver(byte winnerId);
    void onOccupiedCellClicked(int index);
    void onTie();

    void onMatchInRow(int row);
    void onMatchInColumn(int column);

    /**
     * A callback that is triggered when there is a match in any diagonal.
     *
     * @param diagonal 0 if the diagonal is left to right, 1 if the diagonal is right to left.
     */
    void onMatchInDiagonal(int diagonal);
}
