package lz.renatkaitmazov.tictactoe.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
}
