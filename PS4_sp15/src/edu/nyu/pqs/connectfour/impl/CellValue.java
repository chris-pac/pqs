package edu.nyu.pqs.connectfour.impl;

/**
 * Enumerated object used for representing possible values/marks on a game board.
 * 
 * @author cpp270
 *
 */
public enum CellValue {
  PLAYER_ONE,
  PLAYER_TWO,
  PLAYER_ONE_WINNER,
  PLAYER_TWO_WINNER,
  EMPTY;
  
  /**
   * This method returns friendly string representation of some of the values of this enum object.
   * The exact representation are unspecified, subject to change, and should not be used to
   * infer the value.
   */
  @Override
  public String toString() {
    if (this.ordinal() == PLAYER_ONE.ordinal()) {
      return "Player One";
    } else if ((this.ordinal() == PLAYER_TWO.ordinal())) {
      return "Player Two";
    } else {
      return super.toString();      
    }
  }  
}
