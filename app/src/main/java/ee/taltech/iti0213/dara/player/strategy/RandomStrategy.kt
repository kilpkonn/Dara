package ee.taltech.iti0213.dara.player.strategy

import ee.taltech.iti0213.dara.board.*

class RandomStrategy<T : IStone>(isWhite: Boolean) : BaseStrategy<T>(isWhite) {

    override fun getPutMove(board: IBoard<T, IPosition>): IPosition {
        val possibleMoves: MutableList<IPosition> = arrayListOf()
        val matrix = board.getBoardMatrix()
        for (y in 0 until board.getHeight()) {
            for (x in 0 until board.getWidth()) {
                if (matrix[y][x].isEmpty())
                    possibleMoves.add(Position(y, x))
            }
        }
        return possibleMoves.random()
    }

    override fun getMove(board: IBoard<T, IPosition>): IMove<IPosition> {
        val possibleFromPositions: MutableList<IPosition> = arrayListOf()
        val matrix = board.getBoardMatrix()
        for (y in 0 until board.getHeight()) {
            for (x in 0 until board.getWidth()) {
                if (matrix[y][x].isWhite() && isWhite || matrix[y][x].isBlack() && !isWhite)
                    possibleFromPositions.add(Position(y, x))
            }
        }
        val possibleToPositions: MutableList<IPosition> = arrayListOf()
        for (y in 0 until board.getHeight()) {
            for (x in 0 until board.getWidth()) {
                if (matrix[y][x].isEmpty())
                    possibleToPositions.add(Position(y, x))
            }
        }
        return Move(possibleFromPositions.random(), possibleToPositions.random())
    }

    override fun getTakeMove(board: IBoard<T, IPosition>): IPosition {
        val possibleMoves: MutableList<IPosition> = arrayListOf()
        val matrix = board.getBoardMatrix()
        for (y in 0 until board.getHeight()) {
            for (x in 0 until board.getWidth()) {
                if (matrix[y][x].isWhite() && isWhite || matrix[y][x].isBlack() && !isWhite)
                    possibleMoves.add(Position(y, x))
            }
        }
        return possibleMoves.random()
    }

    override fun getName(): String {
        return "Simple Randomness"
    }
}