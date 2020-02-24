package ee.taltech.iti0213.dara.game.board

class Move<T: IPosition>(private val from: T, private val to: T): IMove<T> {

    override fun from(): T {
        return from
    }

    override fun to(): T {
        return to
    }

    override fun toString(): String {
        return "Move $from -> $to)"
    }


}