package JWDEGUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class GUIPainter extends JComponent {
  private static JFrame mainJFrame;
  
  private static CustomCanvas customCanvas;
  
  private static Container container;
  
  public GUIPainter(JFrame jFrame) {
    mainJFrame = jFrame;
  }
  
  public GUIPainter(Container c) {
    container = c;
    customCanvas = new CustomCanvas();
    container.add(customCanvas);
  }
  
  public void setColor(Color color) {
    customCanvas.setColor(color);
  }
  
  public void drawOval(double x, double y, double rad1, double rad2, Color color, String debug) {
    ArrayList<Object> temp = new ArrayList();
    temp.add(Double.valueOf(x));
    temp.add(Double.valueOf(y));
    temp.add(Double.valueOf(rad1));
    temp.add(Double.valueOf(rad2));
    temp.add(color);
    temp.add(debug);
    customCanvas.addCommand(new Command("drawOval", temp));
  }
  
  public void fillOval(double x, double y, double rad1, double rad2, Color color, String debug) {
    ArrayList<Object> temp = new ArrayList();
    temp.add(Double.valueOf(x));
    temp.add(Double.valueOf(y));
    temp.add(Double.valueOf(rad1));
    temp.add(Double.valueOf(rad2));
    temp.add(color);
    temp.add(debug);
    customCanvas.addCommand(new Command("fillOval", temp));
  }
  
  public void drawRect(double x, double y, double w, double h, Color color, String debug) {
    ArrayList<Object> temp = new ArrayList();
    temp.add(Double.valueOf(x));
    temp.add(Double.valueOf(y));
    temp.add(Double.valueOf(w));
    temp.add(Double.valueOf(h));
    temp.add(color);
    temp.add(debug);
    customCanvas.addCommand(new Command("drawRect", temp));
  }
  
  public void fillRect(double x, double y, double w, double h, Color color, String debug) {
    ArrayList<Object> temp = new ArrayList();
    temp.add(Double.valueOf(x));
    temp.add(Double.valueOf(y));
    temp.add(Double.valueOf(w));
    temp.add(Double.valueOf(h));
    temp.add(color);
    temp.add(debug);
    customCanvas.addCommand(new Command("fillRect", temp));
  }
  
  public void fillRect2T(double x, double y, double w, double h, Color color, String debug) {
    ArrayList<Object> temp = new ArrayList();
    temp.add(Double.valueOf(x));
    temp.add(Double.valueOf(y));
    temp.add(Double.valueOf(w));
    temp.add(Double.valueOf(h));
    temp.add(color);
    temp.add(debug);
    customCanvas.addCommand2T(new Command("fillRect", temp));
  }
  
  public void drawRect2T(double x, double y, double w, double h, Color color, String debug) {
    ArrayList<Object> temp = new ArrayList();
    temp.add(Double.valueOf(x));
    temp.add(Double.valueOf(y));
    temp.add(Double.valueOf(w));
    temp.add(Double.valueOf(h));
    temp.add(color);
    temp.add(debug);
    customCanvas.addCommand2T(new Command("drawRect", temp));
  }
  
  public void drawLine(double x1, double y1, double x2, double y2, Color color, String debug) {
    ArrayList<Object> temp = new ArrayList();
    temp.add(Double.valueOf(x1));
    temp.add(Double.valueOf(y1));
    temp.add(Double.valueOf(x2));
    temp.add(Double.valueOf(y2));
    temp.add(color);
    temp.add(debug);
    customCanvas.addCommand(new Command("drawLine", temp));
  }
  
  public void makeText(String s, Font f, double x, double y, Color color, String debug) {
    ArrayList<Object> temp = new ArrayList();
    temp.add(s);
    temp.add(f);
    temp.add(Double.valueOf(x));
    temp.add(Double.valueOf(y));
    temp.add(color);
    temp.add(debug);
    customCanvas.addCommand(new Command("makeText", temp));
  }
  
  public void deleteCMD(int index) {
    ArrayList<Object> temp = new ArrayList();
    temp.add(Integer.valueOf(index));
    customCanvas.addCommand(new Command("deleteCMD", temp));
  }
  
  public void dCMDFromArray(Command c, String debug) {
    ArrayList<Object> temp = new ArrayList();
    temp.add(c);
    temp.add(debug);
    Command cmd = new Command("dCMDFromArray", temp);
    customCanvas.addCommand(cmd);
  }
  
  public void changeCMDColor(int index, Color color, String debug) {
    ArrayList<Object> temp = new ArrayList();
    temp.add(Integer.valueOf(index));
    temp.add(color);
    temp.add(debug);
    customCanvas.addCommandPT(0, new Command("changeCMDColor", temp));
  }
  
  public void mLB(int index, String debug) {
    ArrayList<Object> temp = new ArrayList();
    temp.add(Integer.valueOf(index));
    temp.add(debug);
    customCanvas.addCommandPT(0, new Command("mLB", temp));
  }
  
  public void sendCMD(Command cmd, String debug) {
    ArrayList<Object> temp = new ArrayList();
    temp.add(cmd);
    temp.add(debug);
    customCanvas.addCommand(0, new Command("sendCMD", temp));
  }
  
  public CustomCanvas getCustomCanvas() {
    return customCanvas;
  }
  
  public Graphics2D getGraphics() {
    return customCanvas.getGraphics();
  }
  
  public void clear2T() {
    customCanvas.clear2CMDS();
  }
}
