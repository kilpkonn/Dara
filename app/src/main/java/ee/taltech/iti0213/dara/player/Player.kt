package ee.taltech.iti0213.dara.player

import ee.taltech.iti0213.dara.board.IBoard
import ee.taltech.iti0213.dara.board.IMove
import ee.taltech.iti0213.dara.board.IPosition
import ee.taltech.iti0213.dara.board.IStone
import ee.taltech.iti0213.dara.player.statistics.Statistics
import ee.taltech.iti0213.dara.player.strategy.IStrategy
import java.io.Serializable

class Player<T: IStone, U:IPosition>(private val strategy: IStrategy<T, U>):
    IPlayer<T, U>, Serializable {
    private val statistics: Statistics = Statistics()

    override fun onUserClickedLocation(location: U) {
        strategy.onUserClickedLocation(location)
    }

    override suspend fun getPutMove(board: IBoard<T, U>): U {
        return strategy.getPutMove(board)
    }

    override suspend fun getMove(board: IBoard<T, U>): IMove<U> {
        return strategy.getMove(board)
    }

    override suspend fun getTakeMove(board: IBoard<T, U>): U {
        return strategy.getTakeMove(board)
    }

    override fun getStatistics(): Statistics {
        return statistics
    }

    override fun getName(): String {
        return strategy.getName()
    }
}