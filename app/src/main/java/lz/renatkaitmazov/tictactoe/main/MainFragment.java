package lz.renatkaitmazov.tictactoe.main;

import android.content.Context;
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

    /** **/
    public interface Callback {
        void onTie();
        void onGameIdOver(int winnerId);
    }

    /** Instance variables **/

    @Inject MainPresenter presenter;
    private GameView gameView;
    private Callback callback;
    private int winnerId;

    /** Lifecycle **/

    public final void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Callback)) {
            throw new IllegalArgumentException(context.toString() + " must implement MainFragment.Callback");
        }
        callback = ((Callback) context);
    }

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
        if (winnerId == 0) {
            callback.onTie();
        }
        this.winnerId = winnerId;
    }

    @Override
    public final void onTie() {
        callback.onTie();
    }

    @Override
    public final void onOccupiedCellClicked(int index) {
    }

    @Override
    public final void onMatchInRow(int row) {
        gameView.crossRow(row);
    }

    @Override
    public final void onMatchInColumn(int column) {
        gameView.crossColumn(column);
    }

    @Override
    public final void onMatchInDiagonal(int diagonal) {
        gameView.crossDiagonal(diagonal);
    }

    /** GameView.GameViewListener implementation **/

    @Override
    public final void onCellClicked(int index) {
        presenter.onCellClicked(index);
    }

    @Override
    public final void onOutsideGridClicked() {
    }

    @Override
    public final void onFingerMovedAwayFromCell() {
    }

    @Override
    public void onCrossLineFinish() {
        callback.onGameIdOver(winnerId);
    }

    /** API **/

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    public void startNewGame() {
        presenter.startNewGame();
        gameView.startNewGame();
    }

    /** Helper methods **/


}
