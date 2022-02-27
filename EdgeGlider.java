import JWDEGUI.CustomCanvas;
import JWDEGUI.FileEditor;
import JWDEGUI.FileManager;
import JWDEGUI.GUIObject;
import JWDEGUI.GUIPainter;
import JWDEGUI.GUITemplate;
import JWDEGUI.GUITimer;
import JWDEGUI.ImageManager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

public class EdgeGlider {
  private static GUITemplate gui;
  
  private static JFrame window;
  
  private static Container gC;
  
  private static GUITimer treeTimer;
  
  private static GUITimer treeTimer2;
  
  private static CustomCanvas ccc;
  
  private static JPanel panel1;
  
  private static GUIPainter painter;
  
  private static JTextArea txtArea;
  
  private static GUIObject paneObj;
  
  private static ImageManager imgMan;
  
  private static FileManager fileMan;
  
  private static FileEditor settingsEd;
  
  private static MazeGenerator mzGen;
  
  private static MazeGraph mzGrid;
  
  private static MazeGraphics mzGUI;
  
  private static Player player;
  
  private static PlayerController controller;
  
  private static ArrayList<Mob> mobs;
  
  public static void main(String[] args) throws Throwable {
    gui = new GUITemplate("EdgeGlider Alpha Version 2");
    window = gui.getMainJFrame();
    gui.setJFrameSize(window, 100, 100, 800, 1000);
    gC = gui.makeContainer(window);
    gC.setLayout(new GridLayout(0, 2));
    imgMan = new ImageManager();
    painter = new GUIPainter(gC);
    ccc = painter.getCustomCanvas();
    panel1 = gui.makePanel();
    gC.add(panel1);
    paneObj = gui.createPane(gC, painter, panel1, 5, 5, 75, 75, false, 1, 1);

    // JScrollBar gameSBarV = gui.makeJScrollBar(JScrollBar.VERTICAL, 0, 5, 0, 5);
    JButton lvl1Button = gui.makeJButton("Play level 1");
    JButton lvl2Button = gui.makeJButton("Play level 2");
    JButton lvl3Button = gui.makeJButton("Play level 3");
    JButton lvl4Button = gui.makeJButton("Play level 4");
    lvl1Button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {
            if (EdgeGlider.mzGUI.isRunning())
              //return; 
            EdgeGlider.abortMobs();
            EdgeGlider.enableMaze(1);
          }
        });
    lvl2Button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {
            if (EdgeGlider.mzGUI.isRunning())
              //return; 
            EdgeGlider.abortMobs();
            EdgeGlider.enableMaze(2);
          }
        });
    lvl3Button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {
            if (EdgeGlider.mzGUI.isRunning())
              //return; 
            EdgeGlider.abortMobs();
            EdgeGlider.enableMaze(3);
          }
        });
    lvl4Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        if (EdgeGlider.mzGUI.isRunning()) {
          System.out.println("Can't generate maze - GUI is running");
          //return; 
        }
        EdgeGlider.abortMobs();
        EdgeGlider.enableMaze(4);
      }
    });
    panel1.setLayout(new GridLayout(0, 2));
    gui.setPos(window, 0, 0);
    gui.setSameBounds(gC, window);
    gui.setPos(gC, 0, 0);
    gui.setSameBounds(panel1, gC);
    gui.setPos(panel1, 0, 0);
    gui.setSameBounds((JComponent)ccc, panel1);
    gui.setPos((Container)ccc, 0, 0);
    gui.pack(window, gC);
    gui.setBounds(panel1, 100, 100, 1250, 750);
    gui.setBounds(lvl1Button, 125, 850, 130, 30);
    gui.setBounds(lvl2Button, 275, 850, 130, 30);
    gui.setBounds(lvl3Button, 425, 850, 130, 30);
    gui.setBounds(lvl4Button, 575, 850, 130, 30);
    // ccc.add(gameSBarV, BorderLayout.EAST);
    //gui.setBounds(gameSBarV, 1350, 100, 10, 750);
    // Make singular player controller
    controller = new PlayerController(null, 100L, mzGUI);

    lvl1Button.setFocusable(false);
    lvl2Button.setFocusable(false);
    lvl3Button.setFocusable(false);
    lvl4Button.setFocusable(false);
    panel1.setFocusable(false);
    gC.setFocusable(false);
    ccc.setFocusable(false);
    window.add(lvl1Button);
    window.add(lvl2Button);
    window.add(lvl3Button);
    window.add(lvl4Button);
    enableMaze(1);
    lvl1Button.setVisible(true);
    lvl2Button.setVisible(true);
    lvl3Button.setVisible(true);
    lvl4Button.setVisible(true);
  }
  
  public static void enableMaze(int lvl) {
    mobs = new ArrayList<>();
    switch (lvl) {
      case 1:
        // Original stats: 8, 8
        mzGen = new MazeGenerator(8, 8);
        break;
      case 2:
        mzGen = new MazeGenerator(11, 11);
        break;
      case 3:
        mzGen = new MazeGenerator(10, 14);
        break;
      case 4:
        mzGen = new MazeGenerator(16, 16);
        break;
      default:
        throw new IllegalArgumentException("Levels only from 1-4!!!");
    } 
    mzGrid = new MazeGraph();
    mzGUI = new MazeGraphics(painter, gui, mzGrid);
    mzGen.setGUI(mzGUI);
    mzGUI.setGameArea(paneObj);
    mzGrid.setGrid(mzGen.genMaze(1009L, 9999));
    BigNode start = mzGrid.getGrid()[0][0];
    start.setAdjacentNode("down", start.getAdjacentNode("down"));
    start.setAdjacentNode("right", start.getAdjacentNode("right"));
    player = (Player)mzGrid.getGrid()[0][0].getTile("1,1").getMob();
    controller.setPlayer(player);
    controller.setMazeGraphics(mzGUI);
    // controller = player.getController();
    window.addKeyListener(controller);
    window.setFocusable(true);
    switch (lvl) {
      case 1:
        mobs.add(mzGrid.getGrid()[3][1].addBasicEnemy("1,1", 0, 0));
        mobs.add(mzGrid.getGrid()[1][4].addBasicEnemy("1,1", 0, 100));
        mobs.add(mzGrid.getGrid()[7][4].addBasicEnemy("1,1", 0, 200));
        mzGrid.getGrid()[0][2].addObstacle("1,1");
        mzGrid.getGrid()[6][4].addObstacle("1,1");
        break;
      case 2:
        mobs.add(mzGrid.getGrid()[4][0].addBasicEnemy("1,1", 0, 0));
        mobs.add(mzGrid.getGrid()[0][4].addBasicEnemy("1,1", 0, 100));
        mobs.add(mzGrid.getGrid()[5][5].addBasicEnemy("1,1", 1, 50));
        mzGrid.getGrid()[2][2].addObstacle("1,1");
        mzGrid.getGrid()[2][3].addObstacle("1,1");
        mzGrid.getGrid()[3][4].addObstacle("1,1");
        mzGrid.getGrid()[2][5].addObstacle("1,1");
        mzGrid.getGrid()[2][6].addObstacle("1,1");
        mzGrid.getGrid()[5][4].addObstacle("2,1");
        mzGrid.getGrid()[7][3].addObstacle("1,2");
        break;
      case 3:
        mobs.add(mzGrid.getGrid()[1][9].addBasicEnemy("1,1", 1, 50));
        mobs.add(mzGrid.getGrid()[3][11].addBasicEnemy("1,1", 2, 270));
        mzGrid.getGrid()[1][8].addObstacle("1,0");
        mzGrid.getGrid()[1][8].addObstacle("2,0");
        mzGrid.getGrid()[3][8].addObstacle("2,0");
        mzGrid.getGrid()[3][8].addObstacle("2,1");
        mzGrid.getGrid()[6][9].addObstacle("2,0");
        mzGrid.getGrid()[6][9].addObstacle("2,1");
        break;
      case 4:
      mobs.add(mzGrid.getGrid()[8][8].addBasicEnemy("1,1", 2, 70));
      mobs.add(mzGrid.getGrid()[11][3].addBasicEnemy("1,1", 2, 270));
      mobs.add(mzGrid.getGrid()[8][8].addBasicEnemy("1,1", 1, 50));
      mobs.add(mzGrid.getGrid()[3][11].addBasicEnemy("1,1", 1, 100));
      mobs.add(mzGrid.getGrid()[14][14].addBasicEnemy("1,1", 0, 0));
      mzGrid.getGrid()[2][2].addObstacle("1,1");
      mzGrid.getGrid()[2][3].addObstacle("1,1");
      mzGrid.getGrid()[3][4].addObstacle("1,1");
      mzGrid.getGrid()[2][5].addObstacle("1,1");
      mzGrid.getGrid()[3][8].addObstacle("2,1");
      mzGrid.getGrid()[6][9].addObstacle("2,0");
      mzGrid.getGrid()[6][9].addObstacle("2,1");       
      break;

    } 
    mzGUI.drawMaze();
    ccc.repaint();
  }
  
  public static void abortMobs() {
    for (Mob m : mobs)
      m.abortAll(); 
  }
}
