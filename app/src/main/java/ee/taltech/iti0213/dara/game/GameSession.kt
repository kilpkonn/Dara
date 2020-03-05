package ee.taltech.iti0213.dara.game

import android.util.Log
import ee.taltech.iti0213.dara.game.board.Position
import ee.taltech.iti0213.dara.game.board.SimpleBoard
import ee.taltech.iti0213.dara.game.board.Stone
import ee.taltech.iti0213.dara.game.board.enums.GameState
import ee.taltech.iti0213.dara.game.constants.C
import ee.taltech.iti0213.dara.game.player.Player
import ee.taltech.iti0213.dara.game.player.strategy.HumanStrategy
import ee.taltech.iti0213.dara.game.player.strategy.IStrategy
import ee.taltech.iti0213.dara.game.player.strategy.MiniMaxStrategy
import ee.taltech.iti0213.dara.game.player.strategy.RandomStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.Serializable

class GameSession(player1Strategy: String, player2Strategy: String) : Serializable {

    companion object {
        val strategies = listOf("Simple Randomness", "Human", "Minimax AI") // TODO: Auto scan
        private val TAG = this::class.java.declaringClass!!.simpleName
    }

    val playerWhite: Player<Stone, Position>
    val playerBlack: Player<Stone, Position>
    val board = SimpleBoard<Position>(C.BOARD_HEIGHT, C.BOARD_WIDTH)
    @Transient var onSetupOver: Runnable? = null
    @Transient var onGameOver: Runnable? = null

    @Transient
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private var isWhiteToMove: Boolean = true

    init {
        playerWhite = setupPlayer(player1Strategy, true)
        playerBlack = setupPlayer(player2Strategy, false)
    }

    fun onButtonClick(location: Position) {
        playerWhite.onUserClickedLocation(location)
        playerBlack.onUserClickedLocation(location)
    }

    suspend fun playGame() {
        while (board.getGameState() == GameState.SETUP) {
            val putMove =
                withContext(coroutineScope.coroutineContext + Dispatchers.Default) {
                    if (isWhiteToMove) playerWhite.getPutMove(board)
                    else playerBlack.getPutMove(board)
                }
            if (board.putStone(putMove)) {
                isWhiteToMove = !isWhiteToMove
                Log.d(TAG, "Stone put to: $putMove")
            }
        }
        Log.d(TAG, "All stones have been placed!")
        onSetupOver?.run()
        delay(1000)

        while (board.getGameState() == GameState.PLAYING) {
            Log.d(TAG, "Make move! White to move: $isWhiteToMove")
            val move =
                withContext(coroutineScope.coroutineContext + Dispatchers.Default) {
                    if (isWhiteToMove) playerWhite.getMove(board)
                    else playerBlack.getMove(board)
                }
            var moveRes = board.makeMove(move)
            Log.d(TAG, move.toString())

            while (moveRes >= 1) {
                do {
                    val takeMove =
                        withContext(coroutineScope.coroutineContext + Dispatchers.Default) {
                            if (isWhiteToMove) playerWhite.getTakeMove(board)
                            else playerBlack.getTakeMove(board)
                        }
                    val takeRes = board.takeStone(takeMove)
                    if (takeRes) {
                        Log.d(TAG, "Stone taken from: $takeMove")
                    }
                } while (!takeRes)
                moveRes--
            }
            if (moveRes >= 0) {
                isWhiteToMove = !isWhiteToMove
            }
        }
        onGameOver?.run()
    }

    private fun setupPlayer(strategyString: String, isWhite: Boolean): Player<Stone, Position> {
        val strategy: IStrategy<Stone, Position> = when (strategyString) {
            "Simple Randomness" -> RandomStrategy(isWhite)
            "Minimax AI" -> MiniMaxStrategy(isWhite)
            else -> HumanStrategy(isWhite)
        }
        return Player(strategy)
    }
}