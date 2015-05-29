package edu.nyu.pqs.canvas.api;

// TODO: remove comments about notifying about drawing start, end...

/**
 * This api is used to position child elements within a canvas by using coordinates 
 * that are relative to the usre's surface area. Furthermore, this api allows for manipulation
 * of those elements.
 * 
 * Each element is composed of zero or more shape objects which contain the actual coordinates and
 * rendering properties.
 * 
 * A Canvas class implements the observable/model scheme that notifies the observers/listeners 
 * through fired events.
 * 
 * @author cpp270
 *
 */
public interface ICanvas extends Iterable<ICShape>{
  /**
   * Sets the background RGB color for this canvas.
   * Notifies all registered listeners about the new background color.
   * 
   * @param color RGB background color
   */
  public void setBackgroundColor(int color);
  
  /**
   * Sets the foreground RGB color that will be used to paint the shapes on the canvas.
   * Notifies all registered listeners about the new foreground color.  
   * 
   * @param color RGB foreground color
   */
  public void setForegroundColor(int color);
  
  /**
   * Sets the size of the pen used to draw shapes on the canvas.
   * Notifies all registered listeners about the new pen size. 
   * 
   * @param size the pen size
   * @throws IllegalArgumentException when <code>size</code> is not within min/max pen size bounds
   */
  public void setPenSize(int size);
  
  /**
   * Sets the shape of the pen that will be used to paint shapes on the canvas.
   * Notifies all registered listeners about the new pen shape. 
   * 
   * @param shape the pen shape
   * @throws NullPointerException when <code>shape</code> is null
   */
  public void setPenShape(ICShape.PenShape shape);
  
  /**
   * Returns the background RGB color for this canvas.
   * 
   * @return RGB background color
   */
  public int getBackgroundColor();
  
  /**
   * Returns the foreground RGB color for this canvas.
   * 
   * @return RGB foreground color
   */
  public int getForegroundColor();
  
  /**
   * Returns the pen size for this canvas. This size will always be within the min/max pen size 
   * bounds.
   * 
   * @return pen size
   */
  public int getPenSize();
  
  /**
   * Returns the pen shape for this canvas. The shape will never be null.
   * 
   * @return the pen shape
   */
  public ICShape.PenShape getPenShape();
  
  /**
   * Returns the maximum pen size allowed on this canvas. Will always be greater than 
   * minimum pen size.
   * 
   * @return maximum pen size
   */
  public int getMaxPenSize();
  
  /**
   * Returns the minimum pen size on this canvas. Will always be less than maximum pen size.
   * 
   * @return minimum pen size
   */
  public int getMinPenSize();

  /**
   * Draws a new shape on the canvas at the specified coordinates. The shape and the color are
   * determined by the pen attributes and the foreground color.
   * 
   * Notifies all listeners that drawing is in progress.
   *    
   * @param xCoordinate the x coordinate of the new shape
   * @param yCoordinate the y coordinate of the new shape
   */
  public void draw(int xCoordinate, int yCoordinate);
  
  /**
   * Notifies the canvas that new drawing is about to begin. This method needs to be called at 
   * least once in order to draw on a canvas. If this method is called only once then the 
   * {@link #undo()} will clear the whole canvas. Otherwise {@link #undo()} will remove what has 
   * been drawn between each {@link #beginDrawing} and {@link #finishDrawing()} calls. 
   * 
   * Notifies all listeners that drawing span has started.
   * 
   * @return true if it is ok to draw on this canvas or false otherwise
   * @throws IllegalStateException if called again without first calling {@link #finishDrawing()}
   */
  public boolean beginDrawing();
  
  /**
   * Notifies the canvas that this drawing span has come to an end. This method has to be called 
   * before calling {@link #beginDrawing()} again.
   * 
   * Notifies all listeners that current drawing span has ended.
   * 
   * @return true if finalizing this drawing span was successful or false otherwise
   * @throws IllegalStateException if called again without first calling {@link #beginDrawing()}
   */
  public boolean finishDrawing();
  
  /**
   * Removes what has been drawn on the canvas between {@link #beginDrawing()}  and 
   * {@link #finishDrawing()} invocations.
   * 
   * Notifies all listeners that canvas has changed.
   * 
   * @return true if undo was successful or false otherwise and they should repaint accordingly.
   */
  public boolean undo();
  
  /**
   * Clears the whole canvas.
   * 
   * Notifies all listeners that canvas content has changed and they should repaint accordingly.
   * 
   * @return true if canvas was cleared successfully or false otherwise
   */
  public boolean clearAll();

  /**
   * Registers a listener for canvas update notifications.
   * 
   * @param listener a listener interface object to be added
   * @return true if the listener was successfully added or false otherwise
   * @throws NullPointerException if <code>listener</code> is null
   */
  public boolean addListener(ICanvasListener listener);
  
  /**
   * Removes a listener from canvas update notifications.
   * 
   * @param listener a listener interface object to be removed.
   * @return true if the listener was successfully removed or false otherwise
   * @throws NullPointerException if <code>listener</code> is null
   */
  public boolean removeListener(ICanvasListener listener);
  
  /**
   * Creates a new and distinct ICanvas object.
   * 
   * @return new non-null ICanvas object
   */
  public ICanvas newInstance();
}
