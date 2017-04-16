package lz.renatkaitmazov.tictactoe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import lz.renatkaitmazov.tictactoe.TicTacToeApp;
import lz.renatkaitmazov.tictactoe.di.fragment.FragmentComponent;
import lz.renatkaitmazov.tictactoe.di.fragment.FragmentModule;

/**
 * @author Renat Kaitmazov
 */

public abstract class BaseFragment<V extends MvpView> extends Fragment {

    private FragmentComponent fragmentComponent;

    /** Lifecycle **/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (fragmentComponent == null) {
            fragmentComponent = ((TicTacToeApp) getActivity().getApplication())
                    .getAppComponent()
                    .addFragmentComponent(new FragmentModule());
            onFragmentComponentCreated(fragmentComponent);
        }

        if (getPresenter() != null) {
            getPresenter().bind(getMvpView());
        }
    }

    @Override
    public void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().unbind();
        }
        fragmentComponent = null;
        super.onDestroy();
    }

    /** Abstract methods **/

    protected abstract void onFragmentComponentCreated(FragmentComponent fragmentComponent);
    protected abstract Presenter<V> getPresenter();
    protected abstract V getMvpView();
}
