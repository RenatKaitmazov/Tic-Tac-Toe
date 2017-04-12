package lz.renatkaitmazov.tictactoe.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import lz.renatkaitmazov.tictactoe.R;
import lz.renatkaitmazov.tictactoe.TicTacToeApp;
import lz.renatkaitmazov.tictactoe.model.Grid;

public class MainActivity extends AppCompatActivity {

    /** Instance variables **/


    /** Lifecycle **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
