package JWDEGUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class ImageManager {
  private ArrayList<BufferedImage> images = new ArrayList<>();
  
  private String dir = System.getProperty("user.dir");
  
  public BufferedImage addImg(String name) throws Throwable {
    String fN = "Images\\" + name;
    BufferedImage bI = ImageIO.read(new File(fN));
    this.images.add(bI);
    return bI;
  }
  
  public ArrayList<BufferedImage> getImages() {
    return this.images;
  }
  
  public BufferedImage getImage(int index) {
    return this.images.get(index);
  }
}
