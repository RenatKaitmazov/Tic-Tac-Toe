package lz.renatkaitmazov.tictactoe.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import lz.renatkaitmazov.tictactoe.R;
import lz.renatkaitmazov.tictactoe.base.BaseFragment;
import lz.renatkaitmazov.tictactoe.base.Presenter;
import lz.renatkaitmazov.tictactoe.customviews.GameView;
import lz.renatkaitmazov.tictactoe.databinding.FragmentMainBinding;
import lz.renatkaitmazov.tictactoe.di.fragment.FragmentComponent;

/**
 * @author Renat Kaitmazov
 */

public final class MainFragment extends BaseFragment<MainMvpView>
        implements MainMvpView, GameView.GameViewListener {

    /** Instance variables **/

    @Inject MainPresenter presenter;

    private GameView gameView;

    /** Lifecycle **/

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final FragmentMainBinding binding =
                DataBindingUtil.inflate(inflater,
                        R.layout.fragment_main, container, false);
        gameView = ((GameView) binding.getRoot());
        gameView.setGameViewListener(this);
        return binding.getRoot();
    }

    @Override
    public final void onStart() {
        super.onStart();
        presenter.loadGame();
    }

    /** BaseFragment implementation **/

    @Override
    protected final void onFragmentComponentCreated(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected final Presenter<MainMvpView> getPresenter() {
        return presenter;
    }

    @Override
    protected final MainMvpView getMvpView() {
        return this;
    }

    /** MainMvpView implementation **/

    @Override
    public final void showProgress() {
    }

    @Override
    public final void hideProgress() {
    }

    @Override
    public final void onGameLoaded(byte[] playersMoves) {

    }

    @Override
    public final void onPlayerMoved(int playerId, int index) {
        gameView.drawMarkerAtIndex(playerId, index);
        if (playerId == +1) {
            gameView.switchFocusToOMarkerThumbnail();
        } else if (playerId == -1) {
            gameView.switchFocusToXMarkerThumbnail();
        } else {
            throw new IllegalArgumentException("Unknown played id: " + playerId);
        }
    }

    @Override
    public final void onGameIsOver(byte winnerId) {
        // TODO: 1) Show a dialog to the user
        System.out.println(winnerId + " won the game.");
    }

    @Override
    public final void onTie() {
        // TODO: 2) Show a dialog to the user
        System.out.println("It is a tie!");
    }

    @Override
    public final void onOccupiedCellClicked(int index) {
        System.out.println("The cell has already been chosen by " + index);
    }

    @Override
    public final void onMatchInRow(int row) {
        System.out.println("Match in row " + row);
    }

    @Override
    public final void onMatchInColumn(int column) {
        System.out.println("Match in column " + column);
    }

    @Override
    public final void onMatchInDiagonal(int diagonal) {
        if (diagonal == 0) {
            System.out.println("Match in left to right diagonal.");
        } else if (diagonal == 1) {
            System.out.println("Match in right to left diagonal.");
        } else {
            System.out.println("Unknown diagonal " + diagonal);
        }
    }

    /** GameView.GameViewListener implementation **/

    @Override
    public final void onCellClicked(int index) {
        presenter.onCellClicked(index);
        System.out.println("Clicked at index: " + index);
    }

    @Override
    public final void onOutsideGridClicked() {
        System.out.println("Clicked outside of the grid");
    }

    @Override
    public final void onFingerMovedAwayFromCell() {
        System.out.println("Finger moved away from cell");
    }

    /** API **/

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    /** Helper methods **/


}
