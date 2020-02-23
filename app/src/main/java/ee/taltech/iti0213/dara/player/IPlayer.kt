package ee.taltech.iti0213.dara.player

import ee.taltech.iti0213.dara.board.IBoard
import ee.taltech.iti0213.dara.board.IMove
import ee.taltech.iti0213.dara.board.IPosition
import ee.taltech.iti0213.dara.board.IStone
import ee.taltech.iti0213.dara.player.statistics.Statistics

interface IPlayer<T: IStone, U: IPosition> {

    fun getPutMove(board: IBoard<T, U>): U
    fun getMove(board: IBoard<T, U>): IMove<U>
    fun fetTakeMove(board: IBoard<T, U>): U
    fun getStatistics(): Statistics
    fun getName(): String
}