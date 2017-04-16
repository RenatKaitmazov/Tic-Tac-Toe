package lz.renatkaitmazov.tictactoe.di.fragment;

import dagger.Module;
import dagger.Provides;
import lz.renatkaitmazov.tictactoe.di.scope.PerFragment;
import lz.renatkaitmazov.tictactoe.main.MainPresenter;
import lz.renatkaitmazov.tictactoe.main.MainPresenterImpl;
import lz.renatkaitmazov.tictactoe.model.Game;

/**
 * All dependencies whose lifecycle
 * lasts as long as the lifecycle of
 * the fragment are provided in this module.
 *
 * @author Renat Kaitmazov
 */

@Module
public final class FragmentModule {

    /** API **/

    @Provides
    @PerFragment
    final MainPresenter provideMainPresenter(Game game) {
        return new MainPresenterImpl(game);
    }
}
