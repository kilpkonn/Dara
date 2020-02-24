package ee.taltech.iti0213.dara

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import ee.taltech.iti0213.dara.board.*
import ee.taltech.iti0213.dara.constants.C
import ee.taltech.iti0213.dara.player.Player
import ee.taltech.iti0213.dara.player.strategy.HumanStrategy
import ee.taltech.iti0213.dara.player.strategy.IStrategy
import ee.taltech.iti0213.dara.player.strategy.RandomStrategy

class GameActivity : AppCompatActivity() {

    private lateinit var gameSession: GameSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val player1Strategy = intent!!.extras!!.getString(C.PLAYER1_STRATEGY_KEY)!!
        val player2Strategy = intent!!.extras!!.getString(C.PLAYER2_STRATEGY_KEY)!!

        gameSession = savedInstanceState?.getSerializable(C.GAME_SESSION_KEY) as GameSession?
            ?: GameSession(player1Strategy, player2Strategy)

        setupStatistics(gameSession.playerWhite.getName(), R.id.player1)
        setupStatistics(gameSession.playerBlack.getName(), R.id.player2)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putSerializable(C.GAME_SESSION_KEY, gameSession)
        }
        super.onSaveInstanceState(outState)
    }


    private fun setupStatistics(playerStrategy: String, viewId: Int) {
        val player = findViewById<View>(viewId)

        player.findViewById<TextView>(R.id.txt_player).text = playerStrategy
    }
}
