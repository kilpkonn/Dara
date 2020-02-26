package ee.taltech.iti0213.dara.game.board

import java.io.Serializable

interface IMove<T: IPosition>: Serializable {
    fun from(): T
    fun to(): T
}