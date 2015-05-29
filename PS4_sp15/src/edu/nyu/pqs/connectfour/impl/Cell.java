package edu.nyu.pqs.connectfour.impl;

/**
 * This object is used to store the location and value of a cell on a game board.
 * 
 * @author cpp270
 *
 */
public class Cell {
  private final int row;
  private final int column;
  private final CellValue value;

  public Cell(int row, int column, CellValue value) {
    if (value == null) {
      throw new NullPointerException("cell value can not be null");
    }
    
    this.row = row;
    this.column = column;
    this.value = value;
  }
  
  /**
   * Returns the row location of this cell object.
   * 
   * @return the row location
   */
  public int getRow() {
    return row;
  }
  
  /**
   * Returns the row location of this cell object.
   * 
   * @return the column location
   */
  public int getColumn() {
    return column;
  }
  
  /**
   * Returns the mark/value at the specified row, column location.
   * 
   * @return the cell value
   */
  public CellValue getValue() {
    return value;
  }

  /**
   * Friendly string representation of this cell object. The exact representation is 
   * unspecified and subject to change
   */
  @Override
  public String toString() {
    return String.format("Row: %d, Column: %d, Value: %s", row, column, value.toString());
  }
  
}