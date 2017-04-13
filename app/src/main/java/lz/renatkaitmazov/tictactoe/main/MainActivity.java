package lz.renatkaitmazov.tictactoe.main;

import lz.renatkaitmazov.tictactoe.base.SingleFragmentActivity;

public final class MainActivity extends SingleFragmentActivity<MainFragment> {

    /** Abstract method implementation **/

    @Override
    protected MainFragment getFragment() {
        return MainFragment.newInstance();
    }
}
