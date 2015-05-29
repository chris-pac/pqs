package edu.nyu.pqs.connectfour.api;

import edu.nyu.pqs.connectfour.impl.GameResult;
import edu.nyu.pqs.connectfour.impl.GameType;

/**
 * A listener object that receives game status updates.
 * 
 * @author cpp270
 *
 */
public interface IConnectFourListener {
  
  /**
   * Notifies the listener that a new game has started.
   *  
   * @param type the type of game that has started
   */
  public void gameStart(GameType type);
  
  /**
   * Notifies the listener that there has been a change on the game board.
   * @param updatedBoard changed game board; null value is permitted
   * 
   */
  public void gameUpdate(IGameBoard updatedBoard);
  
  /**
   * Notifies the that a game has ended.
   * 
   * @param result indicates the results of the game (e.g. win, lose , tie)
   */
  public void gameEnd(GameResult result);  
}
