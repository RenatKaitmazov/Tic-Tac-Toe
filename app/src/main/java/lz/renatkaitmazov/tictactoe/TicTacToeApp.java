package lz.renatkaitmazov.tictactoe;

import android.app.Application;

import lz.renatkaitmazov.tictactoe.di.app.AppComponent;
import lz.renatkaitmazov.tictactoe.di.app.AppModule;
import lz.renatkaitmazov.tictactoe.di.app.DaggerAppComponent;

/**
 * @author Renat Kaitmazov
 */

public final class TicTacToeApp extends Application {

    /** Static variables **/

    private AppComponent appComponent;

    /** Lifecycle **/

    @Override
    public final void onCreate() {
        super.onCreate();
    }

    /** API **/

    public final AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

}
