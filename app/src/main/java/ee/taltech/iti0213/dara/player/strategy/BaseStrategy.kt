package ee.taltech.iti0213.dara.player.strategy

import ee.taltech.iti0213.dara.board.IStone
import ee.taltech.iti0213.dara.board.Position

abstract class BaseStrategy<T: IStone>(protected val isWhite: Boolean): IStrategy<T, Position>