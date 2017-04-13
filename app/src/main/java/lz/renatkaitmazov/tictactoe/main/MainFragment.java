package lz.renatkaitmazov.tictactoe.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lz.renatkaitmazov.tictactoe.R;
import lz.renatkaitmazov.tictactoe.databinding.FragmentMainBinding;

/**
 * @author Renat Kaitmazov
 */

public final class MainFragment extends Fragment {

    /** Lifecycle **/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final FragmentMainBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return binding.getRoot();
    }

    /** API **/

    public static MainFragment newInstance() {
        return new MainFragment();
    }
}
