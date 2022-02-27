import JWDEGUI.GUIPainter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

public class BasicEnemy extends GameObject implements Mob {
  private int mode;
  
  private long delay;
  
  private long timer;
  
  private GUIPainter painter;
  
  private boolean canMove = false;
  
  private HashSet<TileNode> checked;
  
  private PriorityQueue<TileNode> toCheck;
  
  private int priority;
  
  private boolean abort = false;
  
  private Thread t;
  
  public BasicEnemy(TileNode cN, MazeGraphics g, int m, int initT) {
    this.currentNode = cN;
    this.gui = g;
    this.type = "BasicEnemy";
    this.mode = m;
    switch (this.mode) {
      case 0:
        this.delay = 1500L;
        break;
      case 1:
        this.delay = 750L;
        break;
      case 2:
        this.delay = 375L;
        break;
      default:
        throw new IllegalArgumentException("INVALID MODE: " + this.mode + ". MODE SHOULD BE FROM 0 to 2");
    } 
    this.painter = g.getPainter();
    this.timer = initT;
    startActions();
  }
  
  public void abortAll() {
    this.abort = true;
    this.painter = null;
    this.checked = null;
    this.toCheck = null;
    this.t.stop();
    this.t = null;
  }
  
  public void startActions() {
    if (this.abort == true)
      return; 
    this.t = new Thread(new Runnable() {
          public void run() {
            try {
              Thread.sleep(BasicEnemy.this.timer);
            } catch (Exception exception) {}
            while (BasicEnemy.this.gui.isRunning() && !BasicEnemy.this.abort) {
              ArrayList<TileNode> path = new ArrayList<>(), oldPath = new ArrayList<>();
              path = BasicEnemy.this.findPlayer(BasicEnemy.this.currentNode);
              if (BasicEnemy.this.abort)
                return; 
              try {
                if (path.size() > 0)
                  BasicEnemy.this.moveNode(path.remove(0)); 
              } catch (Exception exception) {}
              try {
                Thread.sleep(BasicEnemy.this.delay);
              } catch (Exception e) {
                System.out.println(e);
              } 
            } 
          }
        });
    this.t.start();
  }
  
  public void draw(GUIPainter p, int x, int y, double scale) {
    Color color;
    if (this.abort)
      return; 
    switch (this.mode) {
      case 0:
        color = Color.YELLOW;
        break;
      case 1:
        color = Color.ORANGE;
        break;
      case 2:
        color = Color.RED;
        break;
      default:
        throw new IllegalArgumentException("INVALID MODE: " + this.mode + ". MODE SHOULD BE FROM 0 to 2");
    } 
    p.fillRect2T((x + 1), (y + 1), 9.0D * scale, 9.0D * scale, color, "");
    p.drawRect2T((x + 1), (y + 1), 9.0D * scale, 9.0D * scale, Color.PINK, "");
  }
  
  public TileNode getCurrentNode() {
    return this.currentNode;
  }
  
  public void setCurrentNode(TileNode n) {
    this.currentNode = n;
    if (n.getMob() != null && 
      n.getMob() instanceof Player)
      this.gui.lose(); 
  }
  
  public void move(String dir) {
    if (this.abort)
      return; 
    this.currentNode.moveMob(this.currentNode.getAdjacentNode(dir));
    this.painter.clear2T();
    this.gui.drawMobs();
  }
  
  public void moveNode(TileNode node) {
    if (this.abort)
      return; 
    if (this.currentNode.moveMobSafe(this, node)) {
      this.painter.clear2T();
      this.gui.drawMobs();
    } 
  }
  
  public int heuristicDistance(TileNode t1, TileNode t2) {
    return Math.abs(t1.x - t2.x) + Math.abs(t1.y - t2.y);
  }
  
  class BasicEnemyComparator implements Comparator<BasicEnemy> {
    public int compare(BasicEnemy be1, BasicEnemy be2) {
      if (be1.priority > be2.priority)
        return -1; 
      if (be2.priority > be1.priority)
        return 1; 
      return 0;
    }
  }
  
  public boolean hasNode(Collection c, MobHeuristics mob) {
    Iterator i = c.iterator();
    while (i.hasNext()) {
      Object obj = i.next();
      if (((MobHeuristics)obj).tile == mob.tile)
        return true; 
    } 
    return false;
  }
  
  public synchronized ArrayList<TileNode> findPlayer(TileNode start) {
    PriorityQueue<TileNode> openList = new PriorityQueue<>(1000, new TileNodeComparator());
    PriorityQueue<TileNode> closedList = new PriorityQueue<>(1000, new TileNodeComparator());
    ArrayList<TileNode> path = null;
    HashSet<TileNode> checked = new HashSet<>();
    openList.add(start);
    checked.add(start);
    TileNode toFind = this.gui.getPlayerTile();
    start.f = heuristicDistance(start, toFind);
    boolean found = false;
    while (openList.size() > 0) {
      TileNode current = openList.poll();
      if (current == toFind) {
        found = true;
        break;
      } 
      closedList.add(current);
      ArrayList<TileNode> children = new ArrayList<>();
      children.add(current.getAdjacentNode("up"));
      children.add(current.getAdjacentNode("left"));
      children.add(current.getAdjacentNode("right"));
      children.add(current.getAdjacentNode("down"));
      for (TileNode child : children) {
        if (child == null || child.getObj() instanceof Obstacle || closedList.contains(child))
          continue; 
        if (!checked.contains(child) && child.f > 0)
          System.out.println("Node isn't 0'd: " + child.f + " for node " + child); 
        int pf = child.f;
        current.g++;
        child.h = heuristicDistance(child, toFind);
        child.f = child.g + child.h;
        checked.add(child);
        if (pf != 0 && pf < child.f && openList.contains(child))
          continue; 
        openList.remove(child);
        openList.add(child);
        if (pf >= child.f || pf == 0)
          child.setPredecessor(current); 
      } 
    } 
    if (found) {
      TileNode t = toFind, t2 = t;
      path = new ArrayList<>();
      while (t != null) {
        path.add(t);
        t = t.getPredecessor();
        t2.setPredecessor(null);
        t2 = t;
      } 
      Collections.reverse(path);
      path.remove(0);
    } else {
      System.out.println("We failed!");
    } 
    for (TileNode t : checked)
      t.f = t.g = t.h = 0; 
    return path;
  }
}
