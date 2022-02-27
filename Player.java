import JWDEGUI.GUIPainter;
import java.awt.Color;

public class Player extends GameObject implements Mob {
  private long delay = 100L;
  
  private boolean canMove = true;
  
  private static PlayerController controller;
  
  private GUIPainter painter;
  
  private MazeGraphics gui;
  
  public Player(TileNode cN, MazeGraphics g) {
    this.type = "Player";
    // Now handled by EdgeGlider.Java
    // Prevent duplicates by using a static instance
    if (controller == null) {
      //controller = new PlayerController(this, this.delay, g);
    } else {
      //controller.setPlayer(this);
    }
    System.out.println("Made a new player");
    this.currentNode = cN;
    this.gui = g;
    this.gui.setPlayerTile(cN);
  }
  
  public void abortAll() {
    this.controller.abort();
  }
  
  public PlayerController getController() {
    return this.controller;
  }
  
  public void draw(GUIPainter p, int x, int y, double scale) {
    p.fillRect2T((x + 1), (y + 1), 9.0D * scale, 9.0D * scale, Color.BLUE, "");
    this.painter = p;
  }
  
  public TileNode getCurrentNode() {
    return this.currentNode;
  }
  
  public void setCurrentNode(TileNode n) {
    // System.out.println("Changing player to node " + n + " from node " + currentNode);
    this.currentNode = n;
    this.gui.setPlayerTile(n);
    if (n.getObj() instanceof Empty && (
      (Empty)n.getObj()).getMode() == 2)
      this.gui.win(); 
    if (n.getMob() != null && 
      n.getMob() instanceof BasicEnemy)
      this.gui.lose(); 
    // System.out.println("After change, the current node is " + currentNode);
  }
  
  public void move(String dir) {
    // System.out.println("Current node is " + currentNode);
    this.currentNode.moveMob(this.currentNode.getAdjacentNode(dir));
    // System.out.println("Moved to " + currentNode);
    // Edit scroll bars if need be
    gui.updateScrollBars((int)(currentNode.x * 4), (int)(currentNode.y * 4));
    this.painter.clear2T();
    this.gui.drawMobs();
  }
}
