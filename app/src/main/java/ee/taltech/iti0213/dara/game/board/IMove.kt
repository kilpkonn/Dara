package ee.taltech.iti0213.dara.game.board

interface IMove<T: IPosition> {
    fun from(): T
    fun to(): T
}