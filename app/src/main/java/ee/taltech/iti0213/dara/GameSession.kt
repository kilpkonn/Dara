package ee.taltech.iti0213.dara

import ee.taltech.iti0213.dara.board.Position
import ee.taltech.iti0213.dara.board.SimpleBoard
import ee.taltech.iti0213.dara.board.Stone
import ee.taltech.iti0213.dara.constants.C
import ee.taltech.iti0213.dara.player.Player
import ee.taltech.iti0213.dara.player.strategy.HumanStrategy
import ee.taltech.iti0213.dara.player.strategy.IStrategy
import ee.taltech.iti0213.dara.player.strategy.RandomStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

class GameSession(player1Strategy: String, player2Strategy: String) : Serializable {
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

    fun playGame() {
        coroutineScope.launch {
            while (true) {
                if (isWhiteToMove) {
                    board.putStone(playerWhite.getPutMove(board))
                } else {
                    board.putStone(playerBlack.getPutMove(board))
                }
            }
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