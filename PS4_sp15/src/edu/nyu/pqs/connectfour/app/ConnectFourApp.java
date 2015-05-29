package edu.nyu.pqs.connectfour.app;

import edu.nyu.pqs.connectfour.ai.AIFactory;
import edu.nyu.pqs.connectfour.gui.GameView;
import edu.nyu.pqs.connectfour.impl.ConnectFour;
import edu.nyu.pqs.connectfour.impl.GameType;

/**
 * Connect Four Application object responsible for setting the initial values of the game and
 * starting the GUI.
 * 
 * @author cpp270
 *
 */
public class ConnectFourApp {

  /*
   * Launches the game.
   */
  private void start() {
    ConnectFour connectFourModel = new ConnectFour.Builder(GameType.HUMAN_COMPUTER)
      .aiType(AIFactory.AIType.SIMPLE)
      .build();

    new GameView(connectFourModel);
  }
  
  public static void main(String[] args) {
    System.setProperty("apple.laf.useScreenMenuBar", "true");

    new ConnectFourApp().start();
  }

}
