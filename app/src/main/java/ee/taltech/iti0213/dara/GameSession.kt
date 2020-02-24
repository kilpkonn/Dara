package ee.taltech.iti0213.dara

import ee.taltech.iti0213.dara.board.IPosition
import ee.taltech.iti0213.dara.board.IStone
import ee.taltech.iti0213.dara.board.Position
import ee.taltech.iti0213.dara.board.SimpleBoard
import ee.taltech.iti0213.dara.constants.C
import ee.taltech.iti0213.dara.player.Player
import ee.taltech.iti0213.dara.player.strategy.HumanStrategy
import ee.taltech.iti0213.dara.player.strategy.IStrategy
import ee.taltech.iti0213.dara.player.strategy.RandomStrategy
import java.io.Serializable

class GameSession: Serializable {
    private val board = SimpleBoard<Position>(C.BOARD_HEIGHT, C.BOARD_WIDTH)
    val playerWhite: Player<IStone, IPosition>
    val playerBlack: Player<IStone, IPosition>

    constructor(player1Strategy: String, player2Strategy: String) {
        playerWhite = setupPlayer(player1Strategy, true)
        playerBlack = setupPlayer(player2Strategy, false)
    }

    private fun setupPlayer(strategyString: String, isWhite: Boolean): Player<IStone, IPosition> {
        val strategy: IStrategy<IStone, IPosition> = when(strategyString) {
            "Simple Randomness" -> RandomStrategy(isWhite)
            else -> HumanStrategy()
        }
        return Player(strategy)
    }
}