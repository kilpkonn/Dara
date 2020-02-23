package ee.taltech.iti0213.dara.player

import ee.taltech.iti0213.dara.board.IBoard
import ee.taltech.iti0213.dara.board.IMove
import ee.taltech.iti0213.dara.board.IPosition
import ee.taltech.iti0213.dara.board.IStone
import ee.taltech.iti0213.dara.player.statistics.Statistics
import ee.taltech.iti0213.dara.player.strategy.IStrategy

class Player<T: IStone, U:IPosition>(private val strategy: IStrategy<T, U>): IPlayer<T, U> {
    private val statistics: Statistics = Statistics()

    override fun getPutMove(board: IBoard<T, U>): U {
        return strategy.getPutMove(board)
    }

    override fun getMove(board: IBoard<T, U>): IMove<U> {
        return strategy.getMove(board)
    }

    override fun fetTakeMove(board: IBoard<T, U>): U {
        return strategy.getTakeMove(board)
    }

    override fun getStatistics(): Statistics {
        return statistics
    }

    override fun getName(): String {
        return strategy.getName()
    }
}