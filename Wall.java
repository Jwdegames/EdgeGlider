import JWDEGUI.GUIPainter;
import java.awt.Color;
import java.util.HashMap;

public class Wall extends GameObject {
  private HashMap<String, Boolean> edges;
  
  public Wall(String d) {
    this.type = "Wall";
    this.edges = new HashMap<>();
    setDir("up", Boolean.valueOf(false));
    setDir("left", Boolean.valueOf(false));
    setDir("right", Boolean.valueOf(false));
    setDir("down", Boolean.valueOf(false));
    setDir(d, Boolean.valueOf(true));
  }
  
  public void draw(GUIPainter p, int x, int y, double scale) {
    if (((Boolean)this.edges.get("right")).booleanValue() == true)
      p.drawLine(x + 10.0D * scale, y, x + 10.0D * scale, y + 10.0D * scale, Color.BLACK, ""); 
    if (((Boolean)this.edges.get("down")).booleanValue() == true)
      p.drawLine(x, y + 10.0D * scale, x + 10.0D * scale, y + 10.0D * scale, Color.BLACK, ""); 
  }
  
  public void setDir(String d, Boolean b) {
    this.edges.put(d, b);
  }
  
  public boolean getDir(String d) {
    return ((Boolean)this.edges.get(d)).booleanValue();
  }
}
