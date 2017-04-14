package lz.renatkaitmazov.tictactoe.di.app;

import javax.inject.Singleton;

import dagger.Component;
import lz.renatkaitmazov.tictactoe.di.fragment.FragmentComponent;
import lz.renatkaitmazov.tictactoe.di.fragment.FragmentModule;
import lz.renatkaitmazov.tictactoe.main.MainActivity;

/**
 * @author Renat Kaitmazov
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    FragmentComponent addFragmentComponent(FragmentModule fragmentModule);
}
