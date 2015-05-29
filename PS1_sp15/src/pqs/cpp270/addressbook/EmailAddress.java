package pqs.cpp270.addressbook;

/**
 * EmailAddress is an immutable class that is used to store an email address and its parts.
 * 
 * @author Chris Pac
 * @version 1.0
 * @since 2015-03-17
 *
 */
public final class EmailAddress implements java.io.Serializable {
  private static final long serialVersionUID = -5266065750903839091L;
  private final String localName;
  private final String domainName;
  private final String eMail;
  private static final String AT = "@";

  /**
   * This constructor takes a valid email address as a single string.
   * This string is parsed into local-part(name) and domain-part(name)
   * 
   * @param eMail           an email address; this value can not be an empty string and
   *                        it can not be a null value
   *                        
   * @throws NullPointerException On null input value
   * @throws IllegalArgumentException On empty string value
   * @see NullPointerException
   * @see IllegalArgumentException
   */
  public EmailAddress(String eMail) {
    if (eMail == null) {
      throw new NullPointerException("eMail must not be null");
    }
    
    String parts[] = eMail.split(AT);
    
    if (parts.length < 2 || parts.length > 2 
        || parts[0].trim().isEmpty() 
        || parts[1].trim().isEmpty()) {
      throw new IllegalArgumentException("email: " + eMail);
    }
    
    this.eMail = eMail.trim();
    this.localName = parts[0].trim();
    this.domainName = parts[1].trim();
  }  
  
  /**
   * This constructor creates an email address based on an email's two parts.
   * The two parameter strings are concatenated with the "@" character in the middle.
   * 
   * @param localName       an email address's local-part value; 
   *                        this value can not be an empty string and it can not be a null value
   * @param domainName      an email address's domain-part value; 
   *                        this value can not be an empty string and it can not be a null value
   *                        
   * @throws NullPointerException On null input value
   * @throws IllegalArgumentException On empty string value
   * @see NullPointerException
   * @see IllegalArgumentException
   */
  public EmailAddress(String localName, String domainName) {
    if (localName == null) {
      throw new NullPointerException("localName must not be null");
    }else if (domainName == null) {
      throw new NullPointerException("domainName must not be null");
    }else if (localName.trim().isEmpty()) {
      throw new IllegalArgumentException("local name: " + localName);
    }else if (domainName.trim().isEmpty()) {
      throw new IllegalArgumentException("domain name: " + domainName);
    }
    
    this.localName = localName;
    this.domainName = domainName;
    this.eMail = this.localName + AT + this.domainName;
  }
  
  /**
   * This is a standard getter method is used to obtain the email's local-part.
   * 
   * @return the local-part of this email address; this string will not be empty 
   */
  public String getLocalName() {
    return localName;
  }
  
  /**
   * This is a standard getter method is used to obtain the email's domain-part.
   * 
   * @return the domain-part of this email address; this string will not be empty 
   */
  public String getDomainName() {
    return domainName;
  }
  
  /**
   * This is a standard getter method is used to obtain the email value as a single string.
   * 
   * @return the email address; this string will not be empty 
   */
  public String getEmailAddress() {
    return eMail;
  }
  
  /**
   * Indicates whether some object is equal to this EmailAddress object.
   * This method test if the concatenated eMail field is the same as the object
   * to which it is being compared to.
   * 
   * @param o the reference object with which to compare 
   * @return true if this object is the same as the argument; false otherwise 
   */
  @Override public boolean equals(Object o) {
    if (o == this) {
      return true;
    }      
    if (!(o instanceof EmailAddress)) {
      return false;
    }
    EmailAddress em = (EmailAddress) o;
    return em.eMail.equals(eMail);          
  }

  /**
   * Computes and returns the hash code value for this EmailAddress object.
   * The hash code value is computed based on the full eMail string.
   * 
   * @return a hash code value for this name object
   */
  @Override public int hashCode() {
    return eMail.hashCode();
  }  
  
  /**
   * This method returns the string representation of this EmailAddress object.
   * This string is composed of the local-part, "@" symbol, and the domain-part.
   * 
   * "localName@domainName"
   * 
   * @return a string representation of this EmailAddress object which can not be an empty string
   */
  @Override public String toString() {
    return eMail;
  }
}
