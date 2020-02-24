package ee.taltech.iti0213.dara

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ee.taltech.iti0213.dara.game.board.*
import ee.taltech.iti0213.dara.game.constants.C
import ee.taltech.iti0213.dara.game.GameSession
import ee.taltech.iti0213.dara.game.player.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {

    private lateinit var gameSession: GameSession
    private var handler: Handler = Handler()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val player1Strategy = intent!!.extras!!.getString(C.PLAYER1_STRATEGY_KEY)!!
        val player2Strategy = intent!!.extras!!.getString(C.PLAYER2_STRATEGY_KEY)!!

        gameSession = savedInstanceState?.getSerializable(C.GAME_SESSION_KEY) as GameSession?
            ?: GameSession(player1Strategy, player2Strategy)

        setupStatistics(gameSession.playerWhite, R.id.player1)
        setupStatistics(gameSession.playerBlack, R.id.player2)


        handler.postDelayed({
            updateBoard(gameSession.board)
        }, C.GAME_REFRESH_DELAY)
    }

    override fun onStart() {
        super.onStart()
        coroutineScope.launch {
            gameSession.playGame()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putSerializable(C.GAME_SESSION_KEY, gameSession)
        }
        super.onSaveInstanceState(outState)
    }


    @SuppressLint("SetTextI18n")
    private fun setupStatistics(player: Player<Stone, Position>, viewId: Int) {
        val playerView = findViewById<View>(viewId)
        val stats = player.getStatistics()

        playerView.findViewById<TextView>(R.id.txt_player).text = player.getName()
        playerView.findViewById<TextView>(R.id.txt_setupTime).text = "Setup time: ${stats.getSetupThinkingTime()}s"
        playerView.findViewById<TextView>(R.id.txt_moves).text = "Moves: ${stats.getTotalMove()}"
        playerView.findViewById<TextView>(R.id.txt_thinkingTime).text = "Time: ${stats.getTotalThinkingTime()}s"
        playerView.findViewById<TextView>(R.id.txt_timePerMove).text = "Time/move: ${stats.getTimePerMove()}s"
    }

    fun onBoardClick(view: View) {
        val id = view.id
        val idString = view.resources.getResourceName(id)
        val x = idString[idString.lastIndex].toInt() - 'A'.toInt()
        val y = Character.getNumericValue(idString[idString.lastIndex - 1])
        gameSession.onButtonClick(Position(y, x))
        //findViewById<Button>(id).foreground = resources.getDrawable(R.drawable.stone_triangle, theme)
    }

    private fun updateBoard(board: SimpleBoard<Position>) {
        val parent = findViewById<ViewGroup>(R.id.board)
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)

            if (child is Button) {
                val idString = parent.resources.getResourceName(child.id)
                val x = idString[idString.lastIndex].toInt() - 'A'.toInt()
                val y = Character.getNumericValue(idString[idString.lastIndex - 1])

                when {
                    board.getBoardMatrix()[y][x].isWhite() -> child.foreground = resources.getDrawable(R.drawable.stone_triangle, theme)
                    board.getBoardMatrix()[y][x].isBlack() -> child.foreground = resources.getDrawable(R.drawable.stone_square, theme)
                    else -> child.foreground = null
                }
            }
        }
        setupStatistics(gameSession.playerWhite, R.id.player1)
        setupStatistics(gameSession.playerBlack, R.id.player2)

        handler.postDelayed({
            updateBoard(gameSession.board)
        }, C.GAME_REFRESH_DELAY)

    }
}
