package ee.taltech.iti0213.dara.game.player

import ee.taltech.iti0213.dara.game.board.IBoard
import ee.taltech.iti0213.dara.game.board.IMove
import ee.taltech.iti0213.dara.game.board.IPosition
import ee.taltech.iti0213.dara.game.board.IStone
import ee.taltech.iti0213.dara.game.player.statistics.Statistics

interface IPlayer<T: IStone, U: IPosition> {

    fun onUserClickedLocation(location: U)
    suspend fun getPutMove(board: IBoard<T, U>): U
    suspend fun getMove(board: IBoard<T, U>): IMove<U>
    suspend fun getTakeMove(board: IBoard<T, U>): U
    fun getStatistics(): Statistics
    fun getName(): String
}