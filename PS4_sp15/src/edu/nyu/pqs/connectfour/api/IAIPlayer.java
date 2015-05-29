package edu.nyu.pqs.connectfour.api;

import edu.nyu.pqs.connectfour.impl.CellValue;
/**
 * An object that can be used in a connect four game as a computer opponent.
 * AI Player objects are created in the AIFactory.
 * 
 * @author cpp270
 *
 */
public interface IAIPlayer {
  
  /**
   * Sets the board that this AI will analyze for next possible move
   * 
   * @param board reference to the board used in a connect four game
   * @throws NullPointerException if the <code>board</code> is null
   */
  public void setBoard(IGameBoard board);
  
  /**
   * Returns the board that this AI is using.
   * 
   * @return the reference to the game board object or null if it has not been set
   */
  public IGameBoard getBoard();
  
  /**
   * Associates a player with a mark on the board.
   * 
   * @param mark player one or player two mark that they control.
   * @param player the player that controls that value or mark
   * @throws NullPointerException if the <code>mark</code> is null
   */
  public void setPlayer(CellValue mark, int player);
  
  /**
   * Returns the player associated with this AI
   * 
   * @return the integer value of the player
   */
  public int getPlayer();
  
  /**
   * Returns the column where this AI wants to drop a checker
   * 
   * @return the column coordinates of the next move or negative one if a move has not been found
   * @throws NullPointerException if the game board or player mark is null
   * 
   */
  public int getNextMove();
}
