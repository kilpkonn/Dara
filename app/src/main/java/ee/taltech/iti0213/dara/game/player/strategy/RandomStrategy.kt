package ee.taltech.iti0213.dara.game.player.strategy

import ee.taltech.iti0213.dara.game.board.*

class RandomStrategy<T : IStone>(isWhite: Boolean) : BaseStrategy<T>(isWhite) {
    override fun onUserClickedLocation(location: Position) { }

    override suspend fun getPutMove(board: IBoard<T, Position>): Position {
        val possibleMoves: MutableList<Position> = arrayListOf()
        val matrix = board.getBoardMatrix()
        for (y in 0 until board.getHeight()) {
            for (x in 0 until board.getWidth()) {
                if (matrix[y][x].isEmpty())
                    possibleMoves.add(Position(y, x))
            }
        }
        return possibleMoves.random()
    }

    override suspend fun getMove(board: IBoard<T, Position>): IMove<Position> {
        val possibleMoves: MutableList<Move<Position>> = arrayListOf()
        val matrix = board.getBoardMatrix()
        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                if (matrix[y][x].isWhite() && isWhite || matrix[y][x].isBlack() && !isWhite)
                    for (i in -1..1) {
                        for (j in -1..1) {
                            if (y + i >= 0
                                && x + j >= 0
                                && y + i < matrix.size
                                && x + j < matrix[y].size
                                && matrix[y + i][x + j].isEmpty()
                                && (i == 0 || j == 0)
                            ) {
                                possibleMoves.add(Move(Position(y, x), Position(y + i, x + j)))
                            }
                        }
                    }
            }
        }
        return possibleMoves.random()
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
        return possibleMoves.random()
    }

    override fun getName(): String {
        return "Simple Randomness"
    }
}