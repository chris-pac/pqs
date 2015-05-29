package edu.nyu.pqs.connectfour.api;

import edu.nyu.pqs.connectfour.impl.Cell;
import edu.nyu.pqs.connectfour.impl.CellValue;

/**
 * The game board object that keeps track of players moves. The object is responsible for 
 * maintaining the integrity of the game. 
 * 
 * @author cpp270
 *
 */
public interface IGameBoard extends Iterable<Cell> {
  
  /**
   * Returns the number of maximum columns on the board.
   * 
   * @return number of columns
   */
  public int getColumns();
  
  /**
   * Returns the number of maximum rows on the board.
   * 
   * @return number of rows
   */
  public int getRows();
  
  /**
   * Checks if a column is available for a checker drop.
   * 
   * @param column the column to check
   * @return true if there is a free slot at this column or false otherwise
   * @throws IllegalArgumentException if <code>column</code> is less than zero or greater than
   * max number of columns - 1
   */
  public boolean isColumnAvailable(int column);
  
  /**
   * Marks the game board cell with the player's value at a specified column location. The row
   * is calculated automatically based on previous moves. False is returned if all rows are filled
   * at the <code>col</code>.
   * 
   * @param col the column where the value is to be set
   * @param value the value to set
   * @return true if there was an available row at the <code>col</code> or false otherwise
   * @throws IllegalArgumentException if <code>column</code> is less than zero or greater than
   * max number of columns - 1
   */
  public boolean setValueToFirstAvailableRow(int col, CellValue value);  
}
