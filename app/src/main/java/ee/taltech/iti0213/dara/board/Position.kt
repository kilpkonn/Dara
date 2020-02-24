package ee.taltech.iti0213.dara.board

class Position(private val y: Int, private val x: Int) : IPosition {

    override fun getX(): Int {
        return x
    }

    override fun getY(): Int {
        return y
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (y != other.y) return false
        if (x != other.x) return false

        return true
    }

    override fun hashCode(): Int {
        var result = y
        result = 31 * result + x
        return result
    }

    override fun toString(): String {
        return "($y, $x)"
    }


}