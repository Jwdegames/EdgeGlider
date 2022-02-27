import java.util.HashMap;

public class TileNode<T> {
  private Object obj;
  
  private GameObject mob;
  
  private HashMap<String, TileNode<T>> adjacentNodes;
  
  private HashMap<String, TileNode<T>> ghostNodes;
  
  private BigNode parent;
  
  private String id;
  
  private TileNode predecessor;
  
  public TileNode successor = null;
  
  private int priority;
  
  public int x = 0, y = 0, h = 0, f = 0, g = 0;
  
  public TileNode(Object o, BigNode p, String s) {
    this.obj = o;
    this.adjacentNodes = new HashMap<>();
    this.ghostNodes = new HashMap<>();
    this.parent = p;
    this.id = s;
  }
  
  public TileNode getPredecessor() {
    return this.predecessor;
  }
  
  public void setPredecessor(TileNode p) {
    this.predecessor = p;
  }
  
  public void setPriority(int p) {
    this.priority = p;
  }
  
  public int getPriority() {
    return this.priority;
  }
  
  public BigNode getParent() {
    return this.parent;
  }
  
  public void setParent(BigNode p) {
    this.parent = p;
  }
  
  public Object getObj() {
    return this.obj;
  }
  
  public void setObj(T o) {
    this.obj = o;
  }
  
  public GameObject getMob() {
    return this.mob;
  }
  
  public void setMob(GameObject gO) {
    this.mob = gO;
  }
  
  public TileNode<T> getAdjacentNode(String key) {
    return this.adjacentNodes.get(key);
  }
  
  public TileNode<T> getGhostNode(String key) {
    return this.ghostNodes.get(key);
  }
  
  public void setAdjacentNode(String key, TileNode<T> node) {
    this.adjacentNodes.put(key, node);
    this.ghostNodes.put(key, node);
    String opp = "";
    switch (key) {
      case "up":
        opp = "down";
        break;
      case "left":
        opp = "right";
        break;
      case "right":
        opp = "left";
        break;
      case "down":
        opp = "up";
        break;
    } 
    node.getAdjacentNodes().put(opp, this);
    node.getGhostNodes().put(opp, this);
  }
  
  public HashMap<String, TileNode<T>> getAdjacentNodes() {
    return this.adjacentNodes;
  }
  
  public void setAdjacentNodes(HashMap<String, TileNode<T>> nodes) {
    this.adjacentNodes = nodes;
  }
  
  public HashMap<String, TileNode<T>> getGhostNodes() {
    return this.ghostNodes;
  }
  
  public void setGhostNodes(HashMap<String, TileNode<T>> nodes) {
    this.ghostNodes = nodes;
  }
  
  public void addWall(String dir) {
    boolean alreadyWalled = ((GameObject)this.obj).getType().equals("Wall");
    // System.out.println("Already made a wall: " + alreadyWalled);
    if (!alreadyWalled) {
      this.obj = new Wall(dir);
      // System.out.println("Made new wall in direction " + dir);
    } else {
      ((Wall)this.obj).setDir(dir, Boolean.valueOf(true));
    } 
    if (this.adjacentNodes.get(dir) != null) {
      String opp = "";
      switch (dir) {
        case "up":
          opp = "down";
          break;
        case "left":
          opp = "right";
          break;
        case "right":
          opp = "left";
          break;
        case "down":
          opp = "up";
          break;
      } 
      ((TileNode)this.adjacentNodes.get(dir)).getAdjacentNodes().put(opp, null);
      this.adjacentNodes.put(dir, null);
    } 
  }
  
  public void removeWall(String dir) {
    if (!((GameObject)this.obj).getType().equals("Wall"))
      return; 
    ((Wall)this.obj).setDir(dir, Boolean.valueOf(false));
  }
  
  public void moveMob(TileNode node) {
    try {
      ((Mob)this.mob).setCurrentNode(node);
    } catch (Exception exception) {}
    node.setMob(this.mob);
    this.mob = null;
  }
  
  public boolean moveMobSafe(Mob mob, TileNode node) {
    if (node.getMob() == null || node.getMob() instanceof Player) {
      mob.setCurrentNode(node);
      node.setMob((GameObject)mob);
      if (mob == this.mob)
        this.mob = null; 
      return true;
    } 
    return false;
  }
  
  public void setNewEmpty(int mode) {
    this.obj = new Empty(mode);
  }
  
  public String toString() {
    String toRet = id +":[" + x + "," + y + "]";
    return toRet;
  }
}
