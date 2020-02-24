package ee.taltech.iti0213.dara.game.player.strategy

import ee.taltech.iti0213.dara.game.board.IStone
import ee.taltech.iti0213.dara.game.board.Position

abstract class BaseStrategy<T: IStone>(protected val isWhite: Boolean): IStrategy<T, Position>