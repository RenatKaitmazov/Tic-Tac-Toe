package lz.renatkaitmazov.tictactoe.main;

import android.support.v4.app.FragmentTransaction;

import lz.renatkaitmazov.tictactoe.R;
import lz.renatkaitmazov.tictactoe.base.SingleFragmentActivity;

public final class MainActivity extends SingleFragmentActivity<MainFragment>
        implements MainFragment.Callback, GameOverDialog.Callback {

    /** Abstract method implementation **/

    @Override
    protected MainFragment getFragment() {
        return MainFragment.newInstance();
    }

    /** MainFragment.Callback implementation **/

    @Override
    public void onGameIdOver(int winnerId) {
        showGameOverDialog(winnerId);
    }

    @Override
    public void onTie() {
        showGameOverDialog(0);
    }

    /** GameOverDialog.Callback implementation **/

    @Override
    public void onStartNewGame() {
        ((MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.containerSingleFragment))
                .startNewGame();
    }

    @Override
    public void onExit() {
        finish();
    }

    /** Helper methods **/

    private void showGameOverDialog(int winnerId) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        final GameOverDialog gameOverDialog = GameOverDialog.Companion.newInstance(winnerId);
        gameOverDialog.show(transaction, GameOverDialog.TAG);
    }
}
