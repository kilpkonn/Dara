package ee.taltech.iti0213.dara.player.statistics

class Statistics {
    private var moves: Int = 0
    private var setupThinkingTime: Float = 0f
    private var totalThinkingTime: Float = 0f

    fun addSetupMove(time: Float) {
        setupThinkingTime += time
    }

    fun addMove(time: Float) {
        moves++
        totalThinkingTime += time
    }

    fun getSetupThinkingTime(): Float {
        return setupThinkingTime
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