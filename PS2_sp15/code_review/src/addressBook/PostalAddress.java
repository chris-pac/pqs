package addressBook;

/**
 * @author cp1425 A class that stores and handles postal addresses. A postal
 *         address is represented by three {@link String} objects, that cannot
 *         be null.
 */
public class PostalAddress extends EntryField {
  private String line1;
  private String line2;
  private String line3;

  /**
   * Constructor, requires three non null {@link String} objects.
   * @param line1
   * @param line2
   * @param line3
   * @throws IllegalArgumentException
   */
  public PostalAddress(String line1, String line2, String line3) throws IllegalArgumentException {
	this.line1 = line1;
	this.line2 = line2;
	this.line3 = line3;
  }

  @Override
  public String toString() {
	return (line1 + "\n" + line2 + "\n" + line3);
  }

  /**
   * 
   * @return the three lines of the address as one {@link String}
   */
  public String getAddress() {
	return (line1 + "\n" + line2 + "\n" + line3);
  }

  public String getLine1() {
	return line1;
  }

  public void setLine1(String line1) {
	this.line1 = line1;
  }

  public String getLine2() {
	return line2;
  }

  public void setLine2(String line2) {
	this.line2 = line2;
  }

  public String getLine3() {
	return line3;
  }

  public void setLine3(String line3) {
	this.line3 = line3;
  }

  @Override
  public boolean equals(Object o) {
	if (!(o instanceof PostalAddress)) {
	  return false;
	}
	String oneLineAddress = this.toString();
	String queryAsOneLine = ((PostalAddress) o).getAddress().toString();
	if (!oneLineAddress.equals(queryAsOneLine)) {
	  return false;
	}
	return true;
  }

  @Override
  public boolean checkIO(String... strings) {
	return true;
  }

  @Override
  public int hashCode() {
	return line1.hashCode() + line2.hashCode() + line3.hashCode();
  }
}
