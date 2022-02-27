import JWDEGUI.GUIPainter;
import java.awt.Color;

public class Empty extends GameObject {
  private int mode;
  
  public Empty() {
    this.type = "Empty";
    this.mode = 0;
  }
  
  public Empty(int m) {
    this.type = "Empty";
    this.mode = m;
  }
  
  public int getMode() {
    return this.mode;
  }
  
  public void draw(GUIPainter p, int x, int y, double scale) {
    Color color;
    switch (this.mode) {
      case 0:
        color = Color.WHITE;
        break;
      case 1:
        color = Color.CYAN;
        break;
      case 2:
        color = Color.GREEN;
        break;
      default:
        throw new IllegalArgumentException("ILLEGAL COLOR!");
    } 
    p.fillRect((x + 1), (y + 1), 9.0D * scale, 9.0D * scale, color, "");
  }
}
