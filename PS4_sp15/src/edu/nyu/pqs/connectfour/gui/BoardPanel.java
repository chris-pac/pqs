package edu.nyu.pqs.connectfour.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.nyu.pqs.connectfour.api.IGameBoard;
import edu.nyu.pqs.connectfour.impl.Cell;
import edu.nyu.pqs.connectfour.impl.CellValue;

/**
 * package-private final helper class used exclusively to paint the connect four game board.
 * 
 * @author cpp270
 *
 */
final class BoardPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  
  private final Image playerOneCheckerImage;
  private final Image playerTwoCheckerImage;
  private final Image playerOneWinningCheckerImage;
  private final Image playerTwoWinningCheckerImage;
  
  private final int cellSize;
  private final int width;
  private final int height;
  
  private static final String playerOneIcon = "images/blackchecker.png";
  private static final String playerTwoIcon = "images/redchecker.png";
  private static final String playerOneWinningIcon = "images/graychecker.png";
  private static final String playerTwoWinningIcon = "images/pinkchecker.png";
  
  private IGameBoard board;
  
  public BoardPanel(IGameBoard board) {    
    this.board = board;
    
    playerOneCheckerImage = new ImageIcon(playerOneIcon).getImage();
    playerTwoCheckerImage = new ImageIcon(playerTwoIcon).getImage();

    playerOneWinningCheckerImage = new ImageIcon(playerOneWinningIcon).getImage();
    playerTwoWinningCheckerImage = new ImageIcon(playerTwoWinningIcon).getImage();

    cellSize = playerOneCheckerImage.getHeight(null);
    width = playerOneCheckerImage.getWidth(null) * board.getColumns();
    height = cellSize * board.getRows();
    
    setPreferredSize(new Dimension(width, height));
  
  }

  /**
   * Causes the panel to be updated with the new values contained in the game board. No repaint is
   * trigger because the parent repaints everything including this child panel.
   * 
   * @param board the current game board
   * @return
   */
  boolean updateGameBoard(IGameBoard board) {
    this.board = board;
    
    return true;
  }
  
  /*
   * (non-Javadoc)
   * Paints the appropriate board marks based on the values of each board cell.
   * 
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;
    g2d.setColor(Color.CYAN);
    g2d.fillRect(0, 0, width, height);
    
    Image icon = null;
    for (Cell c :  board) {
      if (c.getValue() == CellValue.EMPTY) {
        continue;
      }
      
      // set the icon based on the mark/value on the board cell
      switch (c.getValue()) {
        case PLAYER_ONE :
          icon = playerOneCheckerImage;
          break;
        case PLAYER_TWO :
          icon = playerTwoCheckerImage;
          break;
        case PLAYER_ONE_WINNER :
          icon = playerOneWinningCheckerImage;
          break;
        case PLAYER_TWO_WINNER :
          icon = playerTwoWinningCheckerImage;
          break;
        default :
          icon = null;
      }
      
      if (icon != null) {
        g2d.drawImage(icon, cellSize * c.getColumn(),
            cellSize * c.getRow(), this);                
      }
    }
  }
  
  /**
   * Returns the string location of the icon representing player one
   * @return player one's image location, the string can be null or empty
   */
  public String getPlayerOneIcon() {
    return playerOneIcon;
  }
  
  /**
   * Returns the string location of the icon representing player two
   * @return player two image location, the string can be null or empty
   */
  public String getPlayerTwoIcon() {
    return playerTwoIcon;
  }  
}
