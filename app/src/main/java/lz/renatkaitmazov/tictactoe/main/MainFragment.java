package lz.renatkaitmazov.tictactoe.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import javax.inject.Inject;

import lz.renatkaitmazov.tictactoe.R;
import lz.renatkaitmazov.tictactoe.base.BaseFragment;
import lz.renatkaitmazov.tictactoe.base.Presenter;
import lz.renatkaitmazov.tictactoe.databinding.FragmentMainBinding;
import lz.renatkaitmazov.tictactoe.di.app.AppModule;
import lz.renatkaitmazov.tictactoe.di.fragment.FragmentComponent;

/**
 * @author Renat Kaitmazov
 */

public final class MainFragment extends BaseFragment<MainMvpView> implements MainMvpView {

    /** Instance variables **/
    @Inject MainPresenter presenter;

    private final Button[] buttons = new Button[AppModule.GRID_LENGTH * AppModule.GRID_LENGTH];

    /** Lifecycle **/

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final FragmentMainBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        setupButtons(binding);
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
        final int size = playersMoves.length;
        for (int i = 0; i < size; ++i) {
            final byte move = playersMoves[i];
            if (move != 0) {
                final Button btn = buttons[i];
                btn.setText(move == -1 ? "X" : "O");
            }
        }
    }

    @Override
    public final void onPlayerMoved(int playerId, int index) {
        final Button btn = buttons[index];
        final String symbol;
        if (playerId == -1) {
            symbol = getString(R.string.mark_player_1);
        } else if (playerId == +1) {
            symbol = getString(R.string.mark_player_2);
        } else {
            symbol = "";
        }
        btn.setText(symbol);
    }

    @Override
    public final void onGameIsOver(byte winnerId) {
        // TODO: 1) Show a dialog to the user
        print(winnerId + " has won the game.");
        startNewGame();
    }

    @Override
    public final void onTie() {
        // TODO: 2) Show a dialog to the user
        print("Nobody has been able to win.");
        startNewGame();
    }

    /** API **/

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    /** Helper methods **/

    private void setupButtons(FragmentMainBinding binding) {
        final ConstraintLayout root = binding.rootMainFragment;
        final int childCount = root.getChildCount();

        for (int i = 0; i < childCount; ++i) {
            buttons[i] = (Button) root.getChildAt(i);
        }

        for (int i = 0; i < childCount; ++i) {
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Button btn = (Button) view;
                    final int index = Integer.parseInt(btn.getTag().toString());
                    presenter.onButtonClicked(index);
                }
            });
        }
    }

    private void print(String msg) {
        final String log = "MainFragment";
        Log.i(log, msg);
    }

    private void startNewGame() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetButtons();
                presenter.startNewGame();
            }
        }, 1_000L);
    }

    private void resetButtons() {
        for (final Button btn : buttons) {
            btn.setText("");
        }
    }
}
