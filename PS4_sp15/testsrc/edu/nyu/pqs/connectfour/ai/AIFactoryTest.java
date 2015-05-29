package edu.nyu.pqs.connectfour.ai;

import static org.junit.Assert.*;

import org.junit.Test;

public class AIFactoryTest {

  @Test
  public void testGetAIPlayer_ValidWhenValidTypeIsSpecified() {
    assertNotEquals(AIFactory.getAIPlayer(AIFactory.AIType.SIMPLE), null);
  }

  @Test (expected=NullPointerException.class)
  public void testGetAIPlayer_NullPointerExceptionWhenNullTypeIsSpecified() {
    AIFactory.getAIPlayer(null);
  }

}
