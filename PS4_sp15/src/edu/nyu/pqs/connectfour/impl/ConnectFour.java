package edu.nyu.pqs.connectfour.impl;

import java.util.LinkedList;
import java.util.List;

import edu.nyu.pqs.connectfour.ai.AIFactory;
import edu.nyu.pqs.connectfour.ai.AIFactory.AIType;
import edu.nyu.pqs.connectfour.api.IConnectFourListener;
import edu.nyu.pqs.connectfour.api.IAIPlayer;
import edu.nyu.pqs.connectfour.api.IGameBoard;


public class ConnectFour {
  private List<IConnectFourListener> listeners =
      new LinkedList<IConnectFourListener>();
  
  private final static int PLAYER_ONE = 0;
  private final static int PLAYER_TWO = 1;
  private final static int NUMBER_OF_PLAYERS = 2;
  
  private int activePlayer;
  
  private GameType gameType;
  private GameBoard board;
  
  // will be lazily created if needed
  private IAIPlayer computerPlayer;
  
  // default AI
  private AIFactory.AIType aiType = AIFactory.AIType.SIMPLE;

  /**
   * This Builder class is used to initialize and generate the ConnectFour object.
   * 
   * @author cpp270
   *
   */
  public static class Builder {
    private GameType gameType = null;
    private AIFactory.AIType aiType = null;

    /**
     * A constructor method used to initialize the required value game type.
     * 
     * @param val specifies the type of opponents that will play this game
     * @throws NullPointerException if <code>val</code> is null
     * @see NullPointerException
     */
    public Builder(GameType val) {
      if (val == null) {
        throw new NullPointerException("GameType must not be null");
      }
      gameType = val;      
    }
    
    /**
     * A setter method used to initialize the the type of AI that will be used in this game.
     * 
     * @param val the AI type
     * @return the builder object
     * @throws NullPointerException if  <code>val</code> is null
     * @see NullPointerException
     */
    public Builder aiType(AIFactory.AIType val) {
      if (val == null) {
        throw new NullPointerException("AIType must not be null");
      }
      aiType = val;
      return this;
    }
    
    /**
     * This method creates and returns a new PostalAddress object.
     * 
     * @return new PostalAddress object
     */
    public ConnectFour build() {
      return new ConnectFour(this);
    }
  }
  
  private ConnectFour(Builder builder) {
    board = GameBoard.getInstance();
    
    this.gameType = builder.gameType;
    this.aiType = builder.aiType;
    
    computerPlayer = null;
    
    resetGame();
    
    doComputerMoveHelper();  
  }
  
  public ConnectFour(GameType type) {
    board = GameBoard.getInstance();
    
    this.gameType = type;
    
    computerPlayer = null;
    
    resetGame();
    
    doComputerMoveHelper();
  }
  
  private void resetGame() {
    activePlayer = PLAYER_ONE;
    board.resetBoard();
    
    initializeAIPlayer(gameType, activePlayer);
  }
  
  private boolean isActivePlayerComputer(GameType type, int activePlayer) {
    //int player = getActivePlayerTypeHelper(type, activePlayer);
    
    return (type == GameType.HUMAN_COMPUTER && activePlayer == PLAYER_TWO) ||
        (type == GameType.COMPUTER_HUMAN && activePlayer == PLAYER_ONE);
  }
  
  private void initializeAIPlayer(GameType type, int player) {
    if ((type == GameType.HUMAN_COMPUTER || type == GameType.COMPUTER_HUMAN)) {
      if (computerPlayer == null) {
        computerPlayer = AIFactory.getAIPlayer(aiType);
      }
      
      if (computerPlayer == null) {
        throw new NullPointerException("computer player is null");
      } else {
        computerPlayer.setBoard(board);
        CellValue mark = null;
        
        if (player == PLAYER_ONE) {
          mark = CellValue.PLAYER_ONE;
        } else if (player == PLAYER_TWO) {
          mark = CellValue.PLAYER_TWO;
        }
        
        computerPlayer.setPlayer(mark, player);
      }
    }
  }
  
  private boolean doComputerMove(IAIPlayer computerPlayer) {
    int column = computerPlayer.getNextMove();
    return board.setValueToFirstAvailableRow(column,getCellValueByPlayerTypeHelper(activePlayer));
  }
  
  private boolean doComputerMoveHelper() {
    boolean result = false;
    
    if (isActivePlayerComputer(gameType, activePlayer)) {
      result = doComputerMove(computerPlayer);
      
      // set turn to the next player which should be human
      activePlayer++;
      activePlayer = activePlayer % NUMBER_OF_PLAYERS;
    }
    
    return result;
  }
    
  private CellValue getCellValueByPlayerTypeHelper(int player) {
    if (player == PLAYER_ONE) {
      return CellValue.PLAYER_ONE;
    } else {
      return CellValue.PLAYER_TWO;
    }
  }
  
  /**
   * Returns the game board with all the current checker locations.
   * 
   * @return game board object
   */
  public IGameBoard getBoard() {
    return board;
  }
  
  /**
   * Handles start of a new game.
   * 
   * @param type the type of game to be played
   * 
   */
  public void newGame(GameType type) {
    gameType = type;
        
    resetGame();
    
    fireNewGameEvent(type);
    
    if (doComputerMoveHelper()) {
      fireGameUpdateEvent();
    }
  }
  
  /**
   * Returns the type of game being played currently.
   * 
   * @return the current game type
   */
  public GameType getGameType() {
    return gameType;
  }
  
  /**
   * Returns the type of AI being used in this game.
   * 
   * @return the current AI type
   */
  public AIType getAIType() {
    return aiType;
  }
 
  /**
   * Handles a dropped checker in connect four at a specified column location and selects the
   * next player.
   * 
   * @param column the index column value for the dropped checker
   * @throws IllegalArgumentException if <code>column</code> is out of game bounds
   */
  public void dropChecker(int column) {
    if (column < 0 && column >= GameBoard.COLUMNS) {
      throw new IllegalArgumentException("the column specified is invalid: " + column);
    }
        
    board.setValueToFirstAvailableRow(column, getCellValueByPlayerTypeHelper(activePlayer));
    
    // set turn to the next player
    activePlayer++;
    activePlayer = activePlayer % NUMBER_OF_PLAYERS;
        
    doComputerMoveHelper();
    
    GameResult result = board.getWinner();
   
    // check if we have a winner and notify the listeners
    if (result != GameResult.NONE) {
      fireEndGameEvent(result);
    } else {      
      fireGameUpdateEvent();      
    }
   }
  
  private void fireNewGameEvent(GameType type) {
    for (IConnectFourListener listener: listeners) {
      listener.gameStart(type);
    }
  }
  
  private void fireGameUpdateEvent() {
    for (IConnectFourListener listener: listeners) {
      listener.gameUpdate(this.getBoard());
    }    
  }
  
  private void fireEndGameEvent(GameResult result) {
    for (IConnectFourListener listener: listeners) {
      listener.gameEnd(result);
    }    
  }
  
  /**
   * Registers a listener for game update notifications.
   * 
   * @param listener a listener interface object to be added
   * @return true if the listener was successfully added or false otherwise
   * @throws NullPointerException if <code>listener</code> is null
   */
  public boolean addListener(IConnectFourListener listener) {
    if (listener == null) {
      throw new NullPointerException("listener is null");
    }
    
    return listeners.add(listener);
  }
  
  /**
   * Removes a listener from game update notifications.
   * 
   * @param listener a listener interface object to be removed.
   * @return true if the listener was successfully removed or false otherwise
   * @throws NullPointerException if <code>listener</code> is null
   */
  public boolean removeListener(IConnectFourListener listener) {
    if (listener == null) {
      throw new NullPointerException("listener is null");
    }
    
    return listeners.remove(listener);
  }
}
