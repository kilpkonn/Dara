package ee.taltech.iti0213.dara.player.statistics

import java.io.Serializable
import kotlin.math.roundToInt

class Statistics : Serializable {
    private var moves: Int = 0
    private var setupThinkingTime: Float = 0f
    private var totalThinkingTime: Float = 0f
    private var takeThinkingTime: Float = 0f

    fun addSetupMove(time: Float) {
        setupThinkingTime += time
    }

    fun addMove(time: Float) {
        moves++
        totalThinkingTime += time
    }

    fun addTakeMove(time: Float) {
        takeThinkingTime += time
    }

    fun getSetupThinkingTime(): Float {
        return (setupThinkingTime / 10f).roundToInt() / 100f
    }

    fun getTotalMove(): Int {
        return moves
    }

    fun getTakeThinkingTime(): Float {
        return (takeThinkingTime / 10f).roundToInt() / 100f
    }

    fun getTotalThinkingTime(): Float {
        return (totalThinkingTime / 10f).roundToInt() / 100f
    }

    fun getTimePerMove(): Float {
        if (moves == 0) return 0f
        return (totalThinkingTime / moves / 10f).roundToInt() / 100f
    }
}