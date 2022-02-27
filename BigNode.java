import JWDEGUI.GUIPainter;
import java.util.HashMap;

public class BigNode {
  private HashMap<String, BigNode> adjacentNodes;
  
  private HashMap<String, TileNode> tiles;
  
  private int id;
  
  private int x;
  
  private int y;
  
  private double scale = 1.5D;
  
  private MazeGraphics gui;
  
  public BigNode(int i, int xPos, int yPos, MazeGraphics g) {
    this.adjacentNodes = new HashMap<>();
    this.tiles = new HashMap<>();
    this.id = i;
    this.x = xPos;
    this.y = yPos;
    createEmpty();
    this.gui = g;
  }
  
  public int getID() {
    return this.id;
  }
  
  public int setID(int i) {
    int temp = this.id;
    this.id = i;
    return temp;
  }
  
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public BigNode getAdjacentNode(String key) {
    return this.adjacentNodes.get(key);
  }
  
  public void setAdjacentNode(String key, BigNode node) {
    int i, y, x;
    this.adjacentNodes.put(key, node);
    String opp = "";
    switch (key) {
      case "up":
        for (i = 0; i < 3; i++) 
          ((TileNode)this.tiles.get("" + i + ",0")).setAdjacentNode("up", node.getTile("" + i + ",2")); 
        opp = "down";
        break;
      case "left":
        for (y = 0; y < 3; y++)
          ((TileNode)this.tiles.get("0," + y)).setAdjacentNode("left", node.getTile("2," + y)); 
        opp = "right";
        break;
      case "right":
        for (y = 0; y < 3; y++)
          ((TileNode)this.tiles.get("2," + y)).setAdjacentNode("right", node.getTile("0," + y)); 
        opp = "left";
        break;
      case "down":
        for (x = 0; x < 3; x++)
          ((TileNode)this.tiles.get("" + x + ",2")).setAdjacentNode("down", node.getTile("" + x + ",0")); 
        opp = "up";
        break;
    } 
    node.getAdjacentNodes().put(opp, this);
  }
  
  public HashMap<String, BigNode> getAdjacentNodes() {
    return this.adjacentNodes;
  }
  
  public void setAdjacentNodes(HashMap<String, BigNode> nodes) {
    this.adjacentNodes = nodes;
  }
  
  public TileNode getTile(String coord) {
    return this.tiles.get(coord);
  }
  
  public void setTile(String coord, TileNode tile) {
    this.tiles.put(coord, tile);
  }
  
  public HashMap<String, TileNode> getTiles() {
    return this.tiles;
  }
  
  public void setTiles(HashMap<String, TileNode> t) {
    this.tiles = t;
  }
  
  public void createWalls(String dir) {
    BigNode temp;
    int y;
    int x;
    // System.out.println("Creating walls at (" + this.x + ", " + this.y + ")");
    switch (dir) {
      case "right":
        temp = getAdjacentNode("right");
        for (y = 0; y < 3; y++) {
          ((TileNode)this.tiles.get("2," + y)).addWall("right");
          if (temp != null)
            temp.getTile("0," + y).addWall("left"); 
        } 
        break;
      case "down":
        temp = getAdjacentNode("down");
        for (x = 0; x < 3; x++) {
          ((TileNode)this.tiles.get("" + x + ",2")).addWall("down");
          if (temp != null)
            temp.getTile("" + x + ",0").addWall("up"); 
        } 
        break;
    } 
  }
  
  
  public void createEmpty() {
    // Make the empty tyiles
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        TileNode temp = new TileNode(new Empty(), this, "" + this.id);
        this.tiles.put("" + x + "," + y, temp);
        temp.setParent(this);
        temp.x = 3 * this.x + x;
        temp.y = 3 * this.y + y;
      } 
    } 
    linkNodes();
  }
  
  public void drawNodes(GUIPainter p, int x, int y) {
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        TileNode temp = this.tiles.get("" + r + "," + c);
        ((GameObject)temp.getObj()).draw(p, (int)((x * 30) * this.scale + (10 * r) * this.scale), (int)((y * 30) * this.scale + (10 * c) * this.scale), this.scale);
        if (temp.getMob() != null)
          temp.getMob().draw(p, (int)((x * 30) * this.scale + (10 * r) * this.scale), (int)((y * 30) * this.scale + (10 * c) * this.scale), this.scale); 
      } 
    } 
  }
  
  public void drawMobs(GUIPainter p, int x, int y) {
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        TileNode temp = this.tiles.get("" + r + "," + c);
        if (temp.getMob() != null)
          temp.getMob().draw(p, (int)((x * 30) * this.scale + (10 * r) * this.scale), (int)((y * 30) * this.scale + (10 * c) * this.scale), this.scale); 
      } 
    } 
  }
  
  public void linkNodes() {
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        TileNode temp = this.tiles.get("" + x + "," + y);
        if (x > 0)
          temp.setAdjacentNode("left", this.tiles.get("" + (x - 1) + "," + y)); 
        if (x < 2)
          temp.setAdjacentNode("right", this.tiles.get("" + (x + 1) + "," + y)); 
        if (y > 0)
          temp.setAdjacentNode("up", this.tiles.get("" + x + "," + (y - 1))); 
        if (y < 2)
          temp.setAdjacentNode("down", this.tiles.get("" + x + "," + (y + 1))); 
      } 
    } 
  }
  
  public BasicEnemy addBasicEnemy(String key, int mode, int delay) {
    TileNode temp = this.tiles.get(key);
    BasicEnemy e = new BasicEnemy(temp, this.gui, mode, delay);
    temp.setMob(e);
    return e;
  }
  
  public void addObstacle(String key) {
    TileNode<Obstacle> temp = this.tiles.get(key);
    temp.setObj(new Obstacle(false));
  }
  
  public void specialize(int mode) {
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        ((TileNode)this.tiles.get("" + x + "," + y)).setNewEmpty(mode);
        if (x == 1 && y == 1 && mode == 1) {
          TileNode temp = this.tiles.get("" + x + "," + y);
          temp.setMob(new Player(temp, this.gui));
        } 
      } 
    } 
  }
}
