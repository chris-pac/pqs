package edu.nyu.cs.pqs.ps1;

/**
 * the Email class is to represent the email in the address entry.
 * 
 * @author Tareq Alghamdi taa307@nyu.edu
 *
 */
public class Email {
  // The email is composed of the string email
  private String email;
  
  /**
   * the constructor of the entry email address class
   * @param String emailAddress 
   * @return 
   */
  public Email(String email){
    this.email = email;
  }
  
  /**
   * Getter for the email
   * @return String the email address
   */
  public String getEmail(){
    return email;
  }
  
  /**
   * Setter for the email
   * @param email
   */
  public void setEmail(String email){
    this.email = email;
  }
  
  /**
   * Overridden equals()
   * to check equality of two emails
   * @return true if they are equal and false if they are not
   */
  @Override
  public boolean equals(Object o){
    if(! (o instanceof Email)){
      return false;
    }
    Email e = (Email) o;
    return e.email.equals(email);
  }
  
  /**
   * Overridden hashCode()
   * This method is adopted from hashCode() method in the course text book
   * Effective Java 2nd edition 
   * to return the hash code of the new instance
   * @return int the new hash code
   */
  @Override
  public int hashCode(){
    int hash =17; 
    hash = 31 * hash + email.hashCode();
    return hash;
  }
  
  /**
   * Overridden toString()
   * to return the to string of the new class
   * @return String the email
   */
  @Override
  public String toString(){
    return email;
  }
}
