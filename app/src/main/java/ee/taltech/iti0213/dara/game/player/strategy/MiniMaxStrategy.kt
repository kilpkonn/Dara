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
        val matrix = convertBoard(board.getBoardMatrix(), board.getHeight(), board.getWidth())
        return putMiniMax(matrix, isWhite, 3).value
    }

    override suspend fun getMove(board: IBoard<T, Position>): IMove<Position> {
        val matrix = convertBoard(board.getBoardMatrix(), board.getHeight(), board.getWidth())
        return moveMiniMax(matrix, isWhite, 4).value
    }

    override suspend fun getTakeMove(board: IBoard<T, Position>): Position {
        val matrix = convertBoard(board.getBoardMatrix(), board.getHeight(), board.getWidth())
        return takeSimple(matrix, isWhite)
    }

    private fun moveMiniMax(
        matrix: Array<Array<Stone>>,
        white: Boolean,
        depth: Int
    ): AbstractMap.SimpleEntry<Int, Move<Position>> {
        if (depth == 0) {
            val score = countWhite(matrix) - countBlack(matrix)
            return AbstractMap.SimpleEntry(-score, Move(Position(-1, -1), Position(-1, -1)))
        }

        var currentBest: AbstractMap.SimpleEntry<Int, Move<Position>>? = null

        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                if (matrix[y][x].isWhite() && white || matrix[y][x].isBlack() && !white)
                    for (i in -1..1) {
                        for (j in -1..1) {
                            if (y + i >= 0
                                && x + j >= 0
                                && y + i < matrix.size
                                && x + j < matrix[y].size
                                && matrix[y + i][x + j].isEmpty()
                                && (i == 0 || j == 0)
                            ) {
                                currentBest =
                                    testMove(matrix, white, y, x, i, j, depth, currentBest)
                            }
                        }
                    }
            }
        }
        return currentBest as AbstractMap.SimpleEntry<Int, Move<Position>>
    }

    private fun testMove(
        matrix: Array<Array<Stone>>,
        white: Boolean,
        y: Int,
        x: Int,
        i: Int,
        j: Int,
        depth: Int,
        currentBest: AbstractMap.SimpleEntry<Int, Move<Position>>?
    ): AbstractMap.SimpleEntry<Int, Move<Position>>? {
        val tmpBoard = matrix.copy()
        val streak = if (white) countWhite(tmpBoard) else countBlack(tmpBoard)
        tmpBoard[y][x] = Stone.EMPTY
        tmpBoard[y + i][x + j] = if (white) Stone.WHITE else Stone.BLACK

        if (countWhite(tmpBoard, C.ROW_LENGTH + 1) > 0
            || countBlack(tmpBoard, C.ROW_LENGTH + 1) > 0)
            return currentBest

        val newStreak = streak - (if (white) countWhite(tmpBoard) else countBlack(tmpBoard))

        if (newStreak - streak > 0) {
            val takeMove = takeSimple(tmpBoard, white)
            tmpBoard[takeMove.getY()][takeMove.getX()] = Stone.EMPTY
        }
        val tmp = moveMiniMax(tmpBoard, !white, depth - 1)
        if (currentBest == null || (tmp.key < currentBest.key && white) || (tmp.key > currentBest.key && !white))
            return AbstractMap.SimpleEntry(tmp.key, Move(Position(y, x), Position(y + i, x + j)))
        return currentBest
    }

    private fun takeSimple(matrix: Array<Array<Stone>>, white: Boolean): Position {
        var res: AbstractMap.SimpleEntry<Int, Position>? = null
        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                if (matrix[y][x].isBlack() && white || matrix[y][x].isWhite() && !white) {
                    val tmpBoard = matrix.copy()
                    tmpBoard[y][x] = Stone.EMPTY
                    if (countWhite(tmpBoard) - countWhite(matrix) < 0) continue
                    if (countBlack(tmpBoard) - countBlack(matrix) < 0) continue

                    val score = countWhite(tmpBoard) - countBlack(tmpBoard)
                    if (res == null || (score < res.key && white) || (score > res.key && !white))
                        res = AbstractMap.SimpleEntry(score, Position(y, x))
                }
            }
        }
        return res!!.value
    }

    private fun putMiniMax(
        matrix: Array<Array<Stone>>,
        white: Boolean,
        depth: Int
    ): AbstractMap.SimpleEntry<Int, Position> {
        if (depth == 0) {
            val score = countWhite(matrix, 2) - countBlack(matrix, 2)
            return AbstractMap.SimpleEntry(-score, Position(-1, -1))
        }

        var res: AbstractMap.SimpleEntry<Int, Position>? = null
        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                if (matrix[y][x].isEmpty()) {
                    val tmpBoard = matrix.copy()
                    tmpBoard[y][x] = if (white) Stone.WHITE else Stone.BLACK
                    if (countBlack(tmpBoard) > 0 || countWhite(tmpBoard) > 0) continue

                    val tmp = putMiniMax(tmpBoard, !white, depth - 1)
                    if (res == null || (tmp.key < res.key && white) || (tmp.key > res.key && !white))
                        res = AbstractMap.SimpleEntry(tmp.key, Position(y, x))
                }
            }
        }
        if (res == null) return AbstractMap.SimpleEntry(0, Position(-1, -1))
        return res
    }

    private fun countWhite(matrix: Array<Array<Stone>>, length: Int = C.ROW_LENGTH): Int {
        var count = 0
        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                var down = true
                var right = true
                for (i in 0 until length) {
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
                var down = true
                var right = true
                for (i in 0 until length) {
                    right = right && (x + i < matrix[y].size && matrix[y][x + i].isBlack())
                    down = down && (y + i < matrix.size && matrix[y + i][x].isBlack())
                }
                if (down) count++
                if (right) count++
            }
        }
        return count
    }

    private fun convertBoard(
        matrix: Array<Array<T>>,
        height: Int,
        width: Int
    ): Array<Array<Stone>> {
        val newMatrix: Array<Array<Stone>> = Array(height) { Array(width) { Stone.EMPTY } }
        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                if (!matrix[y][x].isEmpty())
                    newMatrix[y][x] = if (matrix[y][x].isWhite()) Stone.WHITE else Stone.BLACK
            }
        }
        return newMatrix
    }

    override fun getName(): String {
        return "Minimax AI"
    }
}