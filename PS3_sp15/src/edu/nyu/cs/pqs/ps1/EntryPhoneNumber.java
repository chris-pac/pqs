package edu.nyu.cs.pqs.ps1;

/**
 * EntryPhoneNumber class is to hold phone numbers in each entry in the address book. My address 
 * book accept phones in the form of area code, prefix and number. 
 *  
 * @author Tareq Alghamdi taa307@nyu.edu
 */
public class EntryPhoneNumber {
  // The Entry phone number consists of Area Code, Prefix, and the Phone number
  // They are String for the simplicity
  private String areaCode;
  private String prefix;
  private String number;
  
  /**
   * Constructor for fully qualified EntryPhoneNumber
   * @param areaCode
   * @param prefix
   * @param number
   */
  public EntryPhoneNumber(String areaCode, String prefix, String number){
    this.areaCode = areaCode;
    this.prefix = prefix;
    this.number = number;
  }
  
  /**
   * Getter for the area code
   * @return String area code
   */
  public String getAreaCode(){
    return areaCode;
  }
  
  /**
   * Setter for the area code
   * @param areaCode
   */
  public void setAreaCode(String areaCode){
    this.areaCode = areaCode;
  }
  
  /**
   * Getter for the prefix
   * @return String the prefix
   */
  public String getPrefix(){
    return prefix;
  }
  
  /**
   * Setter for the prefix
   * @param prefix
   */
  public void setPrefix(String prefix){
    this.prefix = prefix;
  }
  
  /**
   * Getter for the phone number
   * @return String phone number
   */
  public String getNumber(){
    return number;
  }
  
  /**
   * Setter for the phone number
   * @param number
   */
  public void setNumber(String number){
    this.number = number;
  }
  
  /**
   * Overridden equals()
   * to check the equality of two entry phone numbers
   * @param Object supposed to be entry phone number
   * @return true if the numbers are equal, false if they are not
   */
  @Override
  public boolean equals(Object o){
    if (! (o instanceof EntryPhoneNumber)){
      return false;
    }
    EntryPhoneNumber epn= (EntryPhoneNumber) o;
    return epn.areaCode.equals(areaCode) && epn.prefix.equals(prefix) && epn.number.equals(number);
  }
  
  /**
   * Overridden hashCode()
   * This method is adopted from hashCode() method in the course text book
   * Effective Java 2nd edition 
   * to return the hash code of the entry phone number
   * @return int of the hash code of the phone number components
   */
  @Override 
  public int hashCode(){
    int hash = 17;
    hash = 31 * hash + areaCode.hashCode();
    hash = 31 * hash +prefix.hashCode();
    hash = 31 * hash +number.hashCode();
    return hash;
  }
  
  /**
   * Overridden toString()
   * to return the toString of the phone number 
   */
  @Override
  public String toString(){
    return areaCode + "-" + prefix + "-" + number;
  }
}
