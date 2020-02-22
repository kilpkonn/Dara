package ee.taltech.iti0213.dara.board

import ee.taltech.iti0213.dara.board.enums.GameState

interface IBoard <T : IStone, U : IPosition>{

    fun getBoardMatrix(): Array<Array<T>>

    fun putStone(move: U): Boolean

    fun getHeight(): Int

    fun getWidth(): Int

    fun getGameState(): GameState
}