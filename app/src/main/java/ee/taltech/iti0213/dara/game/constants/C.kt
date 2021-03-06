package ee.taltech.iti0213.dara.game.constants

class C {
    companion object {
        const val PLAYER1_STRATEGY_KEY: String = "player1_strategy"
        const val PLAYER2_STRATEGY_KEY: String = "player2_strategy"

        const val BOARD_HEIGHT: Int = 6
        const val BOARD_WIDTH: Int = 5
        const val STONES_PER_PLAYER: Int = 12
        const val ROW_LENGTH: Int = 3

        const val GAME_SESSION_KEY: String = "game_session"

        const val GAME_REFRESH_DELAY: Long = 33L
        const val BANNER_LIFE_LENGTH: Long = 2 * 1000L
        const val BANNER_MIN_HEIGHT: Int = 200

        const val GAME_DELAY_AFTER_SETUP: Long = 1000L
    }
}