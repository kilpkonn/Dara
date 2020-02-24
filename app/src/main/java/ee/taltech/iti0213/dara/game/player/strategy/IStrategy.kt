package ee.taltech.iti0213.dara.game.player.strategy

import ee.taltech.iti0213.dara.game.board.IBoard
import ee.taltech.iti0213.dara.game.board.IMove
import ee.taltech.iti0213.dara.game.board.IPosition
import ee.taltech.iti0213.dara.game.board.IStone

interface IStrategy<T: IStone, U: IPosition> {

    fun onUserClickedLocation(location: U)
    suspend fun getPutMove(board: IBoard<T, U>): U
    suspend fun getMove(board: IBoard<T, U>): IMove<U>
    suspend fun getTakeMove(board: IBoard<T, U>): U
    fun getName(): String
}