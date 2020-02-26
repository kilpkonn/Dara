package ee.taltech.iti0213.dara.game.board

import ee.taltech.iti0213.dara.game.board.enums.GameState
import ee.taltech.iti0213.dara.game.constants.C
import java.io.Serializable


class SimpleBoard<U : IPosition>(private val height: Int, private val width: Int) :
    IBoard<Stone, U> {
    private var matrix: Array<Array<Stone>> = Array(height) { Array(width) { Stone.EMPTY } }
    private var isWhiteToMove: Boolean = true
    private var gameState: GameState = GameState.SETUP
    private var stonesPerPlayer: Int = C.STONES_PER_PLAYER

    companion object {
        fun Array<Array<Stone>>.copy() = Array(size) { get(it).clone() }
    }

    override fun getBoardMatrix(): Array<Array<Stone>> {
        return matrix.copy()
    }

    override fun putStone(to: U): Boolean {
        if (matrix[to.getY()][to.getX()] != Stone.EMPTY) return false
        if (gotRow(
                to.getY(),
                to.getX(),
                if (isWhiteToMove) Stone.WHITE else Stone.BLACK
            )
        ) return false
        matrix[to.getY()][to.getX()] = if (isWhiteToMove) Stone.WHITE else Stone.BLACK
        isWhiteToMove = !isWhiteToMove
        if (isWhiteToMove) {
            stonesPerPlayer--
            if (stonesPerPlayer <= 0) gameState = GameState.PLAYING
        }
        return true
    }

    override fun makeMove(move: IMove<U>): Int {
        if (matrix[move.from().getY()][move.from().getX()] != (if (isWhiteToMove) Stone.WHITE else Stone.BLACK)) return -1
        if (matrix[move.to().getY()][move.to().getX()] != Stone.EMPTY) return -1
        matrix[move.from().getY()][move.from().getX()] = Stone.EMPTY
        matrix[move.to().getY()][move.to().getX()] = if (isWhiteToMove) Stone.WHITE else Stone.BLACK
        isWhiteToMove = !isWhiteToMove
        return if (gotRow(move.to().getY(), move.to().getX())) 1 else 0
    }

    override fun takeStone(from: U): Boolean {
        if (matrix[from.getY()][from.getX()] == Stone.EMPTY) return false
        if (matrix[from.getY()][from.getX()] == if (!isWhiteToMove) Stone.WHITE else Stone.BLACK) return false
        matrix[from.getY()][from.getX()] = Stone.EMPTY
        evaluateWin()
        return true
    }

    override fun getHeight(): Int {
        return height
    }

    override fun getWidth(): Int {
        return width
    }

    override fun getGameState(): GameState {
        return gameState
    }

    private fun evaluateWin() {
        var whiteCount = 0
        var blackCount = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                when (matrix[y][x]) {
                    Stone.BLACK -> blackCount++
                    Stone.WHITE -> whiteCount++
                }
            }
        }
        gameState =
            if (whiteCount < 3) GameState.BLACK_WON else if (blackCount < 3) GameState.WHITE_WON else gameState
    }

    private fun gotRow(y: Int, x: Int, stoneToPut: Stone? = null): Boolean {
        var upward = true
        var downward = true
        var left = true
        var right = true
        val stone = stoneToPut ?: matrix[y][x]
        for (i in 1 until 3) {
            upward = if (y - i >= 0 && matrix[y - i][x] == stone) upward else false
            downward = if (y + i < height && matrix[y + i][x] == stone) downward else false
            left = if (x - i >= 0 && matrix[y][x - i] == stone) left else false
            right = if (x + i < width && matrix[y][x + i] == stone) right else false
        }
        val centerHorizontal =
            0 < x && x + 1 < width && matrix[y][x - 1] == stone && matrix[y][x + 1] == stone
        val centerVertical =
            0 < y && y + 1 < height && matrix[y - 1][x] == stone && matrix[y + 1][x] == stone
        return upward || downward || left || right || centerHorizontal || centerVertical
    }
}