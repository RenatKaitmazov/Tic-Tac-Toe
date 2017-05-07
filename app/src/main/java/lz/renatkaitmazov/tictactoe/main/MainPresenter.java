package lz.renatkaitmazov.tictactoe.main;

import lz.renatkaitmazov.tictactoe.base.Presenter;

/**
 * @author Renat Kaitmazov
 */

public interface MainPresenter extends Presenter<MainMvpView> {
    void loadGame();
    void onCellClicked(int index);
    void startNewGame();
}
