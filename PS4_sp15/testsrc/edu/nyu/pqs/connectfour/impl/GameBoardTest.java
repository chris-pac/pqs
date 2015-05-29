package edu.nyu.pqs.connectfour.impl;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class GameBoardTest {

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
  
  @Test (expected=IllegalArgumentException.class)
  public void testSetValueToFirstAvailableRow_ExceptionWhenColLessThanZero() {
    GameBoard board = GameBoard.getInstance();
    board.setValueToFirstAvailableRow(-1, CellValue.PLAYER_ONE);
  }
  
  @Test (expected=IllegalArgumentException.class)
  public void testSetValueToFirstAvailableRow_ExceptionWhenWhenColGreaterThanMaxCols() {
    GameBoard board = GameBoard.getInstance();
    // zero index
    board.setValueToFirstAvailableRow(GameBoard.COLUMNS, CellValue.PLAYER_ONE);
  }

  @Test
  public void testSetValueToFirstAvailableRow_FalseWhenColumnIsFilled() {
    GameBoard board = GameBoard.getInstance();
 
    // col 2
    for (int i = 0; i <= board.getRows(); i++) {
      board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    }
  }

  @Test
  public void testIsColumnAvailable_AvailableWhenTopRowEmpty() {
    GameBoard board = GameBoard.getInstance();
    
    // col 2
    for (int i = 0; i < board.getRows() - 1; i++) {
      board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    }

    assertTrue(board.isColumnAvailable(2));
  }

  @Test
  public void testIsColumnAvailable_NotAvailableWhenColumnFilled() {
    GameBoard board = GameBoard.getInstance();
    
    // col 2
    for (int i = 0; i < board.getRows(); i++) {
      board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    }

    assertFalse(board.isColumnAvailable(2));
  }

  @Test (expected=IllegalArgumentException.class)
  public void testIsColumnAvailable_ExceptionWhenColLessThanZero() {   
    GameBoard.getInstance().isColumnAvailable(-1);
  }
  
  @Test (expected=IllegalArgumentException.class)
  public void testIsColumnAvailable_ExceptionWhenColGreaterThanMaxCols() {    
    GameBoard.getInstance().isColumnAvailable(GameBoard.getInstance().getColumns());
  }

  @Test (expected=UnsupportedOperationException.class)
  public void testIterator_UnsupportedOperationExceptionWhenRemovedCalled() {
    Iterator<Cell> it = GameBoard.getInstance().iterator();
    if (it.hasNext()) {
      it.remove();
    }
  }
  
  @Test 
  public void testGetColumns_EqualWhenMethodIsComparedToStatic() {
    GameBoard board = GameBoard.getInstance();
    assertEquals(GameBoard.COLUMNS, board.getColumns());
  }
  
  @Test
  public void testGetRows_EqualWhenMethodIsComparedToStatic() {
    GameBoard board = GameBoard.getInstance();
    assertEquals(GameBoard.ROWS, board.getRows());
  }

  @Test
  public void testGetWinner_HorizontalWinForPalyerOneAtRow4() {
    GameBoard board = GameBoard.getInstance();
    
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);

    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_ONE);
    
    assertEquals(board.getWinner(), GameResult.PLAYER_ONE_WINS);
  }

  @Test
  public void testGetWinner_HorizontalWinForPalyerTwoAtBottomRow() {
    GameBoard board = GameBoard.getInstance();
    
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);

    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_ONE);
    
    assertEquals(board.getWinner(), GameResult.PLAYER_TWO_WINS);
  }

  @Test
  public void testGetWinner_VerticalWinForPalyerOneAtFirstCol() {
    GameBoard board = GameBoard.getInstance();

    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    
    assertEquals(board.getWinner(), GameResult.PLAYER_ONE_WINS);
  }

  @Test
  public void testGetWinner_VerticalWinForPalyerTwoFirstCol() {
    GameBoard board = GameBoard.getInstance();

    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    
    assertEquals(board.getWinner(), GameResult.PLAYER_TWO_WINS);
  }

  @Test
  public void testGetWinner_VerticalWinForPalyerTwoFirstColWhenMoreThanFourSameMarks() {
    GameBoard board = GameBoard.getInstance();

    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    
    assertEquals(board.getWinner(), GameResult.PLAYER_TWO_WINS);
  }

  @Test
  public void testGetWinner_MajorDiagonalWinForPalyerTwo() {
    GameBoard board = GameBoard.getInstance();

    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);

    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);

    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);

    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_TWO);

    assertEquals(board.getWinner(), GameResult.PLAYER_TWO_WINS);
  }

  @Test
  public void testGetWinner_MinorDiagonalWinForPalyerTwo() {
    GameBoard board = GameBoard.getInstance();

    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);

    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
    board.setValueToFirstAvailableRow(4, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(5, CellValue.PLAYER_ONE);

    board.setValueToFirstAvailableRow(0, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(1, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(2, CellValue.PLAYER_TWO);

    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_ONE);
    board.setValueToFirstAvailableRow(3, CellValue.PLAYER_TWO);
    
    assertEquals(board.getWinner(), GameResult.PLAYER_TWO_WINS);
  }

}
