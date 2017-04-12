package lz.renatkaitmazov.tictactoe.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import lz.renatkaitmazov.tictactoe.R;
import lz.renatkaitmazov.tictactoe.databinding.ActivitySingleFragmentBinding;

/**
 * Any activity that holds only one fragment at a time
 * should extend this class.
 *
 * @author Renat Kaitmazov
 */

public abstract class SingleFragmentActivity<F extends Fragment> extends AppCompatActivity {

    /** Instance variables **/

    protected ActivitySingleFragmentBinding singleFragmentBinding;

    /** Lifecycle **/

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singleFragmentBinding = DataBindingUtil.setContentView(this, R.layout.activity_single_fragment);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final F fragment = (F) fragmentManager.findFragmentById(R.id.containerSingleFragment);
        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.containerSingleFragment, getFragment())
                    .commitNow();
        }
    }

    /** Abstract methods **/

    protected abstract F getFragment();
}
