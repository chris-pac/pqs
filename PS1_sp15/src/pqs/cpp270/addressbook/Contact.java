package pqs.cpp270.addressbook;

/**
 * Contact is class that is used to store a individual's contact information.
 * This class uses the Builder pattern to initialize its member variables.
 * 
 * @author Chris Pac
 * @version 1.0
 * @since 2015-03-17
 *
 */
public class Contact implements java.io.Serializable {
  private static final long serialVersionUID = 5519113137891698750L;
  private Name name;
  private PostalAddress postalAddress;
  private PhoneNumber phoneNumber;
  private EmailAddress email;
  private String note;
  
  /**
   * This Builder class is used to initialize and generate the Contact object.
   * 
   * @author Chris Pac
   *
   */
  public static class Builder {
    private Name name;
    private PostalAddress postalAddress;
    private PhoneNumber phoneNumber;
    private EmailAddress email;
    private String note;
        
    /**
     * A setter method used to initialize the name property in the Contact object.
     * 
     * @param val the name object
     * @return the builder object
     * @throws NullPointerException On null input value
     * @see NullPointerException
     */
    public Builder name(Name val) {
      if (val == null) {
        throw new NullPointerException("name must not be null");
      }
      name = val;
      return this;
    }

    /**
     * A setter method used to initialize the postal address property in the Contact object.
     * 
     * @param val the postal address object
     * @return the builder object
     * @throws NullPointerException On null input value
     * @see NullPointerException
     */
    public Builder postalAddress(PostalAddress val) {
      if (val == null) {
        throw new NullPointerException("postalAddress must not be null");
      }
      postalAddress = val;
      return this;
    }
    
    /**
     * A setter method used to initialize the phone number property in the Contact object.
     * 
     * @param val the phone number object
     * @return the builder object
     * @throws NullPointerException On null input value
     * @see NullPointerException
     */
    public Builder phoneNumber(PhoneNumber val) {
      if (val == null) {
        throw new NullPointerException("phoneNumber must not be null");
      }
      phoneNumber = val;
      return this;
    }
    
    /**
     * A setter method used to initialize the email property in the Contact object.
     * 
     * @param val the email object
     * @return the builder object
     * @throws NullPointerException On null input value
     * @see NullPointerException
     */
    public Builder email(EmailAddress val) {
      if (val == null) {
        throw new NullPointerException("email must not be null");
      }
      email = val;
      return this;
    }
    
    /**
     * A setter method used to initialize the note property in the Contact object.
     * 
     * @param val the note string; can be an empty string
     * @return the builder object
     * @throws NullPointerException On null input value
     * @see NullPointerException
     */
    public Builder note(String val) {
      if (val == null) {
        throw new NullPointerException("note must not be null");
      }
      note = val;
      return this;
    }
    
    /**
     * This method creates and returns a new Contact object.
     * 
     * @return new Contact object
     */
    public Contact build() {
      return new Contact(this);
    }
  }
  
  private Contact(Builder builder) {
    name = builder.name;
    postalAddress = builder.postalAddress;
    phoneNumber = builder.phoneNumber;
    email = builder.email;
    note = builder.note;
  }
  
  /**
   * This is a standard getter method is used to obtain the name object.
   * 
   * @return the name object; null if it was never set
   */
  public Name getName() {
    return name;
  }
  
  /**
   * This is a standard getter method is used to obtain the PostalAddress object.
   * 
   * @return the PostalAddress object; null if it was never set
   */
  public PostalAddress getPostalAddress() {
    return postalAddress;
  }
  
  /**
   * This is a standard getter method is used to obtain the PhoneNumber object.
   * 
   * @return the PhoneNumber object; null if it was never set
   */
  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }
  
  /**
   * This is a standard getter method is used to obtain the EmailAddress object.
   * 
   * @return the EmailAddress object; null if it was never set
   */
  public EmailAddress getEmail() {
    return email;
  }
  
  /**
   * This is a standard getter method is used to obtain the note string.
   * 
   * @return the note string; null if it was never set
   */
  public String getNote() {
    return note;
  }
  
  /**
   * Package-private method used to check if this object matches the provided property.
   * @param o the property object to match
   * @return true if the property exists in this object; false otherwise
   */
  boolean matchByProperty(Object o) {
    if (o instanceof Name) {
      return name != null && name.equals(o);
    } else if (o instanceof PostalAddress) {
      return postalAddress != null && postalAddress.equals(o);
    } else if (o instanceof PhoneNumber) {
      return phoneNumber != null && phoneNumber.equals(o);
    } else if (o instanceof EmailAddress) {
      return email != null && email.equals(o);
    } else if (o instanceof String) {
      return note != null && note.equals(o);
    } else {
      return false;
    }
  }
  
  /**
   * Indicates whether some object is equal to this Contact object.
   * This method tests if all the significant fields are the same as the object
   * to which it is being compared to.
   * 
   * @param o the reference object with which to compare 
   * @return true if this object is the same as the argument; false otherwise 
   */
  @Override public boolean equals(Object o) {
    if (o == this) {
      return true;
    }      
    if (!(o instanceof Contact)) {
      return false;
    }
    Contact c = (Contact) o;
    return ((c.name == name) || (c.name != null && c.name.equals(name)))
        && ((c.postalAddress == postalAddress) 
            || (c.postalAddress != null && c.postalAddress.equals(postalAddress))) 
        && ((c.phoneNumber == phoneNumber) 
            || (c.phoneNumber != null && c.phoneNumber.equals(phoneNumber))) 
        && ((c.email == email) || (c.email != null && c.email.equals(email)))
        && ((c.note == note) || (c.note != null && c.note.equals(note)));          
  }
  
  /**
   * Computes and returns the hash code value for this Contact object.
   * The hash code value is computed using two prime numbers and all the significant values
   * of the the contact object.
   * 
   * @return a hash code value for this PostalAddress object
   */
  @Override public int hashCode() {
    int result = 17;
    result = 31 * result + (name == null ? 0 : name.hashCode());
    result = 31 * result + (postalAddress == null ? 0 : postalAddress.hashCode());
    result = 31 * result + (phoneNumber == null ? 0 : phoneNumber.hashCode());
    result = 31 * result + (email == null ? 0 : email.hashCode());
    result = 31 * result + (note == null ? 0 : note.hashCode());
    return result;
  }  
  
  /**
   * This method returns the string representation of this contact object.
   * The exact representation are unspecified and subject to change,
   * but the following may be regarded as typical:
   * 
   * "Name
   * Postal Address
   * Phone Number
   * email"
   * 
   * Note: The string has a new line when appropriate. The note property is not returned.
   *  
   * @return a string representation of this contact object which can be an empty string
   */
  @Override public String toString() {
    String format = "%s";
    format = format + (postalAddress == null ? "%s" : "%n%s");
    format = format + (phoneNumber == null ? "%s" : "%n%s");
    format = format + (email == null ? "%s" : "%n%s");
    
    return String.format(format,
        name == null ? "" : name.toString(), 
        postalAddress == null ? "" : postalAddress.toString(), 
        phoneNumber == null ? "" : phoneNumber.toString(), 
        email == null ? "" : email.toString()).trim();
  }
}
