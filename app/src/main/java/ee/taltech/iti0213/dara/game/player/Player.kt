package ee.taltech.iti0213.dara.game.player

import ee.taltech.iti0213.dara.game.board.IBoard
import ee.taltech.iti0213.dara.game.board.IMove
import ee.taltech.iti0213.dara.game.board.IPosition
import ee.taltech.iti0213.dara.game.board.IStone
import ee.taltech.iti0213.dara.game.player.statistics.Statistics
import ee.taltech.iti0213.dara.game.player.strategy.IStrategy
import java.io.Serializable

class Player<T: IStone, U:IPosition>(private val strategy: IStrategy<T, U>):
    IPlayer<T, U>, Serializable {
    private val statistics: Statistics = Statistics()

    override fun onUserClickedLocation(location: U) {
        strategy.onUserClickedLocation(location)
    }

    override suspend fun getPutMove(board: IBoard<T, U>): U {
        val start = System.currentTimeMillis()
        val move = strategy.getPutMove(board)
        statistics.addSetupMove((System.currentTimeMillis() - start).toFloat())
        return move
    }

    override suspend fun getMove(board: IBoard<T, U>): IMove<U> {
        val start = System.currentTimeMillis()
        val move = strategy.getMove(board)
        statistics.addMove((System.currentTimeMillis() - start).toFloat())
        return move
    }

    override suspend fun getTakeMove(board: IBoard<T, U>): U {
        val start = System.currentTimeMillis()
        val move = strategy.getTakeMove(board)
        statistics.addTakeMove((System.currentTimeMillis() - start).toFloat())
        return move
    }

    override fun getStatistics(): Statistics {
        return statistics
    }

    override fun getName(): String {
        return strategy.getName()
    }
}