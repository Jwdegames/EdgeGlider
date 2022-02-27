import JWDEGUI.GUIPainter;
import java.util.HashMap;

public abstract class GameObject {
  protected int layer = 0;
  
  protected HashMap<Integer, Boolean> collidableLayers;
  
  protected String type;
  
  protected TileNode currentNode;
  
  protected MazeGraphics gui;
  
  public abstract void draw(GUIPainter paramGUIPainter, int paramInt1, int paramInt2, double paramDouble);
  
  public String getType() {
    return this.type;
  }
}
