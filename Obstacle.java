import JWDEGUI.GUIPainter;
import java.awt.Color;

public class Obstacle extends GameObject {
  private Color color;
  
  private boolean mazeWall;
  
  public Obstacle(boolean maze) {
    if (maze) {
      this.color = Color.BLACK;
      this.mazeWall = true;
    } else {
      this.color = Color.RED;
      this.mazeWall = false;
    } 
  }
  
  public void draw(GUIPainter p, int x, int y, double scale) {
    p.fillRect(x, y, 10.0D * scale, 10.0D * scale, this.color, "");
  }
  
  public void setColor(Color c) {
    this.color = c;
  }
}
