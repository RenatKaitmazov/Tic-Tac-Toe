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
    private short movesTillTie = AppModule.CELL_PER_ROW * AppModule.CELL_PER_ROW;
    private boolean gameIsOver;

    /** Constructors **/

    public MainPresenterImpl(Game game) {
        ticTacToeGame = game;
    }

    /** API **/

    @Override
    public final void loadGame() {
        if (view != null) {
            view.showProgress();
            final byte[] playersMoves = ticTacToeGame.getPlayersMoves();
            view.onGameLoaded(playersMoves);
            if (gameIsOver) {
                view.onGameIsOver(playerId);
            }

            if (movesTillTie == 0) {
                view.onTie();
            }
            view.hideProgress();
        }
    }

    @Override
    public void onButtonClicked(int index) {
        if (view != null) {
            if (gameIsOver) {
                view.onGameIsOver(playerId);
                return;
            }

            if (movesTillTie == 0) {
                view.onTie();
                return;
            }

            final short row = (short) (index / AppModule.CELL_PER_ROW);
            final short column = (short) (index % AppModule.CELL_PER_ROW);
            if (ticTacToeGame.setAt(row, column, playerId)) {
                view.onPlayerMoved(playerId, index);
                if (ticTacToeGame.playerHasWon(playerId)) {
                    gameIsOver = true;
                    view.onGameIsOver(playerId);
                } else  {
                    playerId = (byte) -playerId;
                    --movesTillTie;
                    if (movesTillTie == 0) {
                        view.onTie();
                    }
                }
            }
        }
    }

    @Override
    public final void startNewGame() {
        playerId = -1;
        gameIsOver = false;
        movesTillTie = AppModule.CELL_PER_ROW * AppModule.CELL_PER_ROW;
        ticTacToeGame.resetGame();
    }
}
