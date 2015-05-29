package edu.nyu.pqs.connectfour.ai;

import edu.nyu.pqs.connectfour.api.IAIPlayer;

/**
 * The IAIPlayer is factory class for IAIPlayer objects. It creates the most appropriate AI based
 * on the type of AI requested. The factor does not guarantee the exact AI requested but simply
 * the closest one it can produce.
 *
 */
public class AIFactory {

  /**
   * Enumerated list of AI objects this factory can create
   * @author cpp270
   *
   */
  public static enum AIType { SIMPLE };
  
  /**
   * Creates and returns a new IAIPlayer object
   * @param type type of the new AI
   * @return The new IAIPlayer object
   * @throws IllegalStateException if the type can not be resolved
   * @throws NullPointerException if <code>type</code> is null
   */
  public static IAIPlayer getAIPlayer(AIType type) {
    if (type == null) {
      throw new NullPointerException("AIType can not be null");
    }
    
    switch (type) {
      case SIMPLE : 
        return new SimpleAIPlayer();
      default :
        throw new IllegalStateException("Unknown type: " + type);
    }
  }
}
