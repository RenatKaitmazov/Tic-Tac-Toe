package lz.renatkaitmazov.tictactoe.di.app;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Renat Kaitmazov
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
}
