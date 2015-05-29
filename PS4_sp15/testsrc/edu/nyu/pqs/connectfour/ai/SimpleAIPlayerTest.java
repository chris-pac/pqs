package edu.nyu.pqs.connectfour.ai;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import edu.nyu.pqs.connectfour.ai.AIFactory.AIType;
import edu.nyu.pqs.connectfour.api.IAIPlayer;
import edu.nyu.pqs.connectfour.api.IGameBoard;
import edu.nyu.pqs.connectfour.impl.CellValue;
import edu.nyu.pqs.connectfour.impl.GameBoard;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class SimpleAIPlayerTest {

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

  @Test (expected=NullPointerException.class)
  public void testGetNextMove_NullPointerExceptionWhenGameBoardHasNotBeenSet() {
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);

    ai.getNextMove();
  }

  @Test (expected=NullPointerException.class)
  public void testGetNextMove_NullPointerExceptionWhenAIMarkHasNotBeenSet() {
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    IGameBoard board = GameBoard.getInstance();
    ai.setBoard(board);
    
    ai.getNextMove();
  }

  @Test
  public void testGetNextMove_WinningVerticalMoveAtCol6WhenRow2Col6IsTheWinningMove() {
    IGameBoard board = GameBoard.getInstance();
    
    // Player Two is AI
    // checkers are added to a column in reverse order (i.e. from the bottom up)
    
    // col 0
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);

    // col 1
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);

    // col 2
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);

    // col 3
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);

    // col 4
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_ONE);

    // col 5
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_TWO);

    // col 6
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_TWO);
    
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    ai.setBoard(board);
    ai.setPlayer(CellValue.PLAYER_TWO, 2);
    
    // Winning move is at row 2 col 6
    assertEquals(6, ai.getNextMove());
  }

  @Test
  public void testGetNextMove_WinningVerticalMoveAtCol6WhenTopRowIsAvailable() {
    IGameBoard board = GameBoard.getInstance();
    
    // Player Two is AI
    // checkers are added to a column in reverse order (i.e. from the bottom up)
    
    // col 0
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);

    // col 1
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);

    // col 2
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);

    // col 3
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);

    // col 4
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_ONE);

    // col 5
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_TWO);

    // col 6
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_TWO);    
    
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    ai.setBoard(board);
    ai.setPlayer(CellValue.PLAYER_TWO, 2);
    
    // Winning move is at row 2 col 6
    assertEquals(6, ai.getNextMove());
  }

  @Test
  public void testGetNextMove_WinningHorizontalMoveAtCol4WhenRow4Col4IsTheWinningMove() {
    IGameBoard board = GameBoard.getInstance();
    
    // Player Two is AI
    // checkers are added to a column in reverse order (i.e. from the bottom up)
    
    // col 0
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);

    // col 1
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_TWO);

    // col 2
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    
    // col 3
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);

    // col 4
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_ONE);
    
    // col 5
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);

    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    ai.setBoard(board);
    ai.setPlayer(CellValue.PLAYER_TWO, 2);
    
    // Winning move is at row 4 col 4
    assertEquals(4, ai.getNextMove());
  }

  @Test
  public void testGetNextMove_NotCol6MoveWhenColFilledButThreeMarksInARowVertically() {
    IGameBoard board = GameBoard.getInstance();
    
    // Player Two is AI
    // checkers are added to a column in reverse order (i.e. from the bottom up)
    
    // col 0
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);

    // col 1
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);

    // col 2
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);

    // col 3
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);

    // col 4
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_ONE);

    // col 5
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_TWO);

    // col 6
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_TWO);    
    
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    ai.setBoard(board);
    ai.setPlayer(CellValue.PLAYER_TWO, 2);
    
    // Winning move is at row 2 col 6
    assertNotEquals(6, ai.getNextMove());
  }

  @Test
  public void testGetNextMove_WinningMajorDiagonalMoveAtCol1WhenRow2Col1IsTheWinningMove() {
    IGameBoard board = GameBoard.getInstance();
    
    // Player Two is AI
    // checkers are added to a column in reverse order (i.e. from the bottom up)
    
    // col 0
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);

    // col 1
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);

    // col 2
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);

    // col 3
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);

    // col 4
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_ONE);

    // col 5
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_TWO);

    // col 6
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_TWO);
    
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    ai.setBoard(board);
    ai.setPlayer(CellValue.PLAYER_TWO, 2);
    
    // Winning move is at row 2 col 1
    assertEquals(1, ai.getNextMove());
  }

  @Test
  public void testGetNextMove_WinningMinorDiagonalMoveAtCol6WhenRow2Col6IsTheWinningMove() {
    IGameBoard board = GameBoard.getInstance();
    
    // Player Two is AI
    // checkers are added to a column in reverse order (i.e. from the bottom up)
    
    // col 0
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);

    // col 1
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);

    // col 2
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);

    // col 3
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);

    // col 4
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_ONE);

    // col 5
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_TWO);

    // col 6
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(6, CellValue.PLAYER_ONE);
    
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    ai.setBoard(board);
    ai.setPlayer(CellValue.PLAYER_TWO, 2);
    
    // Winning move is at row 2 col 6
    assertEquals(6, ai.getNextMove());
  }

  
  
  @Test
  public void testGetNextMove_WinningMajorDiagonalMoveAtCol0WhenOpponentWinsOnNextMove() {
    IGameBoard board = GameBoard.getInstance();
    
    // Player Two is AI
    // checkers are added to a column in reverse order (i.e. from the bottom up)
    
    // col 0
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);

    // col 1
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_TWO);

    // col 2
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);

    // col 3
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);

    // col 4
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    ai.setBoard(board);
    ai.setPlayer(CellValue.PLAYER_TWO, 2);
    
    // Winning move is at row 2 col 0
    assertEquals(0, ai.getNextMove());
  }  
   
  @Test
  public void testGetNextMove_NegativeOneMoveWhenBoardIsFull() {
    IGameBoard board = GameBoard.getInstance();
  
    for (int i = 0; i < board.getColumns(); i++) {
      for (int j = 0; j < board.getRows(); j++) {
        board.setValueToFirstAvailableRow(i, CellValue.PLAYER_ONE);        
      }
    }
    
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    ai.setBoard(board);
    ai.setPlayer(CellValue.PLAYER_TWO, 2);
    
    // Full board
    assertEquals(-1, ai.getNextMove());    
  }
  
  @Test
  public void testGetNextMove_BlockVerticalMoveAtCol1() {
    IGameBoard board = GameBoard.getInstance();
    
    // Player Two is AI
    // checkers are added to a column in reverse order (i.e. from the bottom up)
    
    // col 0
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);

    // col 1
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
  
    // col 3
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
  
    // col 4
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_ONE);

    // col 5
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_TWO);

    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    ai.setBoard(board);
    ai.setPlayer(CellValue.PLAYER_TWO, 2);
    
    // block player one at row 2 col 1
    assertEquals(1, ai.getNextMove());
    
  }  
  
  @Test (expected=NullPointerException.class)
  public void testSetBoard_NullPointerExceptionWhenNullSet() {
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    ai.setBoard(null);
  }

  @Test
  public void testGetBoard() {
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    IGameBoard board = GameBoard.getInstance();
   
    ai.setBoard(board);

    assertTrue(board.equals(ai.getBoard()));
  }

  @Test (expected=NullPointerException.class)
  public void testSetPlayer_NullPointerExceptionWhenNullSet() {
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    ai.setPlayer(null, 5);
  }

  @Test
  public void testGetPlayer() {
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    ai.setPlayer(CellValue.PLAYER_TWO, 5);
    
    assertEquals(5, ai.getPlayer());
  }

  @Test
  public void testToString_AIStringContainsPlayerTwoGameBoardMarkString() {
    IAIPlayer ai = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    ai.setPlayer(CellValue.PLAYER_TWO, 5);

    assertTrue(ai.toString().contains(CellValue.PLAYER_TWO.toString()));
  }

  @Test
  public void testHashCode_EqualWhenObjectsAreEqual() {
    IGameBoard board = GameBoard.getInstance();
    
    IAIPlayer aiOne = AIFactory.getAIPlayer(AIType.SIMPLE);
    aiOne.setBoard(board);
    aiOne.setPlayer(CellValue.PLAYER_TWO, 5);
    
    IAIPlayer aiTwo = AIFactory.getAIPlayer(AIType.SIMPLE);
    aiTwo.setBoard(board);
    aiTwo.setPlayer(CellValue.PLAYER_TWO, 5);
    
    assertTrue(aiOne.equals(aiTwo));
    assertEquals(aiOne.hashCode(), aiTwo.hashCode());
    
    assertTrue(aiOne.equals(aiOne));
    assertEquals(aiOne.hashCode(), aiOne.hashCode());
  }  

  @Test
  public void testHashCode_EqualWhenObjectsAreEqualAndValuesAreNull() {
    IAIPlayer aiOne = AIFactory.getAIPlayer(AIType.SIMPLE);
    IAIPlayer aiTwo = AIFactory.getAIPlayer(AIType.SIMPLE);
    
    assertTrue(aiOne.equals(aiTwo));
    assertEquals(aiOne.hashCode(), aiTwo.hashCode());
    
    assertTrue(aiOne.equals(aiOne));
    assertEquals(aiOne.hashCode(), aiOne.hashCode());
  }  
  
}
