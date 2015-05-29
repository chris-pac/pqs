package edu.nyu.pqs.connectfour.ai;

import edu.nyu.pqs.connectfour.api.IGameBoard;
import edu.nyu.pqs.connectfour.impl.Cell;
import edu.nyu.pqs.connectfour.impl.CellValue;

/**
 * A connect four AI player object that makes simple defensive moves based on the current 
 * game board layout. This AI is guaranteed to make a winning move if one is available. 
 * If a winning move is not available it makes simple decision about a next move. 
 * The AI tries to find a move that is adjacent to the maximum number of its own checkers.
 * 
 * The AI also actively blocks opposing player's moves.
 * 
 * @author cpp270
 *
 */
class SimpleAIPlayer extends AIPlayer {
  
  
  @Override
  public int getNextMove() {
    if (this.gameBoard == null ) {
      throw new NullPointerException("Game Board in null");
    }
    
    if (this.myBoardMark == null ) {
      throw new NullPointerException("Player Mark is in null");
    }

    CellValue[][] tempBoard = genFriandlyBoardHelper(this.gameBoard);
    
    int col = -1;
    
    // start with a check if we can get identical 4 marks across and then 3 across ... 
    for (int depth = 4; depth > 1 && col == -1; depth--) {
      col = getNextWinningOrDefensiveMove(tempBoard, this.gameBoard.getRows(), 
          this.gameBoard.getColumns(), this.myBoardMark, depth);      
    }

    // we have not found a good move so go for middle column which is the most advantages
    int middleColumn = this.gameBoard.getColumns() / 2;
    if (col == -1 && this.gameBoard.isColumnAvailable(middleColumn)) {
      col = middleColumn;
    } else if (col == -1) {
      // last resort, just pick first available column
      for (int i = 0; i < this.gameBoard.getColumns(); i++) {
        if (this.gameBoard.isColumnAvailable(i)){
          return i;
        }
      }
    }
    
    return col;
  }

  /*
   * Generate a friendly game board we can use to calculate the next move
   */
  private CellValue[][] genFriandlyBoardHelper(IGameBoard board) {
    CellValue[][] friendlyBoard = new CellValue[board.getRows()][board.getColumns()];
    
    for (Cell c :  board) {
      friendlyBoard[c.getRow()][c.getColumn()] = c.getValue();
    }
    
    return friendlyBoard;
  }
  
  /*
   * Returns a winning move if one is available or a defensive move that blocks the next winning 
   * player's move.
   * 
   * Returns a column where that move should be made. 
   * If a move is NOT found -1 is returned.
   * 
   * maxDepth is to specify how many same marks across we are searching for
   */
  private int getNextWinningOrDefensiveMove(CellValue[][] localBoard, 
      int rows, int columns, CellValue aiPlayerMark, int maxDepth) {
    
    CellValue cell = CellValue.EMPTY;
    Cell[] winningCells = new Cell[4];
    
    int defensiveMove = -1;
    
    // Check for possible horizontal move
    for (int row = 0; row < rows; row++) {
      for (int col = 3; col < columns; col++) {
        int playerOneCount = 0; 
        int playerTwoCount = 0;
        int emptyCellVal = -1;
        
        for (int val = 0; val < maxDepth; val++) {
          cell = localBoard[row][col - val];
          winningCells[val] = new Cell(row, col - val, cell);

          if (cell == CellValue.PLAYER_TWO) {
            playerTwoCount++;
          } else if (cell == CellValue.PLAYER_ONE) {
            playerOneCount++;
          } else if (cell == CellValue.EMPTY) {
            emptyCellVal = val;
          }
        }

        if (playerTwoCount == maxDepth - 1 && emptyCellVal != -1) {
          boolean legalMove = isLegalMove(localBoard, winningCells[emptyCellVal], rows);
          if (aiPlayerMark == CellValue.PLAYER_TWO && legalMove) {
            return winningCells[emptyCellVal].getColumn();
          } else if (legalMove) {
            defensiveMove = winningCells[emptyCellVal].getColumn();
          }
        } else if (playerOneCount == maxDepth - 1 && emptyCellVal != -1) {
          boolean legalMove = isLegalMove(localBoard, winningCells[emptyCellVal], rows);
          if (aiPlayerMark == CellValue.PLAYER_ONE && legalMove) {
            return winningCells[emptyCellVal].getColumn();
          } else if (legalMove) {
            defensiveMove = winningCells[emptyCellVal].getColumn();
          }
        }
      }
    }
    // Check possible vertical move
    for (int col = 0; col < columns; col++) {
      for (int row = 3; row < rows; row++) {
        int playerOneCount = 0; 
        int playerTwoCount = 0;
        int emptyCellVal = -1;
        
        for (int val = 0; val < maxDepth; val++) {
          cell = localBoard[row - val][col];
          winningCells[val] = new Cell(row - val, col, cell);

          if (cell == CellValue.PLAYER_TWO) {
            playerTwoCount++;
          } else if (cell == CellValue.PLAYER_ONE) {
            playerOneCount++;
          } else if (cell == CellValue.EMPTY) {
            emptyCellVal = val;
          }
        }
        
        if (playerTwoCount == maxDepth - 1 && emptyCellVal != -1) {
          boolean legalMove = isLegalMove(localBoard, winningCells[emptyCellVal], rows);
          if (aiPlayerMark == CellValue.PLAYER_TWO && legalMove) {
            return winningCells[emptyCellVal].getColumn();
          } else if (legalMove) {
            defensiveMove = winningCells[emptyCellVal].getColumn();
          }
        } else if (playerOneCount == maxDepth - 1 && emptyCellVal != -1) {
          boolean legalMove = isLegalMove(localBoard, winningCells[emptyCellVal], rows);
          if (aiPlayerMark == CellValue.PLAYER_ONE && legalMove) {
            return winningCells[emptyCellVal].getColumn();
          } else if (legalMove) {
            defensiveMove = winningCells[emptyCellVal].getColumn();
          }
        }
      }
    }
    // Check for possible major diagonal move
    for (int row = rows - 4; row >= 0; row--) {
      for (int col = columns - 4; col >= 0; col--) {
        int playerOneCount = 0; 
        int playerTwoCount = 0;
        int emptyCellVal = -1;

        for (int val = maxDepth - 1; val >= 0; val--) {
          cell = localBoard[row + val][col + val];
          winningCells[val] = new Cell(row + val, col + val, cell);
          
          if (cell == CellValue.PLAYER_TWO) {
            playerTwoCount++;
          } else if (cell == CellValue.PLAYER_ONE) {
            playerOneCount++;
          } else if (cell == CellValue.EMPTY) {
            emptyCellVal = val;
          }
        }
        
        if (playerTwoCount == maxDepth - 1 && emptyCellVal != -1) {
          boolean legalMove = isLegalMove(localBoard, winningCells[emptyCellVal], rows);
          if (aiPlayerMark == CellValue.PLAYER_TWO && legalMove) {
            return winningCells[emptyCellVal].getColumn();
          } else if (legalMove) {
            defensiveMove = winningCells[emptyCellVal].getColumn();
          }
        } else if (playerOneCount == maxDepth - 1 && emptyCellVal != -1) {
          boolean legalMove = isLegalMove(localBoard, winningCells[emptyCellVal], rows);
          if (aiPlayerMark == CellValue.PLAYER_ONE && legalMove) {
            return winningCells[emptyCellVal].getColumn();
          } else if (legalMove) {
            defensiveMove = winningCells[emptyCellVal].getColumn();
          }
        }
      }
    }
    // Check for possible minor diagonal move
    for (int row = rows - 4; row >= 0; row--) {
      for (int col = columns - 4; col >= 0; col--) {
        int playerOneCount = 0; 
        int playerTwoCount = 0;
        int emptyCellVal = -1;
        
        for (int val = maxDepth - 1; val >= 0; val--) {
          cell = localBoard[row + val][col - val + 3];
          winningCells[val] = new Cell(row + val, col - val + 3, cell);
          
          if (cell == CellValue.PLAYER_TWO) {
            playerTwoCount++;
          } else if (cell == CellValue.PLAYER_ONE) {
            playerOneCount++;
          } else if (cell == CellValue.EMPTY) {
            emptyCellVal = val;
          }
        }
        
        if (playerTwoCount == maxDepth - 1 && emptyCellVal != -1) {
          boolean legalMove = isLegalMove(localBoard, winningCells[emptyCellVal], rows);
          if (aiPlayerMark == CellValue.PLAYER_TWO && legalMove) {
            return winningCells[emptyCellVal].getColumn();
          } else if (legalMove) {
            defensiveMove = winningCells[emptyCellVal].getColumn();
          }
        } else if (playerOneCount == maxDepth - 1 && emptyCellVal != -1) {
          boolean legalMove = isLegalMove(localBoard, winningCells[emptyCellVal], rows);
          if (aiPlayerMark == CellValue.PLAYER_ONE && legalMove) {
            return winningCells[emptyCellVal].getColumn();
          } else if (legalMove) {
            defensiveMove = winningCells[emptyCellVal].getColumn();
          }
        }
      }
    }
    return defensiveMove;
  }

  /*
   * This function checks if a move to the cell location can be made on the localBoard
   */
  private boolean isLegalMove(CellValue[][] localBoard, Cell cell, int rows) {
    // first check if the cell is empty and then check if the row below is 
    // occupied (i.e. not empty)
    boolean legal = localBoard[cell.getRow()][cell.getColumn()] == CellValue.EMPTY;
    
    // if its not the bottom row check if there is a checker below it
    if (legal && cell.getRow() != rows - 1) {
      legal = localBoard[cell.getRow() + 1][cell.getColumn()] != CellValue.EMPTY;
    }
    
    return legal;
  }
  
  
  /**
   * Indicates whether some object is equal to this AI object.
   * This method test if the id, player mark, and game board are the same as the object
   * to which it is being compared to.
   * 
   * @param o the reference object with which to compare 
   * @return true if this object is the same as the argument; false otherwise 
   */
  @Override public boolean equals(Object o) {
    if (o == this) {
      return true;
    }      
    if (!(o instanceof SimpleAIPlayer)) {
      return false;
    }
    SimpleAIPlayer ai = (SimpleAIPlayer) o;
    return ai.myId == this.myId 
        && ai.gameBoard == this.gameBoard
        && ai.myBoardMark == this.myBoardMark;          
  }

  /**
   * Computes and returns the hash code value for this AI object.
   * The hash code value is computed using two prime numbers and the hash codes
   * of the Id, board, mark values.
   * 
   * @return a hash code value for this ai object
   */
  @Override public int hashCode() {
    int result = 17;
    if (this.gameBoard != null) {
      result = 31 * result + this.gameBoard.hashCode();      
    }
    
    result = 31 * result + this.myId;
    
    if (this.myBoardMark != null) {
      result = 31 * result + this.myBoardMark.hashCode();      
    }
    return result;
  }  
  
  /**
   * This method returns the string representation of this AI object.
   * The exact representation is unspecified and subject to change,
   * but the following may be regarded as typical:
   * 
   * "AI Description: Simple AI, ID: id, Board Mark: mark"
   * 
   * @return a string representation of this name object which can be an empty string
   */
  @Override public String toString() {
    return String.format("AI Description: %s, ID %d, Board Mark %s",
        "Simple AI", this.myId, 
        this.myBoardMark == null ? "Not Set" : this.myBoardMark.toString());
  }  
}
