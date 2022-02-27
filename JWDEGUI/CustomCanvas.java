package JWDEGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JComponent;

public class CustomCanvas extends JComponent {
  private Graphics g;
  
  private Graphics2D g2;
  
  private int cmdCount = -1;
  
  private ArrayList<Command> commands = new ArrayList<>();
  
  private ArrayList<Command> previousCMDs = new ArrayList<>();
  
  private ArrayList<Command> primaryTimedCMDs = new ArrayList<>();
  
  private ArrayList<Command> secondaryTimedCMDs = new ArrayList<>();
  
  public boolean needsToCheck = false;
  
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.g = g;
    this.g2 = (Graphics2D)g;
    g.setColor(Color.BLACK);
    if (this.needsToCheck) {
      removeDuplicateCommands();
      this.needsToCheck = false;
    } 
    int i;
    for (i = 0; i < this.primaryTimedCMDs.size(); i++) {
      Command temp, temp2, c = this.primaryTimedCMDs.get(i);
      if (c.getParameters().size() >= 5 && !c.getParameter(4).getClass().getSimpleName().equals("Color"))
        System.out.println(c.getParameter(4).getClass().getName()); 
      switch (c.getType()) {
        case "drawOval":
          drawOval(((Double)c.getParameter(0)).doubleValue(), ((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() <= 5 || (String)c.getParameter(5) != null);
          break;
        case "fillOval":
          fillOval(((Double)c.getParameter(0)).doubleValue(), ((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() <= 5 || (String)c.getParameter(5) != null);
          break;
        case "drawLine":
          drawLine(((Double)c.getParameter(0)).doubleValue(), ((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() > 5 && (String)c.getParameter(5) != null && ((String)c.getParameter(5)).length() > 0)
            System.out.println((String)c.getParameter(5)); 
          break;
        case "makeText":
          makeText((String)c.getParameter(0), (Font)c.getParameter(1), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() <= 5 || (String)c.getParameter(5) != null);
          break;
        case "deleteCMD":
          deleteCMD(((Integer)c.getParameter(0)).intValue());
          deleteCMD(this.commands.indexOf(c));
          i -= 2;
          break;
        case "dCMDFromArray":
          if (c.getParameters().size() <= 1 || (String)c.getParameter(1) != null);
          removeCommand((Command)c.getParameter(0));
          removeCommand(c);
          System.out.println("Attempting to delete Command: " + c);
          i -= 2;
          break;
        case "changeCMDColor":
          temp = this.commands.get(((Integer)c.getParameter(0)).intValue());
          temp.setParameter(4, c.getParameter(1));
          removeCommandPT(c);
          i--;
          break;
        case "mLB":
          temp2 = this.commands.get(((Integer)c.getParameter(0)).intValue());
          temp2.setParameter(4, Color.BLACK);
          removeCommandPT(c);
          i--;
          break;
        case "debug":
          System.out.println(c);
          System.out.println((String)c.getParameter(0));
          break;
      } 
    } 
    for (i = 0; i < this.commands.size(); i++) {
      Command temp2, c = this.commands.get(i);
      if (c.getParameters().size() >= 5 && !c.getParameter(4).getClass().getSimpleName().equals("Color"))
        System.out.println(c.getParameter(4).getClass().getName()); 
      switch (c.getType()) {
        case "drawOval":
          drawOval(((Double)c.getParameter(0)).doubleValue(), ((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() <= 5 || (String)c.getParameter(5) != null);
          break;
        case "fillOval":
          fillOval(((Double)c.getParameter(0)).doubleValue(), ((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() <= 5 || (String)c.getParameter(5) != null);
          break;
        case "drawRect":
          drawRect(((Double)c.getParameter(0)).doubleValue(), ((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() <= 5 || (String)c.getParameter(5) != null);
          break;
        case "fillRect":
          fillRect(((Double)c.getParameter(0)).doubleValue(), ((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() <= 5 || (String)c.getParameter(5) != null);
          break;
        case "drawLine":
          drawLine(((Double)c.getParameter(0)).doubleValue(), ((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() > 5 && (String)c.getParameter(5) != null && ((String)c.getParameter(5)).length() > 0)
            System.out.println((String)c.getParameter(5)); 
          break;
        case "makeText":
          makeText((String)c.getParameter(0), (Font)c.getParameter(1), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() <= 5 || (String)c.getParameter(5) != null);
          break;
        case "deleteCMD":
          deleteCMD(((Integer)c.getParameter(0)).intValue());
          deleteCMD(this.commands.indexOf(c));
          i -= 2;
          break;
        case "dCMDFromArray":
          if (c.getParameters().size() <= 1 || (String)c.getParameter(1) != null);
          removeCommand((Command)c.getParameter(0));
          removeCommand(c);
          System.out.println("Attempting to delete Command: " + c);
          i -= 2;
          break;
        case "changeCMDColor":
          System.out.println("Bad Code in use!");
          break;
        case "mLB":
          temp2 = this.commands.get(((Integer)c.getParameter(0)).intValue());
          temp2.setParameter(4, Color.BLACK);
          removeCommand(c);
          i--;
          break;
        case "debug":
          System.out.println(c);
          System.out.println((String)c.getParameter(0));
          break;
      } 
    } 
    for (i = 0; i < this.secondaryTimedCMDs.size(); i++) {
      Command temp2, c = this.secondaryTimedCMDs.get(i);
      if (c.getParameters().size() >= 5 && !c.getParameter(4).getClass().getSimpleName().equals("Color"))
        System.out.println(c.getParameter(4).getClass().getName()); 
      switch (c.getType()) {
        case "drawOval":
          drawOval(((Double)c.getParameter(0)).doubleValue(), ((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() <= 5 || (String)c.getParameter(5) != null);
          break;
        case "fillOval":
          fillOval(((Double)c.getParameter(0)).doubleValue(), ((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() <= 5 || (String)c.getParameter(5) != null);
          break;
        case "drawRect":
          drawRect(((Double)c.getParameter(0)).doubleValue(), ((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() <= 5 || (String)c.getParameter(5) != null);
          break;
        case "fillRect":
          fillRect(((Double)c.getParameter(0)).doubleValue(), ((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() <= 5 || (String)c.getParameter(5) != null);
          break;
        case "drawLine":
          drawLine(((Double)c.getParameter(0)).doubleValue(), ((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() > 5 && (String)c.getParameter(5) != null && ((String)c.getParameter(5)).length() > 0)
            System.out.println((String)c.getParameter(5)); 
          break;
        case "makeText":
          makeText((String)c.getParameter(0), (Font)c.getParameter(1), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), (Color)c.getParameter(4));
          if (c.getParameters().size() <= 5 || (String)c.getParameter(5) != null);
          break;
        case "deleteCMD":
          deleteCMD(((Integer)c.getParameter(0)).intValue());
          deleteCMD(this.commands.indexOf(c));
          i -= 2;
          break;
        case "dCMDFromArray":
          if (c.getParameters().size() <= 1 || (String)c.getParameter(1) != null);
          removeCommand((Command)c.getParameter(0));
          removeCommand(c);
          System.out.println("Attempting to delete Command: " + c);
          i -= 2;
          break;
        case "changeCMDColor":
          System.out.println("Bad Code in use!");
          break;
        case "mLB":
          temp2 = this.commands.get(((Integer)c.getParameter(0)).intValue());
          temp2.setParameter(4, Color.BLACK);
          removeCommand(c);
          i--;
          break;
        case "debug":
          System.out.println(c);
          System.out.println((String)c.getParameter(0));
          break;
      } 
    } 
  }
  
  public void removeDuplicateCommands() {
    for (int i = this.commands.size() - 1; i >= 1; i--) {
      if (!((Command)this.commands.get(i)).getType().equals("null"))
        for (int j = 0; j < i; j++) {
          if (((Command)this.commands.get(j)).equals(this.commands.get(i)) && !((Command)this.commands.get(j)).equals("null"))
            if (((Command)this.commands.get(j)).getType().equals("drawLine")) {
              ((Command)this.commands.get(j)).setType("null");
            } else {
              ((Command)this.commands.get(j)).setType("null");
            }  
        }  
    } 
  }
  
  public void setColor(Color color) {
    this.g2.setColor(color);
  }
  
  public Graphics2D getGraphics() {
    if (this.g2 == null)
      return (Graphics2D)this.g; 
    return this.g2;
  }
  
  public void drawOval(double x, double y, double rad1, double rad2, Color color) {
    this.g2.setColor(color);
    Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, rad1, rad2);
    this.g2.draw(ellipse);
  }
  
  public void fillOval(double x, double y, double rad1, double rad2, Color color) {
    this.g2.setColor(color);
    Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, rad1, rad2);
    this.g2.fill(ellipse);
  }
  
  public void drawRect(double x, double y, double w, double h, Color color) {
    this.g2.setColor(color);
    Rectangle2D.Double rectangle = new Rectangle2D.Double(x, y, w, h);
    this.g2.draw(rectangle);
  }
  
  public void fillRect(double x, double y, double w, double h, Color color) {
    this.g2.setColor(color);
    Rectangle2D.Double rectangle = new Rectangle2D.Double(x, y, w, h);
    this.g2.fill(rectangle);
  }
  
  public void drawLine(double x1, double y1, double x2, double y2, Color color) {
    this.g2.setColor(color);
    Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
    this.g2.draw(line);
  }
  
  public void makeText(String string, Font font, double x, double y, Color color) {
    this.g.setFont(font);
    this.g.setColor(color);
    this.g.drawString(string, (int)Math.round(x), (int)Math.round(y));
  }
  
  public ArrayList<Command> getCommands() {
    return this.commands;
  }
  
  public Command getCommand(int index) {
    return this.commands.get(index);
  }
  
  public Command getCommandPT(int index) {
    return this.primaryTimedCMDs.get(index);
  }
  
  public void setCommands(ArrayList<Command> c) {
    this.commands = c;
  }
  
  public void addCommand(Command c) {
    this.commands.add(c);
  }
  
  public void addCommand(int index, Command c) {
    this.commands.add(index, c);
  }
  
  public void addCommandPT(Command c) {
    this.primaryTimedCMDs.add(c);
  }
  
  public void addCommandPT(int index, Command c) {
    this.primaryTimedCMDs.add(index, c);
  }
  
  public void addCommand2T(Command c) {
    this.secondaryTimedCMDs.add(c);
  }
  
  public void addCommand2T(int index, Command c) {
    this.secondaryTimedCMDs.add(index, c);
  }
  
  public void removeCommand(int index) {
    this.commands.remove(index);
  }
  
  public void removeCommand(Command c) {
    int i = 0;
    Command cmd = null;
    boolean found = false;
    for (i = 0; i < this.commands.size(); i++) {
      cmd = this.commands.get(i);
      if (c.equals(cmd)) {
        found = true;
        break;
      } 
    } 
    if (found) {
      this.commands.remove(this.commands.indexOf(cmd));
    } else {
      System.out.println("Command not found: " + c);
    } 
  }
  
  public void removeCommandPT(Command c) {
    int i = 0;
    Command cmd = null;
    boolean found = false;
    for (i = 0; i < this.primaryTimedCMDs.size(); i++) {
      cmd = this.primaryTimedCMDs.get(i);
      if (c.equals(cmd)) {
        found = true;
        break;
      } 
    } 
    if (found) {
      this.primaryTimedCMDs.remove(this.primaryTimedCMDs.indexOf(cmd));
    } else {
      System.out.println("Command not found: " + c);
    } 
  }
  
  public int getECMDIndex(Command c) {
    int index = 0;
    for (int i = this.commands.size() - 1; i >= 0; i--) {
      Command tempC = this.commands.get(i);
      if (c.equals(tempC))
        return i; 
      index++;
    } 
    throw new Error("COMMAND NOT FOUND!");
  }
  
  public void deleteCMD(int index) {
    this.commands.remove(index);
  }
  
  public void change() {
    this.needsToCheck = true;
  }
  
  public void clearCMDs() {
    this.commands = new ArrayList<>();
  }
  
  public void clear2CMDS() {
    this.secondaryTimedCMDs = new ArrayList<>();
  }
}
