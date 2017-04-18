package lz.renatkaitmazov.tictactoe.main;

import lz.renatkaitmazov.tictactoe.base.MvpView;

/**
 * @author Renat Kaitmazov
 */

public interface MainMvpView extends MvpView {
    void onGameLoaded(byte[] playersMoves);
    void onPlayerMoved(int playerId, int index);
    void onGameIsOver(byte winnerId);
    void onTie();
}
