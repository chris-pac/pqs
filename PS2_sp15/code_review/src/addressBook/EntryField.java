package addressBook;

/**
 * @author cp1425
 * 
 * A super class, to be extended by {@link addressBook.Entry} objects.
 */
public abstract class EntryField {
  public abstract String toString();
  public abstract boolean equals(Object o);
  public abstract int hashCode();
  protected abstract boolean checkIO(String... strings); 
}
