package edu.nyu.pqs.connectfour.ai;

import edu.nyu.pqs.connectfour.api.IAIPlayer;
import edu.nyu.pqs.connectfour.api.IGameBoard;
import edu.nyu.pqs.connectfour.impl.CellValue;

/**
 * The base class for all the AI player implementations. The main method that needs to be 
 * implemented in derived is getNextMove
 * 
 * @author cpp270
 *
 */
abstract class AIPlayer implements IAIPlayer {
  protected IGameBoard gameBoard = null;
  protected CellValue myBoardMark = null;
  protected int myId;
  
  @Override
  public void setBoard(IGameBoard board) {
    if (board == null) {
      throw new NullPointerException("game board can not be null");
    }
    gameBoard = board;
  }

  @Override
  public IGameBoard getBoard() {
    return gameBoard;
  }

  @Override
  public void setPlayer(CellValue mark, int player) {
    if (mark == null) {
      throw new NullPointerException("AI board mark is null");
    }
    
    myBoardMark = mark;
    myId = player;
  }

  @Override
  public int getPlayer() {
    return myId;
  }

}
