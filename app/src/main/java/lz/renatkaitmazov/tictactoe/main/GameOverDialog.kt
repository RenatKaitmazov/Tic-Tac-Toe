package lz.renatkaitmazov.tictactoe.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import lz.renatkaitmazov.tictactoe.R

/**
 *
 * @author Renat Kaitmazov
 */

class GameOverDialog : DialogFragment() {

    //------------------------------------------------------------------------
    // Interfaces
    //------------------------------------------------------------------------

    interface Callback {
        fun onStartNewGame()
        fun onExit()
    }

    //------------------------------------------------------------------------
    // Static data
    //------------------------------------------------------------------------

    companion object {
        const val TAG = "GameOverDialog"
        private const val ARG_KEY_WINNER_ID = "arg.key.WINNER_ID"

        fun newInstance(winnerId: Int): GameOverDialog {
            val args = Bundle()
            args.putInt(ARG_KEY_WINNER_ID, winnerId)
            val dialog = GameOverDialog()
            dialog.arguments = args
            return dialog
        }
    }

    //------------------------------------------------------------------------
    // Properties
    //------------------------------------------------------------------------

    lateinit var callback: Callback
        private set

    //------------------------------------------------------------------------
    // DialogFragment Lifecycle Events
    //------------------------------------------------------------------------

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is Callback) {
            throw IllegalArgumentException("$context must implement GameOverDialog.Callback")
        }
        callback = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        val winnerId = arguments.getInt(ARG_KEY_WINNER_ID)
        val title = when (winnerId) {
            0 -> getString(R.string.title_tie)
            +1 -> getString(R.string.title_winner, "X")
            -1 -> getString(R.string.title_winner, "O")
            else -> throw IllegalArgumentException("Unknown winner id: $winnerId")
        }
        return AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(R.string.description_game_over_dialog)
                .setPositiveButton(R.string.title_button_new_game) {_, _ -> callback.onStartNewGame() }
                .setNegativeButton(R.string.title_button_exit) { _, _ -> callback.onExit() }
                .create()
    }
}