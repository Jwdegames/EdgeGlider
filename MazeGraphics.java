import JWDEGUI.GUIPainter;
import JWDEGUI.GUITemplate;
import JWDEGUI.GUIObject;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

public class MazeGraphics {
  private GUIPainter painter;
  
  private GUITemplate gui;
  
  private MazeGraph graph;

  private GUIObject gameArea;
  
  private boolean running = true;
  
  private TileNode playerNode;
  
  private JLabel statusField;
  
  private boolean won;
  
  public MazeGraphics(GUIPainter p, GUITemplate g, MazeGraph gr) {
    this.painter = p;
    this.gui = g;
    this.graph = gr;
  }
  
  public TileNode getPlayerTile() {
    return this.playerNode;
  }
  
  public void setPlayerTile(TileNode p) {
    this.playerNode = p;
  }
  
  public void lose() {
    System.out.println("We lost! :(");
    this.running = false;
    this.won = false;
    this.painter.makeText("OOF! YOU LOSE!", new Font("Impact", 1, 50), 150.0D, 200.0D, Color.black, "");
  }
  
  public void win() {
    System.out.println("We win! :)");
    this.running = false;
    this.won = true;
    this.painter.fillRect2T(100.0D, 100.0D, 500.0D, 200.0D, Color.WHITE, "");
    this.painter.makeText("A WINNER IS YOU!", new Font("Impact", 1, 50), 150.0D, 200.0D, Color.black, "");
    updateScrollBars(0, 0);
  }
  
  public boolean isWin() {
    return this.won;
  }
  
  public boolean isRunning() {
    return this.running;
  }
  
  public void setRunning(boolean b) {
    this.running = b;
  }
  
  public GUIPainter getPainter() {
    return this.painter;
  }

  public void setGameArea(GUIObject gA) {
      gameArea = gA;
  }

  public GUIObject getGameArea() {
      return gameArea;
  }

  // Used by player to update scroll bars when it moves
  public void updateScrollBars(int x, int y) {
      gui.setPaneViewCoords(gameArea, x, y);
  }
  
  public void drawMaze() {
    // running = true;
    this.painter.getCustomCanvas().clearCMDs();
    this.painter.getCustomCanvas().clear2CMDS();
    BigNode[][] grid = this.graph.getGrid();
    for (int r = 0; r < grid.length; r++) {
      for (int c = 0; c < (grid[r]).length; c++) {
        BigNode temp = grid[r][c];
        temp.drawNodes(this.painter, r + 1, c + 1);
      } 
    } 
    this.painter.drawRect(45.0D, 45.0D, (grid.length * 45), ((grid[0]).length * 45), Color.BLACK, "");
  }
  
  public void drawMobs() {
    // running = true;
    BigNode[][] grid = this.graph.getGrid();
    for (int r = 0; r < grid.length; r++) {
      for (int c = 0; c < (grid[r]).length; c++) {
        BigNode temp = grid[r][c];
        temp.drawMobs(this.painter, r + 1, c + 1);
      } 
    } 
    this.painter.getCustomCanvas().repaint();
  }
}
