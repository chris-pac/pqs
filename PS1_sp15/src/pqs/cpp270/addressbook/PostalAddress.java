package pqs.cpp270.addressbook;

/**
 * PostalAddress is an immutable class that is used to store a postal address.
 * This class uses the Builder pattern to initialize its member variables.
 * 
 * @author Chris Pac
 * @version 1.0
 * @since 2015-03-17
 *
 */
public final class PostalAddress implements java.io.Serializable {
  private static final long serialVersionUID = 9183989911693572360L;
  private final String city;
  private final String country;
  private final String postalCode;
  private final String stateOrProvince;
  private final String street;
  private final String streetNumber;
  private final String apartmentNumber;
  
  /**
   * This Builder class is used to initialize and generate the PostalAddress object.
   * 
   * @author Chris Pac
   *
   */
  public static class Builder {
    private String city = "";
    private String country = "";
    private String postalCode = "";
    private String stateOrProvince = "";
    private String street = "";
    private String streetNumber = "";
    private String apartmentNumber = "";

    /**
     * A setter method used to initialize the city value in the postal address object.
     * 
     * @param val the city string value; can be an empty string
     * @return the builder object
     * @throws NullPointerException On null input value
     * @see NullPointerException
     */
    public Builder city(String val) {
      if (val == null) {
        throw new NullPointerException("city must not be null");
      }
      city = val;
      return this;
    }

    /**
     * A setter method used to initialize the country value in the postal address object.
     * 
     * @param val the country string value; can be an empty string
     * @return the builder object
     * @throws NullPointerException On null input value
     * @see NullPointerException
     */
    public Builder country(String val) {
      if (val == null) {
        throw new NullPointerException("country must not be null");
      }
      country = val;
      return this;
    }
    
    /**
     * A setter method used to initialize the country value in the postal address object.
     * 
     * @param val the country string value; can be an empty string
     * @return the builder object
     * @throws NullPointerException On null input value
     * @see NullPointerException
     */
    public Builder postalCode(String val) {
      if (val == null) {
        throw new NullPointerException("postalCode must not be null");
      }
      postalCode = val;
      return this;
    }
    
    /**
     * A setter method used to initialize the state or 
     * province value in the postal address object.
     * 
     * @param val the state or province string value; can be an empty string
     * @return the builder object
     * @throws NullPointerException On null input value
     * @see NullPointerException
     */
    public Builder stateOrProvince(String val) {
      if (val == null) {
        throw new NullPointerException("stateOrProvince must not be null");
      }
      stateOrProvince = val;
      return this;
    }
    
    /**
     * A setter method used to initialize the street value in the postal address object.
     * 
     * @param val the street string value; can be an empty string
     * @return the builder object
     * @throws NullPointerException On null input value
     * @see NullPointerException
     */
    public Builder street(String val) {
      if (val == null) {
        throw new NullPointerException("street must not be null");
      }
      street = val;
      return this;
    }
    
    /**
     * A setter method used to initialize the street number value in the postal address object.
     * 
     * @param val the street number string value; can be an empty string
     * @return the builder object
     * @throws NullPointerException On null input value
     * @see NullPointerException
     */
    public Builder streetNumber(String val) {
      if (val == null) {
        throw new NullPointerException("street must not be null");
      }
      streetNumber = val;
      return this;
    }
    
    /**
     * A setter method used to initialize the apartment number 
     * value in the postal address object.
     * 
     * @param val the apartment number string value; can be an empty string
     * @return the builder object
     * @throws NullPointerException On null input value
     * @see NullPointerException
     */
    public Builder apartmentNumber(String val) {
      if (val == null) {
        throw new NullPointerException("apartmentNumber must not be null");
      }
      apartmentNumber = val;
      return this;
    }
    
    /**
     * This method creates and returns a new PostalAddress object.
     * 
     * @return new PostalAddress object
     */
    public PostalAddress build() {
      return new PostalAddress(this);
    }
  }
  
  private PostalAddress(Builder builder) {
    city = builder.city;
    country = builder.country;
    postalCode = builder.postalCode;
    stateOrProvince = builder.stateOrProvince;
    street = builder.street;
    streetNumber = builder.streetNumber;
    apartmentNumber = builder.apartmentNumber;
  }
  
  /**
   * This is a standard getter method is used to obtain the city value.
   * 
   * @return the city value of the postal address; can be empty
   */
  public String getCity() {
    return city;
  }
  
  /**
   * This is a standard getter method is used to obtain the country value.
   * 
   * @return the country value of the postal address; can be empty
   */
  public String getCountry() {
    return country;
  }
  
  /**
   * This is a standard getter method is used to obtain the postal code value.
   * 
   * @return the postal code value of the postal address; can be empty
   */
  public String getPostalCode() {
    return postalCode;
  }
  
  /**
   * This is a standard getter method is used to obtain the state or province value.
   * 
   * @return the state or province value of the postal address; can be empty
   */
  public String getStateOrProvince() {
    return stateOrProvince;
  }
  
  /**
   * This is a standard getter method is used to obtain the street value.
   * 
   * @return the street value of the postal address; can be empty
   */
  public String getStreet() {
    return street;
  }
  
  /**
   * This is a standard getter method is used to obtain the street number value.
   * 
   * @return the street number value of the postal address; can be empty
   */
  public String getStreetNumber() {
    return streetNumber;
  }
  
  /**
   * This is a standard getter method is used to obtain the apartment number value.
   * 
   * @return the apartment number value of the postal address; can be empty
   */
  public String getApartmentNumber() {
    return apartmentNumber;
  }
  
  /**
   * Indicates whether some object is equal to this PostalAddress object.
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
    if (!(o instanceof PostalAddress)) {
      return false;
    }
    PostalAddress pa = (PostalAddress) o;
    return pa.city.equals(city) 
        && pa.country.equals(country) 
        && pa.postalCode.equals(postalCode) 
        && pa.stateOrProvince.equals(stateOrProvince) 
        && pa.street.equals(street) 
        && pa.streetNumber.equals(streetNumber) 
        && pa.apartmentNumber.equals(apartmentNumber);          
  }
  
  /**
   * Computes and returns the hash code value for this PostalAddress object.
   * The hash code value is computed using two prime numbers and all the significant values
   * of the the postal address object.
   * 
   * @return a hash code value for this PostalAddress object
   */
  @Override public int hashCode() {
    int result = 17;
    result = 31 * result + city.hashCode();
    result = 31 * result + country.hashCode();
    result = 31 * result + postalCode.hashCode();
    result = 31 * result + stateOrProvince.hashCode();
    result = 31 * result + street.hashCode();
    result = 31 * result + streetNumber.hashCode();
    result = 31 * result + apartmentNumber.hashCode();
    return result;
  }  
  
  /**
   * This method returns the string representation of this postal address object.
   * The exact representation are unspecified and subject to change,
   * but the following may be regarded as typical:
   * 
   * "StreetNumber StreetName (optional) apt. ApartmentNumber
   * City StateOrProvince PostalCode Country"
   * 
   * Note: The string has a new line when appropriate
   *  
   * @return a string representation of this postal address object which can be an empty string
   */
  @Override public String toString() {
    String format = "";
    if (!streetNumber.isEmpty() || !street.isEmpty() || !apartmentNumber.isEmpty()) {
      format = "%s %s";
      if (!apartmentNumber.isEmpty()) {
        format = format + " apt. %s";
      }
    }

    if (!city.isEmpty() || !stateOrProvince.isEmpty() 
        || !postalCode.isEmpty() || !country.isEmpty()) {
      if (!format.isEmpty()) {
        format = format + "%n";
      } else {
        format = "%s %s";
      }
      
      format = format + "%s %s %s %s";
    }

    if (format.isEmpty()) {
      return "";
    }
    
    if (!apartmentNumber.isEmpty()) {
      return String.format(format,
          streetNumber, street, apartmentNumber, city, stateOrProvince, postalCode, country)
          .trim();
    } else {
      return String.format(format,
          streetNumber, street, city, stateOrProvince, postalCode, country).trim();
    }
  }
}
