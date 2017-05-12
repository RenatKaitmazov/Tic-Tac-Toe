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
    private byte playerId = +1;
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
            if (gameIsOver) {
                view.onGameIsOver(playerId);
            } else if (movesTillTie == 0) {
                view.onTie();
            } else {
                view.showProgress();
                final byte[] playersMoves = ticTacToeGame.getPlayersMoves();
                view.onGameLoaded(playersMoves);
                view.hideProgress();
            }
        }
    }

    @Override
    public void onCellClicked(int index) {
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
                gameIsOver = true;
                if (ticTacToeGame.hasMatchInFirstRow(playerId)) {
                    view.onMatchInRow(0);
                } else if (ticTacToeGame.hasMatchInSecondRow(playerId)) {
                    view.onMatchInRow(1);
                } else if (ticTacToeGame.hasMatchInThirdRow(playerId)) {
                    view.onMatchInRow(2);
                } else if (ticTacToeGame.hasMatchInFirstColumn(playerId)) {
                    view.onMatchInColumn(0);
                } else if (ticTacToeGame.hasMatchInSecondColumn(playerId)) {
                    view.onMatchInColumn(1);
                } else if (ticTacToeGame.hasMatchInThirdColumn(playerId)) {
                    view.onMatchInColumn(2);
                } else if (ticTacToeGame.hasMatchInLeftToRightDiagonal(playerId)) {
                    view.onMatchInDiagonal(0);
                } else if (ticTacToeGame.hasMatchInRightToLeftDiagonal(playerId)) {
                    view.onMatchInDiagonal(1);
                } else {
                    gameIsOver = false;
                    playerId = (byte) -playerId;
                    --movesTillTie;
                    if (movesTillTie == 0) {
                        view.onTie();
                    }
                }
            } else {
                view.onOccupiedCellClicked(index);
            }
        }
    }

    @Override
    public final void startNewGame() {
        playerId = +1;
        gameIsOver = false;
        movesTillTie = AppModule.CELL_PER_ROW * AppModule.CELL_PER_ROW;
        ticTacToeGame.resetGame();
    }
}
