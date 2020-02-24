package ee.taltech.iti0213.dara.player.strategy

import ee.taltech.iti0213.dara.board.IBoard
import ee.taltech.iti0213.dara.board.IMove
import ee.taltech.iti0213.dara.board.IPosition
import ee.taltech.iti0213.dara.board.IStone

interface IStrategy<T: IStone, U: IPosition> {

    fun onUserClickedLocation(location: U)
    fun getPutMove(board: IBoard<T, U>): U
    fun getMove(board: IBoard<T, U>): IMove<U>
    fun getTakeMove(board: IBoard<T, U>): U
    fun getName(): String
}