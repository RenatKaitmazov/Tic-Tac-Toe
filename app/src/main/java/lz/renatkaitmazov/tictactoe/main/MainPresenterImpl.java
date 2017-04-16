package lz.renatkaitmazov.tictactoe.main;

import lz.renatkaitmazov.tictactoe.base.BasePresenter;
import lz.renatkaitmazov.tictactoe.di.app.AppModule;
import lz.renatkaitmazov.tictactoe.model.Game;

/**
 * @author Renat Kaitmazov
 */

public final class MainPresenterImpl extends BasePresenter<MainMvpView> implements MainPresenter {

    /** Instance variables **/

    private final Game ticTacToeGame;
    private byte playerId = -1;

    /** Constructors **/

    public MainPresenterImpl(Game game) {
        ticTacToeGame = game;
    }

    /** API **/

    @Override
    public void onButtonClicked(int index) {
        final short row = (short) (index / AppModule.GRID_LENGTH);
        final short column = (short) (index % AppModule.GRID_LENGTH);
        if (ticTacToeGame.setAt(row, column, playerId)) {
            // TODO make the view give a visual feedback after each move of the two players.
            if (ticTacToeGame.playerHasWon(playerId)) {
                // TODO let the view know that we have a winner
            } else {
                // The other player's turn
                playerId = (byte) -playerId;
                if (view != null) {

                }
            }
        }
    }
}
