import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener {
  private Player player;
  
  private long delay;
  
  private long timer;
  
  private boolean canMove = true;
  
  private MazeGraphics gui;
  
  private boolean aborted = false;

  private boolean inUse;

  private int count = 0;

  private KeyEvent currentEvent = null;
  
  public PlayerController(Player p, long d, MazeGraphics g) {
    this.player = p;
    this.delay = d;
    this.gui = g;
    System.out.println("New controller made");
  }
  
  public void abort() {
    this.aborted = true;
  }

  public void setPlayer(Player p) {
    player = p;
  }

  public void setMazeGraphics(MazeGraphics g) {
    gui = g;
  }
  
  public void keyPressed(KeyEvent e) {
    // Prevent duplicate events
    if (e.equals(currentEvent)) return;
    inUse = true;
    currentEvent = e;
    // System.out.println(count++);
    TileNode temp = this.player.getCurrentNode();
    // System.out.println("At key press, current node is " + temp);
    if ((!this.canMove && System.currentTimeMillis() - this.timer < this.delay) || !this.gui.isRunning() || this.aborted)
      return; 
    if (System.currentTimeMillis() - this.timer >= this.delay)
      this.canMove = true; 
    
    if (e.getKeyCode() == 39) {
      if (temp.getAdjacentNode("right") != null && !(temp.getAdjacentNode("right").getObj() instanceof Obstacle)) {
        this.player.move("right");
        this.timer = System.currentTimeMillis();
        this.canMove = false;
      } 
    } else if (e.getKeyCode() == 37) {
      if (temp.getAdjacentNode("left") != null && !(temp.getAdjacentNode("left").getObj() instanceof Obstacle)) {
        this.player.move("left");
        this.timer = System.currentTimeMillis();
        this.canMove = false;
      } 
    } else if (e.getKeyCode() == 38) {
      if (temp.getAdjacentNode("up") != null && !(temp.getAdjacentNode("up").getObj() instanceof Obstacle)) {
        this.player.move("up");
        this.timer = System.currentTimeMillis();
        this.canMove = false;
      } 
    } else if (e.getKeyCode() == 40 && 
      temp.getAdjacentNode("down") != null && !(temp.getAdjacentNode("down").getObj() instanceof Obstacle)) {
      this.player.move("down");
      this.timer = System.currentTimeMillis();
      this.canMove = false;
    } 
    // System.out.println("At conclusion of key press, current node is " + player.getCurrentNode());
    inUse = false;
  }
  
  public void keyReleased(KeyEvent e) {}
  
  public void keyTyped(KeyEvent e) {}
}
