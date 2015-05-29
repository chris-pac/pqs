package edu.nyu.pqs.connectfour.impl;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.pqs.connectfour.ai.AIFactory;
import edu.nyu.pqs.connectfour.api.IConnectFourListener;
import edu.nyu.pqs.connectfour.api.IGameBoard;

public class ConnectFourTest {

  /*
   * Helper class used to test if listeners are notified properly
   */
  class MockListener implements IConnectFourListener {
    boolean gameStratFired = false;
    boolean gameUpdateFired = false;
    boolean gameEndFired = false;
    
    GameType gameType = null;
    GameResult gameResult = null;
    IGameBoard updatedGameBoard = null;
    
    public MockListener(ConnectFour model) {
      model.addListener(this);
    }
    
    public MockListener() {
      
    }

    @Override
    public void gameStart(GameType type) {
      gameType = type;
      gameStratFired = true;
      
      // handles empty string, null string and null type
      if (type.toString().isEmpty()) {
        throw new IllegalStateException("GameType string empty");
      }
    }

    @Override
    public void gameUpdate(IGameBoard updatedBoard) {
      updatedGameBoard = updatedBoard;
      gameUpdateFired = true;      
    }

    @Override
    public void gameEnd(GameResult result) {
      gameResult = result;
      gameEndFired = true;
      
      // handles empty string, null string and null result
      if (result.toString().isEmpty()) {
        throw new IllegalStateException("GameResult string empty");
      }
    }
    
  }
  
  /*
   * We need to reset the singleton GameBoard to new value using reflection.
   * This involves setting a new GameBoard instance to the static final INSTANCE field
   */
  @Before
  public void setUp() throws Exception {
    final Field field = Class.forName(GameBoard.class.getName()).getDeclaredField("INSTANCE");
    field.setAccessible(true);
    
    // Disable the final field modifier
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    
    Object oldValue = field.get(Class.forName(GameBoard.class.getName()));
    
    // create new instance of GameBoard using reflection since constructor is private
    final Constructor<GameBoard> constructor;
    constructor = GameBoard.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    GameBoard newValue = constructor.newInstance();
    
    field.set(oldValue, newValue);
  }

  @Test
  public void testFireNewGameEvent_FiredAndGameTypeValidWhenNewGameStarts() {
    ConnectFour model = new ConnectFour(GameType.HUMAN_COMPUTER);
    
    int numberOfListeners = 5;
    MockListener[] listeners = new MockListener[numberOfListeners];
    
    for (int i = 0; i < numberOfListeners; i++) {
      listeners[i] = new MockListener(model);
    }
    
    GameType type = GameType.COMPUTER_HUMAN;
    model.newGame(type);
    
    for (MockListener l : listeners) {
      assertTrue(l.gameStratFired);
      assertEquals(l.gameType, type);
    }
    
    type = GameType.HUMAN_COMPUTER;
    model.newGame(type);
    
    for (MockListener l : listeners) {
      assertTrue(l.gameStratFired);
      assertEquals(l.gameType, type);
    }

    type = GameType.HUMAN_HUMAN;
    model.newGame(type);
    
    for (MockListener l : listeners) {
      assertTrue(l.gameStratFired);
      assertEquals(l.gameType, type);
    }
  }

  @Test
  public void testFireNewGameEvent_NotFiredWhenNewGameStartsAndListenerIsRemoved() {
    ConnectFour model = new ConnectFour(GameType.HUMAN_COMPUTER);
    
    int numberOfListeners = 5;
    MockListener[] listeners = new MockListener[numberOfListeners];
    
    for (int i = 0; i < numberOfListeners; i++) {
      listeners[i] = new MockListener(model);
    }
    
    // remove one listener
    model.removeListener(listeners[numberOfListeners / 2]);
    
    GameType type = GameType.COMPUTER_HUMAN;
    model.newGame(type);
    
    assertFalse(listeners[numberOfListeners / 2].gameStratFired);
  }

  @Test
  public void testFireEndGameEvent_FiredAndGameResultValidWhenNewGameEnds() {
    ConnectFour model = new ConnectFour(GameType.HUMAN_HUMAN);
    
    int numberOfListeners = 5;
    MockListener[] listeners = new MockListener[numberOfListeners];
    
    for (int i = 0; i < numberOfListeners; i++) {
      listeners[i] = new MockListener(model);
    }
    
    GameResult result = GameResult.PLAYER_ONE_WINS;
    
    // human 1
    model.dropChecker(0);
    // human 2
    model.dropChecker(1);
    
    // human 1
    model.dropChecker(0);
    // human 2
    model.dropChecker(2);
    
    // human 1
    model.dropChecker(0);
    // human 2
    model.dropChecker(3);
    
    // human 1 winning move
    model.dropChecker(0);
    
    for (MockListener l : listeners) {
      assertTrue(l.gameEndFired);
      assertEquals(l.gameResult, result);
    }
  }

  @Test
  public void testFireEndGameEvent_NotFiredWhenGameEndsAndListenerIsRemoved() {
    ConnectFour model = new ConnectFour(GameType.HUMAN_HUMAN);
    
    int numberOfListeners = 5;
    MockListener[] listeners = new MockListener[numberOfListeners];
    
    for (int i = 0; i < numberOfListeners; i++) {
      listeners[i] = new MockListener(model);
    }
    
    // human 1
    model.dropChecker(0);
    // human 2
    model.dropChecker(1);
    
    // human 1
    model.dropChecker(0);
    // human 2
    model.dropChecker(2);
    
    // human 1
    model.dropChecker(0);
    // human 2
    model.dropChecker(3);
 
    // remove one listener before the winning move
    model.removeListener(listeners[numberOfListeners / 2]);

    // human 1 winning move
    model.dropChecker(0);    
        
    assertFalse(listeners[numberOfListeners / 2].gameStratFired);
  }
  
  @Test (expected=IllegalArgumentException.class)
  public void testDropChecker_IllegalArgumentExceptionWhenColLessThanZero() {
    ConnectFour model = new ConnectFour(GameType.HUMAN_HUMAN);
    model.dropChecker(-1);
  }

  @Test (expected=IllegalArgumentException.class)
  public void testDropChecker_IllegalArgumentExceptionWhenColGreaterThanMaxColumns() {
    ConnectFour model = new ConnectFour(GameType.HUMAN_HUMAN);
    model.dropChecker(Integer.MAX_VALUE);
  }

  @Test (expected=NullPointerException.class)
  public void testAddListener_NullPointerExceptionWhenNullListenerAdded() {
    ConnectFour model = new ConnectFour(GameType.HUMAN_HUMAN);
    model.addListener(null);
  }

  @Test
  public void testRemoveListener_FalseWhenListenerRemovedBeforeItWasAdded() {
    ConnectFour model = new ConnectFour(GameType.HUMAN_HUMAN);
    model.removeListener(new MockListener());
  }

  @Test (expected=NullPointerException.class)
  public void testAddListener_NullPointerExceptionWhenNullListenerRemoved() {
    ConnectFour model = new ConnectFour(GameType.HUMAN_HUMAN);
    model.removeListener(null);
  }
  
  @Test
  public void testGetGameType() {
    ConnectFour model = new ConnectFour(GameType.HUMAN_HUMAN);
    assertEquals(model.getGameType(), GameType.HUMAN_HUMAN);
  }

  @Test
  public void testGetAIType() {
    ConnectFour model = new ConnectFour.Builder(GameType.HUMAN_COMPUTER)
    .aiType(AIFactory.AIType.SIMPLE)
    .build();    
    assertEquals(model.getAIType(), AIFactory.AIType.SIMPLE);
  }

}
