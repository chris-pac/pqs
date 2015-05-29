package edu.nyu.pqs.connectfour.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import edu.nyu.pqs.connectfour.api.IConnectFourListener;
import edu.nyu.pqs.connectfour.api.IGameBoard;
import edu.nyu.pqs.connectfour.impl.ConnectFour;
import edu.nyu.pqs.connectfour.impl.GameBoard;
import edu.nyu.pqs.connectfour.impl.GameResult;
import edu.nyu.pqs.connectfour.impl.GameType;

/**
 * The view object that handles game updates and GUI.
 * 
 * @author cpp270
 *
 */
public class GameView  implements IConnectFourListener {

  private JFrame frame = new JFrame();
  private BoardPanel boardPanel;
  
  private JRadioButtonMenuItem humanComputerItem;
  private JRadioButtonMenuItem computerHumanItem;
  private JRadioButtonMenuItem humanHumanItem;
  private GameType selectedGameType;
  private ButtonGroup gameTypeGroup;
  
  private ConnectFour model;
  private JButton[] columnDropButtons;

  public GameView(ConnectFour model) {
    if (model == null) {
      throw new NullPointerException("Model can not be null");
    }

    this.model = model;
    model.addListener(this);
    
    selectedGameType = model.getGameType();
    
    JPanel mainPanel = new JPanel();
    
    columnDropButtons = new JButton[GameBoard.COLUMNS];
    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new GridLayout(1, 7, 15, 6));
    for (int i = 0; i < GameBoard.COLUMNS; i++) {
      JButton button = new JButton(Integer.toString(i));
      button.addActionListener(humanMoveActionListener);
      buttonsPanel.add(button);
      columnDropButtons[i] = button;
    }
    
    mainPanel.add(buttonsPanel, BorderLayout.NORTH);
    
    // create the panel that will handle game board painting
    boardPanel = new BoardPanel(model.getBoard());    
    
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(mainPanel, BorderLayout.NORTH);
    frame.getContentPane().add(boardPanel, BorderLayout.CENTER);
    
    frame.setJMenuBar(createMenuBar());

    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
  }
  
  
  private ActionListener humanMoveActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        model.dropChecker(Integer.valueOf(e.getActionCommand()));
    }
  };

  /*
   * There are two ways this could be implemented. 
   * One: Save the game type and wait till the user starts a new game for the selected game type
   * to take effect OR
   * Two: Start a new game with the selected game type immediately.
   * 
   * I feel option two in the current GUI is more intuitive.
   */
  private ActionListener gameTypeActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      Object o = e.getSource();
      if (o.equals(humanComputerItem)) {
        selectedGameType = GameType.HUMAN_COMPUTER;
      } else if (o.equals(computerHumanItem)) {
        selectedGameType = GameType.COMPUTER_HUMAN;
      } else if (o.equals(humanHumanItem)) {
        selectedGameType = GameType.HUMAN_HUMAN;
      }
      
      model.newGame(selectedGameType);
    }
  };
  
  private ActionListener newGameActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      model.newGame(selectedGameType);
    }
  };

  private ActionListener closeActionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      frame.dispose();
    }
  };

  private JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");

    JMenuItem newGame = new JMenuItem("New Game");
    newGame.addActionListener(newGameActionListener);
    fileMenu.add(newGame);
    fileMenu.add(new JSeparator());
    
    JMenuItem closeItem = new JMenuItem("Close");
    closeItem.addActionListener(closeActionListener);
    fileMenu.add(closeItem);

    JMenu optionsMenu = new JMenu("Options");
    humanComputerItem = new JRadioButtonMenuItem(GameType.HUMAN_COMPUTER.toString(), 
        selectedGameType == GameType.HUMAN_COMPUTER);
    humanComputerItem.addActionListener(gameTypeActionListener);
    computerHumanItem = new JRadioButtonMenuItem(GameType.COMPUTER_HUMAN.toString(), 
        selectedGameType == GameType.COMPUTER_HUMAN);
    computerHumanItem.addActionListener(gameTypeActionListener);
    humanHumanItem = new JRadioButtonMenuItem(GameType.HUMAN_HUMAN.toString(), 
        selectedGameType == GameType.HUMAN_HUMAN);
    humanHumanItem.addActionListener(gameTypeActionListener);
    
    optionsMenu.add(humanComputerItem);
    optionsMenu.add(computerHumanItem);
    optionsMenu.add(humanHumanItem);
    
    gameTypeGroup = new ButtonGroup();
    gameTypeGroup.add(humanComputerItem);
    gameTypeGroup.add(computerHumanItem);
    gameTypeGroup.add(humanHumanItem);
    
    menuBar.add(fileMenu);
    menuBar.add(optionsMenu);
    
    return menuBar;
  }

  @Override
  public void gameStart(GameType type) {
    selectedGameType = type;
    humanComputerItem.setSelected(selectedGameType == GameType.HUMAN_COMPUTER);
    computerHumanItem.setSelected(selectedGameType == GameType.COMPUTER_HUMAN);
    humanHumanItem.setSelected(selectedGameType == GameType.HUMAN_HUMAN);
    
    updateColumnButtonsStatus(null);

    frame.repaint();
  }

  @Override
  public void gameUpdate(IGameBoard updatedBoard) {
    boardPanel.updateGameBoard(updatedBoard);
    
    updateColumnButtonsStatus(updatedBoard);
    
    frame.repaint();    
  }

  @Override
  public void gameEnd(GameResult result) {
    frame.repaint();    
    
    Icon icon = null;
    if (result == GameResult.PLAYER_ONE_WINS) {
      icon = new ImageIcon(boardPanel.getPlayerOneIcon()); 
    } else if (result == GameResult.PLAYER_TWO_WINS) {
      icon = new ImageIcon(boardPanel.getPlayerTwoIcon());      
    }
    
    JOptionPane pane = new JOptionPane(result.toString(), JOptionPane.INFORMATION_MESSAGE, 
        JOptionPane.DEFAULT_OPTION, icon);
    
    JDialog dialog = pane.createDialog(frame, "Game Result");
    dialog.setVisible(true);    
  }

  /*
   * Helper method to handle the UI setting of buttons. If board is null all buttons will be
   * reset back to default status.
   */
  private void updateColumnButtonsStatus(IGameBoard board) {
    if (board == null) {
      for (int col = 0; col < GameBoard.COLUMNS; col++) {
        columnDropButtons[col].setEnabled(true);
      }
      return;
    }
    
    for (int col = 0; col < board.getColumns(); col++) {
      columnDropButtons[col].setEnabled(board.isColumnAvailable(col));
    }
  }
}
