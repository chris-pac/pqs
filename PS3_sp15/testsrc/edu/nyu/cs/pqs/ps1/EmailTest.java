package edu.nyu.cs.pqs.ps1;

/*
 * @author Chris Pac
 * @version 1.0
 * @since 2015-03-31
 * 
 * Tests Assume that null values are Valid
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class EmailTest {
  Email nullEmail = new Email(null);
  
  @Test
  public void testHashCode_EqualWhenObjectsValuesAreEqual() {
    Email email1 = new Email("A ubiquitous, general-purpose, "
        + "programming language.");
    Email email2 = new Email("A ubiquitous, general-purpose, "
        + "programming language.");
    assertTrue(email1.equals(email2));
    
    assertEquals(email1.hashCode(), email2.hashCode());
    
    email1.setEmail("-500");
    email2.setEmail("-500");
    assertTrue(email1.equals(email2));
    
    assertEquals(email1.hashCode(), email2.hashCode());
  }

  /*
   * Test Fails due to null values.
   */
  @Test
  public void testHashCode_EqualWhenObjectsAreEqualAndValuesAreNull() {
    Email nullNote2 = new Email(null);
    if (nullEmail.equals(nullNote2)) {
      assertEquals(nullEmail.hashCode(), nullNote2.hashCode());
    }
  }
  
  @Test
  public void testGetText() {
    String email = "A ubiquitous, general-purpose, "
        + "programming language.";
    nullEmail.setEmail(email);
    assertEquals(email, nullEmail.getEmail());
    
    nullEmail.setEmail(null);
    assertEquals(null, nullEmail.getEmail());

    nullEmail.setEmail("");
    assertEquals("", nullEmail.getEmail());
  }

  /*
   * Test Fails because overwritten equals does not check if the input object 
   * has the same reference.
   */
  @Test
  public void testEqualsObject_EqualWhenSameObjectIsCompared() {
    assertTrue(nullEmail.equals(nullEmail));
  }
  
  @Test
  public void testEqualsObject_EqualWhenDifferentObjectsWithSameValues() {
    Email email1 = new Email("A ubiquitous, general-purpose, "
        + "programming language.");
    Email email2 = new Email("A ubiquitous, general-purpose, "
        + "programming language.");
    assertTrue(email1.equals(email2));

    email1.setEmail("foo");    
    email2.setEmail("bar");
    email1.setEmail("");
    email2.setEmail("");
    assertTrue(email1.equals(email2));    
  }

  /*
   * Test Fails due to null values.
   */
  @Test
  public void testEqualsObject_EqualWhenAllValuesAreNull() {
    Email nullNote2 = new Email(null);
    assertTrue("all values null",nullEmail.equals(nullNote2));
    
    nullEmail.setEmail("50");
    nullNote2.setEmail("100");
    nullEmail.setEmail(null);
    nullNote2.setEmail(null);
    assertTrue(nullEmail.equals(nullNote2));
  }

  @Test
  public void testEqualsObject_NotEqualWhenDifferentInstanceObjects() {
    Email phone1 = new Email("Hello");
    Object o = new Object();
    assertFalse(phone1.equals(o));
  }
  
  @Test
  public void testEqualsObject_NotEqualWhenObjectsHaveDifferentValues() {
    assertFalse(nullEmail.equals(null));
    
    Email email1 = new Email("A ubiquitous, general-purpose, "
        + "programming language.");
    Email email2 = new Email("A ubiquitous, gxneral-purpose, "
        + "programming language.");
    assertFalse(email1.equals(email2));
    
    email2.setEmail("5");
    email1.setEmail("");
    assertFalse(email1.equals(email2));
  }
  
  @Test
  public void testToString() {
    // toString does not specify exact format so we can only test:
    // 1. that string is not empty or null when we set values
    // 2. Our set values do show up in toString
    Email email = new Email("A ubiquitous, general-purpose, "
        + "programming language.");
    assertNotEquals("empty string", "", email.toString());
    assertNotEquals("null string", null, email.toString());
    
    assertTrue(email.toString().contains("general"));
  }
}
