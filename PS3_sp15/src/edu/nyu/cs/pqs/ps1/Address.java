package edu.nyu.cs.pqs.ps1;

/**
 * the Address class represents the postal address for the entry holder in the address book.
 * It holds Street, City, State, Zip and Country
 * @author Tareq Alghamdi taa307@nyu.edu 
 *
 */
public class Address {
  // The below are the postal address fields for the address entry
  // They are all private strings to represents any country with any 
  // address specification
  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;
  
  /**
   * Constructor of the address of the entry. 
   * @param str
   * @param cty
   * @param sta
   * @param z
   * @param cnt
   */
  public Address(String str, String cty, String sta, String z, String cnt){
    street = str;
    city = cty;
    state = sta;
    zip = z;
    country = cnt;
  }
  
  /**
   * Getter for the street
   * @return String street
   */
  public String getStreet(){
    return street;
  }
  
  /**
   * Setter for the street
   * @param String str
   */
  public void setStreet(String str){
    street = str;
  }
  
  /**
   * Getter for the city
   * @return String city
   */
  public String getCity(){
    return city;
  }
  
  /**
   * Setter for the city
   * @param String cty
   */
  public void setString(String cty){
    city = cty;
  }
  
  /**
   * Getter for the state
   * @return String state
   */
  public String getState(){
    return state;
  }
  
  /**
   * Setter for the state
   * @param String sta
   */
  public void setState(String sta){
    state = sta;
  }
  
  /**
   * Getter for the zip
   * @return String zip
   */
  public String getZip(){
    return zip;
  }
  
  /**
   * Setter for the zip
   * @param String z
   */
  public void setZip(String z){
    zip = z;
  }
  
  /**
   * Getter for the country
   * @return String Country
   */
  public String getCountry(){
    return country;
  }
  
  /**
   * Setter for the country
   * @param String cnt
   */
  public void setCountry(String cnt){
    country = cnt;
  }
  
  /**
   * Overridden equals()
   * to check if two addresses are equal and return true. If any address parameter if missing or 
   * does not match, it returns false
   * @return boolean result
   */
  @Override
  public boolean equals(Object o){
    if(!(o instanceof Address)){
      return false;
    }
    // Two addresses are equal if all the address fields are equal
    Address a = (Address) o;
    return a.street.equals(street) &&
        a.city.equals(city)&&
        a.state.equals(state) &&
        a.zip.equals(zip) &&
        a.country.equals(country);
  }
  
  /**
   * Overridden hashCode() 
   * to produce the hash code of the new address
   * This method is adopted from hashCode() method in the course text book
   * Effective Java 2nd edition 
   * @return int the new hashcode
   */
  @Override
  public int hashCode(){
    int hash = 17;
    hash = 31 * hash + street.hashCode();
    hash = 31 * hash + city.hashCode();
    hash = 31 * hash + state.hashCode();
    hash = 31 * hash + zip.hashCode();
    hash = 31 * hash + country.hashCode();
    return hash;
  }
  
  /**
   * Overridden toString()
   * to produce the address in the form
   * Street
   * City, State Zip
   * Country
   * @return String address
   */
  @Override
  public String toString(){
    return street + "\n" +
       city + ", " + state + " " + zip + "\n" +
       country;
  }
}
