package ee.taltech.iti0213.dara.board

interface IBoard <T : IStone, U : IPosition>{

    fun getBoardMatrix(): Array<Array<T>>

    fun makeMove(move: U): Boolean

    fun getHeight(): Int

    fun getWidth(): Int
}