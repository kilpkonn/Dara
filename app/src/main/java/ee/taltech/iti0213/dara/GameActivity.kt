package ee.taltech.iti0213.dara

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ee.taltech.iti0213.dara.board.Position
import ee.taltech.iti0213.dara.board.SimpleBoard
import ee.taltech.iti0213.dara.constants.C

class GameActivity : AppCompatActivity() {

    private lateinit var gameSession: GameSession
    private var handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val player1Strategy = intent!!.extras!!.getString(C.PLAYER1_STRATEGY_KEY)!!
        val player2Strategy = intent!!.extras!!.getString(C.PLAYER2_STRATEGY_KEY)!!

        gameSession = savedInstanceState?.getSerializable(C.GAME_SESSION_KEY) as GameSession?
            ?: GameSession(player1Strategy, player2Strategy)

        setupStatistics(gameSession.playerWhite.getName(), R.id.player1)
        setupStatistics(gameSession.playerBlack.getName(), R.id.player2)


        handler.postDelayed({
            updateBoard(gameSession.board)
        }, C.GAME_REFRESH_DELAY)
    }

    override fun onStart() {
        super.onStart()
        gameSession.playGame()
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

    fun onBoardClick(view: View) {
        val id = view.id
        val idString = view.resources.getResourceName(id)
        val y = C.BOARD_HEIGHT - (idString[idString.lastIndex].toInt() - 'A'.toInt())
        val x = idString[idString.lastIndex - 1].toInt() - '0'.toInt()
        gameSession.onButtonClick(Position(y, x))
        //findViewById<Button>(id).foreground = resources.getDrawable(R.drawable.stone_triangle, theme)
    }

    private fun updateBoard(board: SimpleBoard<Position>) {
        val parent = findViewById<ViewGroup>(R.id.board)
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)

            if (child is Button) {
                val idString = parent.resources.getResourceName(child.id)
                val a = idString[idString.lastIndex]
                val x = idString[idString.lastIndex].toInt() - 'A'.toInt()
                val y = Character.getNumericValue(idString[idString.lastIndex - 1])

                if (board.getBoardMatrix()[y][x].isWhite())
                    child.foreground = resources.getDrawable(R.drawable.stone_triangle, theme)
                if (board.getBoardMatrix()[y][x].isBlack())
                    child.foreground = resources.getDrawable(R.drawable.stone_square, theme)
                else
                    child.foreground = null
            }
        }

    }
}
