package edu.nyu.pqs.connectfour.impl;

import java.util.Iterator;

import edu.nyu.pqs.connectfour.api.IGameBoard;

/**
 * The game board a singleton class with done with static factory.
 * It is always initialized since it is indispensable to the game.
 * 
 * @author chris
 *
 */
public class GameBoard implements IGameBoard {
  private static final GameBoard INSTANCE = new GameBoard();
  
  public static final int ROWS = 6;
  public static final int COLUMNS = 7;
  
  private final CellValue[][] board;
  private int[] firstAvailableRow;
  private final Cell[] winningCells;

  private GameBoard() {
    board = new CellValue[ROWS][COLUMNS];
    firstAvailableRow = new int[COLUMNS];
    winningCells = new Cell[4];
    
    resetBoard();
  }
  
  /**
   * Returns an instance of a GameBoard. The function will not return a null value.
   * 
   * @return GameBoard instance
   */
  public static GameBoard getInstance() { 
    return INSTANCE; 
  }
  
  /**
   * Resets the board to initial values.
   * 
   */
  public void resetBoard() {
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLUMNS; col++) {
          board[row][col] = CellValue.EMPTY;
      }
    }
    
    for (int col = 0; col < COLUMNS; col++) {
      firstAvailableRow[col] = ROWS - 1;
    }
  }

  /**
   * Marks the game board cell with the player's value at a specified column location. The row
   * is calculated automatically based on previous moves. False is returned if all rows are filled
   * at the <code>col</code>.
   * 
   * @param col the column where the value is to be set
   * @param value the value to set
   * @return true if there was an available row at the <code>col</code> or false otherwise
   * @throws IllegalArgumentException if <code>col</code> is less than zero or greater than
   * max number of columns - 1
   */
  @Override
  public boolean setValueToFirstAvailableRow(int col, CellValue value) {
    if (col < 0 || col >= COLUMNS) {
      throw new IllegalArgumentException("the column is outside of game bounds: " + col);
    }

    int row = firstAvailableRow[col];
    if (row < 0) {
      return false;
    }
    
    markBoard(row, col, value);
    
    --firstAvailableRow[col];
    
    return true;
  }
  
  /**
   * Sets a new value to the board.
   * 
   * @param row the row value
   * @param col the column value
   * @param value the new value 
   * @throws IllegalArgumentException if the <code>row</code> or <code>col</code> are out of
   * game bounds.
   */
  private void markBoard(int row, int col, CellValue value) {
    if (row < 0 || row >= ROWS || col < 0 || col >= COLUMNS) {
      throw new IllegalArgumentException("row: "+row+" or col: "+col+" are out of bounds.");
    }
    
    board[row][col] = value;
  }
  
  /**
   * Checks if there is a winner and if there is one then the board cell values are updated to
   * reflect the win.
   * 
   * @return the current board status 
   */
  public GameResult getWinner() {
    GameResult result = checkForWinner();
    
    // if there is a winner we need to update the board
    if (result == GameResult.PLAYER_ONE_WINS || result == GameResult.PLAYER_TWO_WINS) {
      for (int val = 0; val < 4; val++) {
        board[winningCells[val].getRow()][winningCells[val].getColumn()] = 
            winningCells[val].getValue();
      }
    } else if (result == GameResult.NONE) {
      boolean isColAvailable = false;
      for (int val = 0; val < COLUMNS && !isColAvailable; val++) {
        isColAvailable = isColumnAvailable(val);
      }
      
      if (!isColAvailable) {
        return GameResult.STALEMATE;
      }
    }
    
    return result;
  }
  
  /*
   * Check for a winner across horizontal rows, vertical columns, minor and major diagonal.
   * 
   * The implementation of this algorithm closely follows one implemented by Brian Borowski
   */
  private GameResult checkForWinner() {
    CellValue cell = CellValue.EMPTY;
    
    // Check horizontally
    for (int row = 0; row < ROWS; row++) {
      for (int col = 3; col < COLUMNS; col++) {
        int playerOneCount = 0; 
        int playerTwoCount = 0;
          
        for (int val = 0; val < 4; val++) {
          cell = board[row][col - val];
          if (cell == CellValue.PLAYER_TWO) {
            winningCells[val] = new Cell(row, col - val, CellValue.PLAYER_TWO_WINNER);
            playerTwoCount++;
          } else if (cell == CellValue.PLAYER_ONE) {
            winningCells[val] = new Cell(row, col - val, CellValue.PLAYER_ONE_WINNER);
            playerOneCount++;
          }
        }
        if (playerTwoCount == 4) {
          return GameResult.PLAYER_TWO_WINS;
        } else if (playerOneCount == 4) {
          return GameResult.PLAYER_ONE_WINS;
        }
      }
    }
    // Check vertically
    for (int col = 0; col < COLUMNS; col++) {
      for (int row = 3; row < ROWS; row++) {
        int playerOneCount = 0; 
        int playerTwoCount = 0;
        for (int val = 0; val < 4; val++) {
          cell = board[row - val][col];
          if (cell == CellValue.PLAYER_TWO) {
            winningCells[val] = new Cell(row - val, col, CellValue.PLAYER_TWO_WINNER);
            playerTwoCount++;
          } else if (cell == CellValue.PLAYER_ONE) {
            winningCells[val] = new Cell(row - val, col, CellValue.PLAYER_ONE_WINNER);
            playerOneCount++;
          }
        }
        if (playerTwoCount == 4) {
          return GameResult.PLAYER_TWO_WINS;
        } else if (playerOneCount == 4) {
          return GameResult.PLAYER_ONE_WINS;
        }
      }
    }
    // Check major diagonal
    for (int row = ROWS - 4; row >= 0; row--) {
      for (int col = COLUMNS - 4; col >= 0; col--) {
        int playerOneCount = 0; 
        int playerTwoCount = 0;
        for (int val = 3; val >= 0; val--) {
          cell = board[row + val][col + val];
          if (cell == CellValue.PLAYER_TWO) {
            winningCells[val] = new Cell(row + val, col + val, CellValue.PLAYER_TWO_WINNER);
            playerTwoCount++;
          } else if (cell == CellValue.PLAYER_ONE) {
            winningCells[val] = new Cell(row + val, col + val, CellValue.PLAYER_ONE_WINNER);
            playerOneCount++;
          }
        }
        if (playerTwoCount == 4) {
          return GameResult.PLAYER_TWO_WINS;
        } else if (playerOneCount == 4) {
          return GameResult.PLAYER_ONE_WINS;
        }
      }
    }
    // Check minor diagonal
    for (int row = ROWS - 4; row >= 0; row--) {
      for (int col = COLUMNS - 4; col >= 0; col--) {
        int playerOneCount = 0; 
        int playerTwoCount = 0;
        for (int val = 3; val >= 0; val--) {
          cell = board[row + val][col - val + 3];
          if (cell == CellValue.PLAYER_TWO) {
            winningCells[val] = new Cell(row + val, col - val + 3, CellValue.PLAYER_TWO_WINNER);
            playerTwoCount++;
          } else if (cell == CellValue.PLAYER_ONE) {
            winningCells[val] = new Cell(row + val, col - val + 3, CellValue.PLAYER_ONE_WINNER);
            playerOneCount++;
          }
        }
        if (playerTwoCount == 4) {
          return GameResult.PLAYER_TWO_WINS;
        } else if (playerOneCount == 4) {
          return GameResult.PLAYER_ONE_WINS;
        }
      }
    }
    return GameResult.NONE;
  }  
  
  /**
   * Returns a new iterator object that iterates over the cells on the game board.
   * 
   */
  @Override
  public Iterator<Cell> iterator() {
    return new BoardIterator(board);
  }
  
  /**
   * Private iterator class that iterates through the board one by one and returns cell values.
   * 
   * @author chris
   *
   */
  private class BoardIterator implements Iterator<Cell> {
    private CellValue[][] board;
    private int rowPosition = 0;
    private int columnPosition = 0;
    
    private BoardIterator(CellValue[][] board) {
      this.board = board;
    }
    
    @Override
    public boolean hasNext() {            
      return rowPosition < GameBoard.ROWS;
    }

    @Override
    public Cell next() {
      Cell cell = new Cell(rowPosition, columnPosition, board[rowPosition][columnPosition]);
      columnPosition++;
      
      if (columnPosition == GameBoard.COLUMNS) {
        rowPosition++;
        columnPosition = 0;
      }
      
      return cell;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();      
    }
    
  }

  @Override
  public int getColumns() {
    return COLUMNS;
  }

  @Override
  public int getRows() {
    return ROWS;
  }

  @Override
  public boolean isColumnAvailable(int column) {
    if (column < 0 || column >= COLUMNS) {
      throw new IllegalArgumentException("the column is outside of game bounds: " + column);
    }
    
    return firstAvailableRow[column] != -1;
  }
  
}
