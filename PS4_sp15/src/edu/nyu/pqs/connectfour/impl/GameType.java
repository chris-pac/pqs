package edu.nyu.pqs.connectfour.impl;

/**
 * Enumerated object used for representing opponents of a game.
 * 
 * @author cpp270
 *
 */
public enum GameType {
  HUMAN_COMPUTER,
  COMPUTER_HUMAN,
  HUMAN_HUMAN;
  
  /**
   * This method returns friendly string representation of some of the values of this enum object.
   * The exact representation are unspecified, subject to change, and should not be used to
   * infer the value.
   * 
   * The method will return a string that is friendly, concise description of who 
   * the opponents are.
   */
  @Override
  public String toString() {
    if (this.ordinal() == HUMAN_COMPUTER.ordinal()) {
      return "Human vs. Computer";
    } else if ((this.ordinal() == COMPUTER_HUMAN.ordinal())) {
      return "Computer vs. Human";
    } else if ((this.ordinal() == HUMAN_HUMAN.ordinal())) {
      return "Human vs. Human";
    } else {
      return super.toString();      
    }
  }    
}
