package lz.renatkaitmazov.tictactoe.di.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * All dependencies whose lifecycle
 * lasts as long as the lifecycle of
 * the application are provided in this module.
 *
 * @author Renat Kaitmazov
 */

@Module
public final class AppModule {

    /** Instance variables **/

    private final Application app;

    /** Constructors **/

    public AppModule(@NonNull Application app) {
        this.app = app;
    }

    /** API **/

    @Provides
    @Singleton
    public final Context provideAppContext() {
        return this.app.getApplicationContext();
    }
}
