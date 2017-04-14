package lz.renatkaitmazov.tictactoe.di.fragment;

import dagger.Subcomponent;
import lz.renatkaitmazov.tictactoe.di.scope.PerFragment;
import lz.renatkaitmazov.tictactoe.main.MainFragment;

/**
 * @author Renat Kaitmazov
 */

@PerFragment
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {
    void inject(MainFragment mainFragment);
}
