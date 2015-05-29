package edu.nyu.pqs.canvas.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import edu.nyu.pqs.canvas.api.ICShape;
import edu.nyu.pqs.canvas.api.ICanvas;

/**
 * This panel used to display/paint the contents of the canvas. This is done by iterating over the
 * shape objects in the canvas and rendering them according to their values.
 * 
 * @author cpp270
 *
 */
class SurfacePanel extends JPanel {
  private static final long serialVersionUID = 1L;

  ICanvas canvas;
  
  public SurfacePanel(ICanvas canvas) {
    this.canvas = canvas;
        
    this.addMouseListener(mouseClickListener);
    this.addMouseMotionListener(mouseDraggedListener);
    
    setPreferredSize(new Dimension(300, 800));
    setVisible( true );
  }
  
  /*
   * Notifies the canvas about start and end of a drawing span.
   */
  private MouseAdapter mouseClickListener = new MouseAdapter() {
    public void mousePressed(MouseEvent e) {
      canvas.beginDrawing();      
    }
    
    public void mouseReleased(MouseEvent e) {
      canvas.finishDrawing();      
    }
  };

  /*
   * Notifies the canvas what's currently being drawn.
   */
  private MouseAdapter mouseDraggedListener = new MouseAdapter() {
    public void mouseDragged(MouseEvent e) {
      canvas.draw(e.getX(), e.getY());
    }
  };
  
  /*
   * (non-Javadoc)
   * Paints each shape object on the canvas.
   * 
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;
    super.paintComponent(g);
    
    // make drawing antialias
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY );
    
    // first paint the background
    Color color = new Color(canvas.getBackgroundColor());
    g2d.setColor(color);
    g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    
    // now paint all the shapes on the canvas
    int rgbColor = 0;
    for (ICShape cs : canvas) {
      // no need to create a new Color if it hasn't changed between each shape.
      if (rgbColor != cs.getColor() || color == null) {
        rgbColor = cs.getColor();
        color = new Color(rgbColor);
      }
      
      // set the color of the shape to be painted
      g2d.setColor(color);
      
      switch(cs.getPenShape()) {
        case ROUND :
          g2d.fillOval( cs.getX(), cs.getY(), cs.getPenSize(), cs.getPenSize() );
          break;
        case SQUARE :
          g2d.fillRect(cs.getX(), cs.getY(), cs.getPenSize(), cs.getPenSize());
          break;
        default :
          throw new IllegalStateException("Unknow shape");
      }
    }

    // release memory of graphics2d after finished drawing with it.
    g2d.dispose();
  }
}
