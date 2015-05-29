package addressBook;

/**
 * @author cp1425
 * 
 * A class that stores a {@Java.lang.String} object,
 * representing a phone number.
 *
 */
public class PhoneNumber extends EntryField {
  private String pn;

  /**
   * Constructor method - a {@link String} containing only numeric
   * characters [0-9] should be passed in, else an exception is thrown.
   * @param pn
   * @throws InvalidIOException
   */
  public PhoneNumber(String pn) throws InvalidIOException {
	if (pn.matches("[0-9]+")) {
	  this.pn = pn;
	} else {
	  throw new InvalidIOException();
	}
  }

  @Override
  public String toString() {
	return pn;
  }

  @Override
  public boolean checkIO(String... strings) {
	
	return false;
  }
  
  @Override
  public boolean equals(Object o) {
	if (!(o instanceof PhoneNumber)){
	  return false;
	}
	if (!pn.equals(((PhoneNumber) o).getPn())){
	  return false;
	}
	return true;
  }
  
  @Override
  public int hashCode() {
	return pn.hashCode();
  }

  /**
   * @return the phone number as {@link String}
   */
  public String getPn() {
    return pn;
  }
  
  /**
   * @return Sets the passed in {@link String} as the stored
   * phone number.
   */
  public void setPn(String pn) {
    this.pn = pn;
  }
}
