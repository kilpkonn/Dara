package ee.taltech.iti0213.dara.player.strategy

import ee.taltech.iti0213.dara.board.IPosition
import ee.taltech.iti0213.dara.board.IStone

abstract class BaseStrategy<T: IStone>(protected val isWhite: Boolean): IStrategy<T, IPosition> {
}