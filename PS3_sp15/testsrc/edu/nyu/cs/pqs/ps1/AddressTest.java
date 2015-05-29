package edu.nyu.cs.pqs.ps1;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * @author Chris Pac
 * @version 1.0
 * @since 2015-03-31
 * 
 * Tests Assume that null values are Valid
 */

public class AddressTest {
  Address nullAdd = new Address(null, null, null, null, null);
  
  @Test
  public void testHashCode_EqualWhenObjectsValuesAreEqual() {
    Address add1 = new Address("street", "city", "state", "zipcode", "country");
    Address add2 = new Address("street", "city", "state", "zipcode", "country");
    assertTrue(add1.equals(add2));
    
    assertEquals(add1.hashCode(), add2.hashCode());
  }

  /*
   * Test Fails due to null values.
   */
  @Test
  public void testHashCode_EqualWhenObjectsAreEqualAndValuesAreNull() {
    Address add2 = new Address(null, null, null, null, null);
    assertTrue(nullAdd.equals(add2));
    
    assertEquals(nullAdd.hashCode(), add2.hashCode());
  }

  /*
   * Test Fails because overwritten equals does not check if the input object 
   * has the same reference.
   */
  @Test
  public void testEqualsObject_EqualWhenSameObjectIsCompared() {
    assertTrue(nullAdd.equals(nullAdd));
  }

  @Test
  public void testEqualsObject_NotEqualWhenDifferentInstanceObjects() {
    Object o = new Object();
    assertFalse(nullAdd.equals(o));
  }
  
  @Test
  public void testEqualsObject_NotEqualWhenObjectsHaveDifferentValues() {
    Address add1 = new Address("street", "city", "state", "zipcode", "country");
    Address add2 = new Address("s66", "c77", "s88", "z99", "c23");    
  
    assertFalse(add1.equals(add2));
  }
  
  @Test
  public void testSimpleSettersAndGetters_ValidWhenValuesAreNull() {
    assertNull(nullAdd.getStreet());

    assertNull(nullAdd.getStreet());

    assertNull(nullAdd.getCity());

    assertNull(nullAdd.getZip());

    assertNull(nullAdd.getCountry());
  }

  /*
   * Test Fails because I consider incorrectly named setter function to be a bug.
   */
  @Test
  public void testSimpleSettersAndGetters_ValidWhenValuesNotNull() {
    Address add1 = new Address("street", "city", "state", "zipcode", "country");
    
    assertEquals("street", add1.getStreet());
    add1.setStreet("");
    assertEquals("", add1.getStreet());

    assertEquals("city", add1.getCity());
    // missing correctly named City setter
    add1.setString("");
    assertEquals("", add1.getCity());

    assertEquals("state", add1.getState());
    add1.setState("");
    assertEquals("", add1.getState());

    assertEquals("zipcode", add1.getZip());
    add1.setZip("");
    assertEquals("", add1.getZip());

    assertEquals("country", add1.getCountry());
    add1.setCountry("");
    assertEquals("", add1.getCountry());
    
    fail("missing correctly named City setter");
  }

  @Test
  public void testToString() {
    // toString does not specify exact format so we can only test:
    // 1. that string is not empty or null when we set values
    // 2. Our set values do show up in toString
    String[] n = {"s11", "c22", "s33", "z44", "c55"};
    Address add1 = new Address(n[0],n[1],n[2],n[3],n[4]);
    
    assertNotEquals("empty string", "", add1.toString());
    assertNotEquals("null string", null, add1.toString());
    
    for (String s : n) {
      assertTrue("test failed for: "+s,add1.toString().contains(s));
    }
  }
  
}
