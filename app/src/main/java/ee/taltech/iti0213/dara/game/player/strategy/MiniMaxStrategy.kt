package ee.taltech.iti0213.dara.game.player.strategy

import ee.taltech.iti0213.dara.game.board.*
import ee.taltech.iti0213.dara.game.constants.C
import java.util.AbstractMap

class MiniMaxStrategy<T : IStone>(isWhite: Boolean) : BaseStrategy<T>(isWhite) {

    companion object {
        fun Array<Array<Stone>>.copy() = Array(size) { get(it).clone() }
    }

    override fun onUserClickedLocation(location: Position) {
    }

    override suspend fun getPutMove(board: IBoard<T, Position>): Position {
        val matrix = board.getBoardMatrix()
        val newMatrix: Array<Array<Stone>> =
            Array(board.getHeight()) { Array(board.getWidth()) { Stone.EMPTY } }
        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                if (!matrix[y][x].isEmpty())
                    newMatrix[y][x] = if (matrix[y][x].isWhite()) Stone.WHITE else Stone.BLACK
            }
        }
        return putMiniMax(newMatrix, isWhite, Position(0, 0), 3).value
    }

    override suspend fun getMove(board: IBoard<T, Position>): IMove<Position> {
        val possibleFromPositions: MutableList<Position> = arrayListOf()
        val matrix = board.getBoardMatrix()
        for (y in 0 until board.getHeight()) {
            for (x in 0 until board.getWidth()) {
                if (matrix[y][x].isWhite() && isWhite || matrix[y][x].isBlack() && !isWhite)
                    possibleFromPositions.add(Position(y, x))
            }
        }
        val possibleToPositions: MutableList<Position> = arrayListOf()
        for (y in 0 until board.getHeight()) {
            for (x in 0 until board.getWidth()) {
                if (matrix[y][x].isEmpty())
                    possibleToPositions.add(Position(y, x))
            }
        }
        return Move(possibleFromPositions.random(), possibleToPositions.random()) // TODO: Minimax
    }

    override suspend fun getTakeMove(board: IBoard<T, Position>): Position {
        val possibleMoves: MutableList<Position> = arrayListOf()
        val matrix = board.getBoardMatrix()
        for (y in 0 until board.getHeight()) {
            for (x in 0 until board.getWidth()) {
                if (matrix[y][x].isWhite() && !isWhite || matrix[y][x].isBlack() && isWhite)
                    possibleMoves.add(Position(y, x))
            }
        }
        return possibleMoves.random() // TODO: Weighting
    }

    private fun putMiniMax(
        matrix: Array<Array<Stone>>,
        white: Boolean,
        move: Position,
        depth: Int
    ): AbstractMap.SimpleEntry<Int, Position> {
        if (depth == 0) {
            val score = countWhite(matrix, 2) - countBlack(matrix, 2)
            return AbstractMap.SimpleEntry(score * (if (white) 1 else -1), move)
        }

        var res: AbstractMap.SimpleEntry<Int, Position>? = null
        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                if (matrix[y][x].isEmpty()) {
                    val tmpBoard = matrix.copy()
                    tmpBoard[y][x] = if (white) Stone.WHITE else Stone.BLACK
                    val tmp = putMiniMax(tmpBoard, !white, Position(y, x), depth - 1)
                    if (res == null || (tmp.key < res.key && white) || (tmp.key > res.key && !white))
                        res = tmp
                }
            }
        }
        return res as AbstractMap.SimpleEntry<Int, Position>
    }

    private fun countWhite(matrix: Array<Array<Stone>>, length: Int = C.ROW_LENGTH): Int {
        var count = 0
        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                var down = matrix[y][x].isWhite()
                var right = matrix[y][x].isWhite()
                for (i in 0..length) {
                    right = right && (x + i < matrix[y].size && matrix[y][x + i].isWhite())
                    down = down && (y + i < matrix.size && matrix[y + i][x].isWhite())
                }
                if (down) count++
                if (right) count++
            }
        }
        return count
    }

    private fun countBlack(matrix: Array<Array<Stone>>, length: Int = C.ROW_LENGTH): Int {
        var count = 0
        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                var down = matrix[y][x].isWhite()
                var right = matrix[y][x].isWhite()
                for (i in 0..length) {
                    right = right && (x + i < matrix[y].size && matrix[y][x + i].isBlack())
                    down = down && (y + i < matrix.size && matrix[y + i][x].isBlack())
                }
                if (down) count++
                if (right) count++
            }
        }
        return count
    }

    override fun getName(): String {
        return "Minimax AI"
    }
}