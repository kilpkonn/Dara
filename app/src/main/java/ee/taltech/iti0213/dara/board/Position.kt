package ee.taltech.iti0213.dara.board

class Position(private val x: Int, private val y: Int) : IPosition {

    override fun getX(): Int {
        return x
    }

    override fun getY(): Int {
        return y
    }
}