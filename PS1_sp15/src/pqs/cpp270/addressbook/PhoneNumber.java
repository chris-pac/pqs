package pqs.cpp270.addressbook;

/**
 * PhoneNumber is an immutable class that is used to store a phone number value.
 * 
 * @author Effective Java 2nd Edition
 * @author Chris Pac
 * @version 1.0
 * @since 2015-03-17
 *
 */
public final class PhoneNumber implements java.io.Serializable {
  private static final long serialVersionUID = 2849348849991572358L;
  private final short areaCode;
  private final short prefix;
  private final short lineNumber;

  /**
   * Sole constructor.
   * All parameters are of type int and are checked to be in the correct range of values. 
   * 
   * @param areaCode    the three digit area code that must be greater than zero and less than 999
   * @param prefix      the three digit prefix number that must be greater than zero 
   *                    and less than 999
   * @param lineNumber  the four digit line number that must be greater than zero 
   *                    and less than 9999
   * @throws IllegalArgumentException On invalid out of range argument
   * @see IllegalArgumentException
   */
  public PhoneNumber(int areaCode, int prefix, int lineNumber) {
    rangeCheck(areaCode,    999, "area code");
    rangeCheck(prefix,      999, "prefix");
    rangeCheck(lineNumber, 9999, "line number");
    
    this.areaCode = (short) areaCode;
    this.prefix = (short) prefix;
    this.lineNumber = (short) lineNumber;
  }
  
  /**
   * A helper method that checks if the three parts of a phone number are in the correct range.
   * 
   * @param arg   one of the three parts of a phone number
   * @param max   maximum value the part 
   * @param name  a helpful string that is passed to the exception
   */
  private static void rangeCheck(int arg, int max, String name) {
    if (arg < 0 || arg > max) {
      throw new IllegalArgumentException(name +": " + arg);
    }
  }
  
  /**
   * This is a standard getter method is used to obtain the area code of the phone number.
   * 
   * @return the area code for this phone number which is greater than zero and less than 999
   */
  public int getAreaCode() {
    return (int) areaCode;
  }
  
  /**
   * This is a standard getter method is used to obtain the prefix of the phone number.
   * 
   * @return the prefix for this phone number which is greater than zero and less than 999
   */
  public int getPrefix() {
    return (int) prefix;
  }
  
  /**
   * This is a standard getter method is used to obtain the line number of the phone number.
   * 
   * @return the line number for this phone number which is greater than zero and less than 9999
   */
  public int getLineNumber() {
    return (int) lineNumber;
  }
  
  /**
   * Indicates whether some object is equal to this PhoneNumber object.
   * This method test if the area code, prefix, and the line number 
   * are the same as those in the object to which it is being compared to.
   * 
   * @param o the reference object with which to compare 
   * @return true if this object is the same as the argument; false otherwise 
   */
  @Override public boolean equals(Object o) {
    if (o == this) {
      return true;
    }      
    if (!(o instanceof PhoneNumber)) {
      return false;
    }
    PhoneNumber pn = (PhoneNumber) o;
    return pn.lineNumber == lineNumber && pn.prefix == prefix && pn.areaCode == areaCode;
  }

  /**
   * Computes and returns the hash code value for this PhoneNumber object.
   * The hash code value is computed using two prime numbers and the values
   * of the area code, prefix and the line number.
   * 
   * @return a hash code value for this PhoneNumber object
   */
  @Override public int hashCode() {
    int result = 17;
    result = 31 * result + areaCode;
    result = 31 * result + prefix;
    result = 31 * result + lineNumber;
    return result;
  }  
  
  /**
   * Returns the string representation of this phone number.
   * The string consists of fourteen characters whose format
   * is "(AAA) YYY-ZZZZ", where AAA is the area code, YYY is
   * the prefix, and ZZZZ is the line number.  (Each of the
   * capital letters represents a single decimal digit.)
   *
   * @return a string representation of this phone number object which will not be an empty string
   */  
  @Override public String toString() {
    return String.format("(%03d) %03d-%04d",areaCode, prefix, lineNumber);
  }  
}
