package ee.taltech.iti0213.dara.player.strategy

import ee.taltech.iti0213.dara.board.IBoard
import ee.taltech.iti0213.dara.board.IMove
import ee.taltech.iti0213.dara.board.IPosition
import ee.taltech.iti0213.dara.board.IStone

class HumanStrategy<T: IStone, U: IPosition>: IStrategy<T, U> {
    private var clickedLocation: U? = null
    override fun onUserClickedLocation(location: U) {
        clickedLocation = location
    }

    override fun getPutMove(board: IBoard<T, U>): U {
        clickedLocation = null
        while (clickedLocation == null) Thread.sleep(10)
        return clickedLocation as U
    }

    override fun getMove(board: IBoard<T, U>): IMove<U> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTakeMove(board: IBoard<T, U>): U {
        clickedLocation = null
        while (clickedLocation == null) Thread.sleep(10)
        return clickedLocation as U
    }

    override fun getName(): String {
        return "Human"
    }
}