package ee.taltech.iti0213.dara.game.board

import java.io.Serializable

interface IStone: Serializable {

    fun isEmpty(): Boolean

    fun isWhite(): Boolean

    fun isBlack(): Boolean
}