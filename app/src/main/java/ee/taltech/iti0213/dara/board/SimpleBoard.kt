package ee.taltech.iti0213.dara.board

class SimpleBoard<U : IPosition> : IBoard<Stone, U> {

    private var matrix: Array<Array<Stone>>
    private var isWhiteToMove: Boolean
    private val height: Int
    private val width: Int

    companion object {
        fun Array<Array<Stone>>.copy() = Array(size) { get(it).clone() }
    }

    constructor(height: Int, width: Int) {
        this.matrix = Array(height) { Array(width) { Stone.EMPTY } }
        this.isWhiteToMove = true
        this.height = height
        this.width = width
    }


    override fun getBoardMatrix(): Array<Array<Stone>> {
        return matrix.copy()
    }

    override fun makeMove(move: U): Boolean {
        if (matrix[move.getY()][move.getX()] != Stone.EMPTY) return false
        matrix[move.getY()][move.getY()] = if (isWhiteToMove) Stone.WHITE else Stone.BLACK
        isWhiteToMove = !isWhiteToMove
        return true
    }

    override fun getHeight(): Int {
        return height
    }

    override fun getWidth(): Int {
        return width
    }
}