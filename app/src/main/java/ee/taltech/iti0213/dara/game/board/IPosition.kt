package ee.taltech.iti0213.dara.game.board

import java.io.Serializable

interface IPosition: Serializable {

    fun getX(): Int

    fun getY(): Int
}