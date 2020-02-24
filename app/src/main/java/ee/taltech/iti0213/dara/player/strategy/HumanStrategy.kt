package ee.taltech.iti0213.dara.player.strategy

import ee.taltech.iti0213.dara.board.*
import kotlinx.coroutines.delay

class HumanStrategy<T : IStone>(isWhite: Boolean) : BaseStrategy<T>(isWhite) {
    private var clickedLocation: Position? = null
    override fun onUserClickedLocation(location: Position) {
        clickedLocation = location
    }

    override suspend fun getPutMove(board: IBoard<T, Position>): Position {
        clickedLocation = null
        while (clickedLocation == null) delay(10)
        return clickedLocation as Position
    }

    override suspend fun getMove(board: IBoard<T, Position>): IMove<Position> {
        clickedLocation = null
        val matrix = board.getBoardMatrix()
        while (clickedLocation == null
            || matrix[clickedLocation!!.getY()][clickedLocation!!.getX()] == Stone.WHITE && !isWhite
            || matrix[clickedLocation!!.getY()][clickedLocation!!.getX()] == Stone.BLACK && isWhite)
            delay(10)

        val fromLocation = clickedLocation
        clickedLocation = null
        while (clickedLocation == null) delay(10)

        if (clickedLocation == fromLocation) return getMove(board) // Deselect current
        return Move(fromLocation as Position, clickedLocation as Position)
    }

    override suspend fun getTakeMove(board: IBoard<T, Position>): Position {
        clickedLocation = null
        while (clickedLocation == null) delay(10)
        return clickedLocation as Position
    }

    override fun getName(): String {
        return "Human"
    }
}