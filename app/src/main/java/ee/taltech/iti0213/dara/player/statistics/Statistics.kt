package ee.taltech.iti0213.dara.player.statistics

class Statistics {
    private var moves: Int = 0
    private var totalThinkingTime: Float = 0f

    fun addMove(time: Float) {
        moves++
        totalThinkingTime += time
    }

    fun getTotalMove(): Int {
        return moves
    }

    fun getTotalThinkingTime(): Float {
        return totalThinkingTime
    }

    fun getTimePerMove(): Float {
        return totalThinkingTime / moves
    }
}