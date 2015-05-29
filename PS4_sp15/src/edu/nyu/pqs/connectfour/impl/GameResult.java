package edu.nyu.pqs.connectfour.impl;

/**
 * Enumerated object used for representing game outcome results.
 * 
 * @author cpp270
 *
 */
public enum GameResult {
  PLAYER_ONE_WINS,
  PLAYER_TWO_WINS,
  STALEMATE,
  NONE;

  /**
   * This method returns friendly string representation of some of the values of this enum object.
   * The exact representation are unspecified, subject to change, and should not be used to
   * infer the game result value.
   * 
   * A typical string representation will, however, indicate the results of the game.
   */  
  @Override
  public String toString() {
    if (this.ordinal() == PLAYER_ONE_WINS.ordinal()) {
      return "Player One Wins!";
    } else if ((this.ordinal() == PLAYER_TWO_WINS.ordinal())) {
      return "Player Two Wins!";
    } else if ((this.ordinal() == STALEMATE.ordinal())) {
      return "Tie.";
    } else {
      return super.toString();      
    }
  }
    
}
