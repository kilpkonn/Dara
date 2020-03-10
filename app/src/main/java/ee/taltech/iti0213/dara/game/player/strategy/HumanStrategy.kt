package ee.taltech.iti0213.dara.game.player.strategy

import ee.taltech.iti0213.dara.game.board.*
import kotlinx.coroutines.delay

class HumanStrategy<T : IStone>(isWhite: Boolean) : BaseStrategy<T>(isWhite) {
    @Transient private var clickedLocation: Position? = null
    override fun onUserClickedLocation(location: Position) {
        clickedLocation = location
    }

    override suspend fun getPutMove(board: IBoard<T, Position>): Position {
        clickedLocation = null
        return waitForClickedLocation()
    }

    override suspend fun getMove(board: IBoard<T, Position>): IMove<Position> {
        clickedLocation = null
        val matrix = board.getBoardMatrix()
        var fromLocation: Position
        do {
            fromLocation = waitForClickedLocation()
        } while (matrix[fromLocation.getY()][fromLocation.getX()] == Stone.WHITE && !isWhite
            || matrix[fromLocation.getY()][fromLocation.getX()] == Stone.BLACK && isWhite)

        clickedLocation = null
        val location = waitForClickedLocation()

        if (location == fromLocation) return getMove(board) // Deselect current
        return Move(fromLocation, location)
    }

    override suspend fun getTakeMove(board: IBoard<T, Position>): Position {
        clickedLocation = null
        return waitForClickedLocation()
    }

    override fun getName(): String {
        return "Human"
    }

    @Synchronized
    private suspend fun waitForClickedLocation(): Position {
        while (clickedLocation == null) delay(10)
        return clickedLocation as Position
    }
}