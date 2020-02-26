package ee.taltech.iti0213.dara.game.player.strategy

import ee.taltech.iti0213.dara.game.board.*

class MinimaxStrategy<T : IStone>(isWhite: Boolean) : BaseStrategy<T>(isWhite) {
    override fun onUserClickedLocation(location: Position) {
    }

    override suspend fun getPutMove(board: IBoard<T, Position>): Position {
        val possibleMoves: MutableList<Position> = arrayListOf()
        val matrix = board.getBoardMatrix()
        for (y in 0 until board.getHeight()) {
            for (x in 0 until board.getWidth()) {
                if (matrix[y][x].isEmpty())
                    possibleMoves.add(Position(y, x))
            }
        }
        return possibleMoves.random() // TODO: Weighting
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

    override fun getName(): String {
        return "Minimax AI"
    }
}