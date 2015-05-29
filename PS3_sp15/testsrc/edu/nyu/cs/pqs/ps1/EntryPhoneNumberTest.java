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

public class EntryPhoneNumberTest {
  EntryPhoneNumber nullPhone = new EntryPhoneNumber(null, null, null);

  /*
   * Test Fails due to null values.
   */
  @Test
  public void testHashCode_EqualWhenObjectsAreEqualAndValuesAreNull() {
    EntryPhoneNumber nullPhone2 = new EntryPhoneNumber(null, null, null);
    assertTrue(nullPhone.equals(nullPhone2));

    assertEquals(nullPhone.hashCode(), nullPhone2.hashCode());
  }
  
  @Test
  public void testHashCode_EqualWhenObjectsValuesAreEqual() {
    EntryPhoneNumber phone1 = new EntryPhoneNumber("11111", "22222", "333333");
    EntryPhoneNumber phone2 = new EntryPhoneNumber("11111", "22222", "333333");
    assertTrue(phone1.equals(phone2));
    
    assertEquals(phone1.hashCode(), phone2.hashCode());

    // change values through a setter
    phone1.setPrefix("-500");
    phone2.setPrefix("-500");
    assertTrue(phone1.equals(phone2));
    
    assertEquals(phone1.hashCode(), phone2.hashCode());
  }

  @Test
  public void testGetAreaCode() {
    assertNull(nullPhone.getAreaCode());
        
    nullPhone.setAreaCode("30000");
    assertEquals("30000", nullPhone.getAreaCode());
    
    nullPhone.setAreaCode("Hello");
    assertEquals("Hello", nullPhone.getAreaCode());
    
    nullPhone.setAreaCode(null);
    assertNull("area code reset to null fails",nullPhone.getAreaCode());
    
    nullPhone.setAreaCode("");
    assertEquals("", nullPhone.getAreaCode());
    
    // test of the constructor works
    EntryPhoneNumber phone = new EntryPhoneNumber("10000", "10001", "10002");
    assertEquals("constructor fails to set area code","10000", phone.getAreaCode());
  }

  @Test
  public void testGetPrefix() {
    assertNull(nullPhone.getPrefix());
    
    nullPhone.setPrefix("30000");
    assertEquals("30000", nullPhone.getPrefix());
    
    nullPhone.setPrefix("Hello");
    assertEquals("Hello", nullPhone.getPrefix());
    
    nullPhone.setPrefix(null);
    assertNull("prefix reset to null fails",nullPhone.getPrefix());

    nullPhone.setPrefix("");
    assertEquals("", nullPhone.getPrefix());

    // test of the constructor works
    EntryPhoneNumber phone = new EntryPhoneNumber("10000", "10001", "10002");
    assertEquals("constructor fails to set area code","10001", phone.getPrefix());
   }

  @Test
  public void testGetNumber() {
    assertNull(nullPhone.getPrefix());
    
    nullPhone.setNumber("30000");
    assertEquals("30000", nullPhone.getNumber());
    
    nullPhone.setNumber("Hello");
    assertEquals("Hello", nullPhone.getNumber());
    
    nullPhone.setNumber(null);
    assertNull("number reset to null fails",nullPhone.getNumber());

    nullPhone.setNumber("");
    assertEquals("", nullPhone.getNumber());
    
    // test of the constructor works
    EntryPhoneNumber phone = new EntryPhoneNumber("10000", "10001", "10002");
    assertEquals("constructor fails to set area code","10002", phone.getNumber());
  }

  /*
   * Test Fails due to null values.
   */
  @Test
  public void testEqualsObject_EqualWhenValuesAreNull() {
    EntryPhoneNumber nullPhone2 = new EntryPhoneNumber(null, null, null);
    assertTrue("all values null",nullPhone.equals(nullPhone2));
    
    EntryPhoneNumber phone3 = new EntryPhoneNumber("10000", null, "10002");
    EntryPhoneNumber phone4 = new EntryPhoneNumber("10000", null, "10002");
    assertTrue(phone3.equals(phone4));

    phone3.setAreaCode(null);
    phone4.setAreaCode(null);
    assertTrue(phone3.equals(phone4));
  }
 
  /*
   * Test Fails because overwritten equals does not check if the input object 
   * has the same reference.
   */
  @Test
  public void testEqualsObject_EqualWhenSameObjectIsCompared() {
    assertTrue(nullPhone.equals(nullPhone));
  }
  
  @Test
  public void testEqualsObject_EqualWhenDifferentObjectsWithSameValues() {
    EntryPhoneNumber phone1 = new EntryPhoneNumber("10000", "10001", "10002");
    EntryPhoneNumber phone2 = new EntryPhoneNumber("10000", "10001", "10002");
    assertTrue(phone1.equals(phone2));
    
    phone1.setPrefix("-500");
    phone2.setPrefix("-500");
    assertTrue(phone1.equals(phone2));
    
    EntryPhoneNumber phone3 = new EntryPhoneNumber("", "", "");
    EntryPhoneNumber phone4 = new EntryPhoneNumber("", "", ""); 
    assertTrue("empty string values",phone3.equals(phone4));
  }

  @Test
  public void testEqualsObject_NotEqualWhenDifferentInstanceObjects() {
    EntryPhoneNumber phone1 = new EntryPhoneNumber("10000", "10001", "5");
    Object o = new Object();
    assertFalse(phone1.equals(o));
  }
  
  @Test
  public void testEqualsObject_NotEqualWhenObjectsHaveDifferentValues() {
    assertFalse(nullPhone.equals(null));
    
    EntryPhoneNumber phone1 = new EntryPhoneNumber("10000", "10001", "5");
    EntryPhoneNumber phone2 = new EntryPhoneNumber("10000", "10001", "10002");
    assertFalse(phone1.equals(phone2));
    
    phone2.setNumber("5");
    phone1.setPrefix("");
    assertFalse(phone1.equals(phone2));
    
    phone2.setPrefix("");
    phone1.setAreaCode("");
    assertFalse(phone1.equals(phone2));
  }
  
  @Test
  public void testToString() {
    // toString does not specify exact format so we can only test:
    // 1. that string is not empty or null when we set values
    // 2. Our set values do show up in toString
    EntryPhoneNumber phone = new EntryPhoneNumber("1111", "2222222222", "99999999");
    assertNotEquals("empty string", "", phone.toString());
    assertNotEquals("null string", null, phone.toString());
    
    assertTrue(phone.toString().contains("1111"));
    assertTrue(phone.toString().contains("2222222222"));
    assertTrue(phone.toString().contains("99999999"));
  }

}
