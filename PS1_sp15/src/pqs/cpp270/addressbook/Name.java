package pqs.cpp270.addressbook;

/**
 * Name is an immutable class that is used to store an individual's various name values.
 * 
 * @author Chris Pac
 * @version 1.0
 * @since 2015-03-17
 *
 */
public final class Name implements java.io.Serializable {
  private static final long serialVersionUID = -2164970920580834412L;
  private final String firstName;
  private final String middleName;
  private final String lastName;
  
  /**
   * Sole constructor.
   * All parameters are stripped of leading and trailing white spaces. 
   * 
   * @param firstName       first name of an individual; this value can be an empty string;
   *                        it can not be a null value
   * @param middleName      middle name of an individual; this value can be an empty string;
   *                        it can not be a null value
   * @param lastName        last name of an individual; this value can be an empty string;
   *                        it can not be a null value
   * @throws NullPointerException On null input value
   * @see NullPointerException
   */
  public Name(String firstName, String middleName, String lastName) {
    if (firstName == null) {
      throw new NullPointerException("firstName must not be null");
    } else if (middleName == null) {
      throw new NullPointerException("middleName must not be null");
    } else if (lastName == null) {
      throw new NullPointerException("lastName must not be null");
    }
    
    this.firstName = firstName.trim();
    this.middleName = middleName.trim();
    this.lastName = lastName.trim();
  }
  
  /**
   * This is a standard getter method is used to obtain an individual's first name.
   * 
   * @return the first name of an individual which can be an empty string 
   *         but will not contain all white spaces or a null value
   */
  public String getFirstName() {
    return firstName;
  }
  
  /**
   * This is a standard getter method is used to obtain an individual's middle name.
   * 
   * @return the middle name of an individual which can be an empty string 
   *         but will not contain all white spaces or a null value
   */
  public String getMiddleName() {
    return middleName;
  }
  
  /**
   * This is a standard getter method is used to obtain an individual's last name.
   * 
   * @return the last name of an individual which can be an empty string 
   *         but will not contain all white spaces or a null value
   */
  public String getLastName() {
    return lastName;
  }
  
  /**
   * Indicates whether some object is equal to this Name object.
   * This method test if the first name, middle name and last name are the same as the object
   * to which it is being compared to.
   * 
   * @param o the reference object with which to compare 
   * @return true if this object is the same as the argument; false otherwise 
   */
  @Override public boolean equals(Object o) {
    if (o == this) {
      return true;
    }      
    if (!(o instanceof Name)) {
      return false;
    }
    Name n = (Name) o;
    return n.firstName.equals(firstName) 
        && n.middleName.equals(middleName) 
        && n.lastName.equals(lastName);          
  }

  /**
   * Computes and returns the hash code value for this Name object.
   * The hash code value is computed using two prime numbers and the hash codes
   * of the first, middle and last names.
   * 
   * @return a hash code value for this name object
   */
  @Override public int hashCode() {
    int result = 17;
    result = 31 * result + firstName.hashCode();
    result = 31 * result + middleName.hashCode();
    result = 31 * result + lastName.hashCode();
    return result;
  }  
  
  /**
   * This method returns the string representation of this name object.
   * The exact representation are unspecified and subject to change,
   * but the following may be regarded as typical:
   * 
   * "FirstName MiddleName LastName"
   * 
   * @return a string representation of this name object which can be an empty string
   */
  @Override public String toString() {
    return String.format("%s %s %s",firstName, middleName, lastName).trim();
  }
}
