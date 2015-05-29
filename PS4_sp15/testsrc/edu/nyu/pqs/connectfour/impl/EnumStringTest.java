package edu.nyu.pqs.connectfour.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class EnumStringTest {

  @Test
  public void test_CellValueToStringNotEmptyOrNull() {
    assertNotEquals("PLAYER_ONE null test", CellValue.PLAYER_ONE.toString(), null);
    assertNotEquals("PLAYER_TWO null test", CellValue.PLAYER_TWO.toString(), null);
    assertNotEquals("EMPTY null test", CellValue.EMPTY.toString(), null);

    assertFalse("PLAYER_ONE empty test", CellValue.PLAYER_ONE.toString().isEmpty());
    assertFalse("PLAYER_TWO empty test", CellValue.PLAYER_TWO.toString().isEmpty());
    assertFalse("EMPTY empty test", CellValue.EMPTY.toString().isEmpty());
  }

  @Test
  public void test_GameTypeToStringNotEmptyOrNull() {
    assertNotEquals("HUMAN_COMPUTER null test", GameType.HUMAN_COMPUTER.toString(), null);
    assertNotEquals("COMPUTER_HUMAN null test", GameType.COMPUTER_HUMAN.toString(), null);
    assertNotEquals("HUMAN_HUMAN null test", GameType.HUMAN_HUMAN.toString(), null);

    assertFalse("HUMAN_COMPUTER empty test", GameType.HUMAN_COMPUTER.toString().isEmpty());
    assertFalse("COMPUTER_HUMAN empty test", GameType.COMPUTER_HUMAN.toString().isEmpty());
    assertFalse("HUMAN_HUMAN empty test", GameType.HUMAN_HUMAN.toString().isEmpty());
  }

  @Test
  public void test_GameResultToStringNotEmptyOrNull() {
    assertNotEquals("PLAYER_ONE_WINS null test", GameResult.PLAYER_ONE_WINS.toString(), null);
    assertNotEquals("PLAYER_TWO_WINS null test", GameResult.PLAYER_TWO_WINS.toString(), null);
    assertNotEquals("STALEMATE null test", GameResult.STALEMATE.toString(), null);
    assertNotEquals("NONE null test", GameResult.NONE.toString(), null);

    assertFalse("PLAYER_ONE_WINS empty test", GameResult.PLAYER_ONE_WINS.toString().isEmpty());
    assertFalse("PLAYER_TWO_WINS empty test", GameResult.PLAYER_TWO_WINS.toString().isEmpty());
    assertFalse("STALEMATE empty test", GameResult.STALEMATE.toString().isEmpty());
    assertFalse("NONE empty test", GameResult.NONE.toString().isEmpty());
  }

}
