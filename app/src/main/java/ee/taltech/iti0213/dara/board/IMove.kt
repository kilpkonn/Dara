package ee.taltech.iti0213.dara.board

interface IMove<T: IPosition> {
    fun from(): T
    fun to(): T
}