package ee.taltech.iti0213.dara.board

import ee.taltech.iti0213.dara.board.enums.GameState

interface IBoard <T : IStone, U : IPosition>{

    /**
     * Get the matrix of the board
     * @return 2D Array of stones
     */
    fun getBoardMatrix(): Array<Array<T>>
    /**
     * Place stone on startup
     * @param to Location where stone is placed
     * @return [Boolean] Whether the to was made or no
     */
    fun putStone(to: U): Boolean

    /**
     * Make move
     * @param move move with from and to locations
     * @return [Int] -1 if move was invalid, 1 if row of 3 was achieved, otherwise 0
     */
    fun makeMove(move: Move<U>): Int

    /**
     * Take enemies stone
     * @param from Location of the stone
     * @return [Boolean] Whether the move was successful or no
     */
    fun takeStone(from: U): Boolean

    /**
     * Get the height of the board
     * @return [Int] height
     */
    fun getHeight(): Int

    /**
     * Get the width of the board
     * @return [Int] width
     */
    fun getWidth(): Int

    /**
     * Get the game state
     * @return [GameState] SETUP, PLAYING, or XXX_WON
     */
    fun getGameState(): GameState
}