package ee.taltech.iti0213.dara

import android.util.Log
import ee.taltech.iti0213.dara.board.Position
import ee.taltech.iti0213.dara.board.SimpleBoard
import ee.taltech.iti0213.dara.board.Stone
import ee.taltech.iti0213.dara.board.enums.GameState
import ee.taltech.iti0213.dara.constants.C
import ee.taltech.iti0213.dara.player.Player
import ee.taltech.iti0213.dara.player.strategy.HumanStrategy
import ee.taltech.iti0213.dara.player.strategy.IStrategy
import ee.taltech.iti0213.dara.player.strategy.RandomStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.Serializable

class GameSession(player1Strategy: String, player2Strategy: String) : Serializable {

    companion object {
        private val TAG = this::class.java.declaringClass!!.simpleName
    }

    val playerWhite: Player<Stone, Position>
    val playerBlack: Player<Stone, Position>
    val board = SimpleBoard<Position>(C.BOARD_HEIGHT, C.BOARD_WIDTH)
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

        while (board.getGameState() == GameState.PLAYING) {
            Log.d(TAG, "Make move! White to move: $isWhiteToMove")
            val move =
                withContext(coroutineScope.coroutineContext + Dispatchers.Default) {
                    if (isWhiteToMove) playerWhite.getMove(board)
                    else playerBlack.getMove(board)
                }
            val moveRes = board.makeMove(move)
            Log.d(TAG, move.toString())

            if (moveRes >= 1) {
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
            }
            if (moveRes >= 0) {
                isWhiteToMove = !isWhiteToMove
            }
            //delay(100) // To see something
        }
    }

    private fun setupPlayer(strategyString: String, isWhite: Boolean): Player<Stone, Position> {
        val strategy: IStrategy<Stone, Position> = when (strategyString) {
            "Simple Randomness" -> RandomStrategy(isWhite)
            else -> HumanStrategy()
        }
        return Player(strategy)
    }
}