package JWDEGUI;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GUITimer implements Runnable {
  private Thread t;
  
  private String threadName;
  
  private ArrayList<Command> commands = new ArrayList<>();
  
  private ArrayList<Integer> continousTimes = new ArrayList<>();
  
  private CustomCanvas theCC;
  
  private int currentTime = 0;
  
  private int cTimeMarker = 0;
  
  private int step = 0;
  
  private GUIPainter painter;
  
  private boolean running = false;
  
  private boolean continous = false;
  
  private int initialDelay = 1000;
  
  public GUITimer(String name) {
    this.threadName = name;
  }
  
  public void run() {
    try {
      sleep(this.initialDelay);
    } catch (Exception e) {
      System.out.println(e);
    } 
    if (this.running) {
      this.theCC = this.painter.getCustomCanvas();
      if (!this.continous) {
        try {
          for (Command c : this.commands) {
            sleep(((Integer)c.getParameter(0)).intValue());
            this.currentTime += ((Integer)c.getParameter(0)).intValue();
            runCommand(c);
            this.theCC.repaint();
          } 
        } catch (Exception e) {
          System.out.println(e);
        } 
      } else {
        reorderByTime();
        for (Iterator<Integer> iterator = this.continousTimes.iterator(); iterator.hasNext(); ) {
          int i = ((Integer)iterator.next()).intValue();
          this.cTimeMarker = i - this.currentTime;
          try {
            sleep(this.cTimeMarker);
          } catch (Exception e) {
            System.out.println(e);
          } 
          this.currentTime += this.cTimeMarker;
          this.step++;
          for (Command c : this.commands) {
            if (!c.getParameter(0).getClass().getSimpleName().equals("Integer"))
              System.err.println("Command has no time: " + c); 
            if (((Integer)c.getParameter(0)).intValue() == i) {
              runCommand(c);
              this.theCC.repaint();
            } 
          } 
        } 
      } 
    } 
  }
  
  public void start() {
    this.t = new Thread(this, this.threadName);
    this.t.start();
  }
  
  public void reorderByTime() {
    ArrayList<Command> tempA = new ArrayList<>();
    ArrayList<Integer> tempB = new ArrayList<>();
    for (Command c : this.commands) {
      if (!tempB.contains(Integer.valueOf(((Integer)c.getParameter(0)).intValue())))
        tempB.add(Integer.valueOf(((Integer)c.getParameter(0)).intValue())); 
    } 
    Collections.sort(tempB);
    if (this.commands.size() > 0)
      tempA.add(this.commands.get(0)); 
    int maxIndex = 0;
    for (int i = 1; i < this.commands.size(); i++) {
      for (int j = 0; j < tempA.size(); j++) {
        if (((Integer)((Command)this.commands.get(i)).getParameter(0)).intValue() > ((Integer)((Command)tempA.get(j)).getParameter(0)).intValue())
          maxIndex = j; 
      } 
      tempA.add(maxIndex, this.commands.get(i));
    } 
    this.commands = tempA;
    this.continousTimes = tempB;
  }
  
  public void sleep(int time) throws InterruptedException {
    Thread.sleep(time);
  }
  
  public void suspend() {
    this.t.suspend();
  }
  
  public void resume() {
    this.t.resume();
  }
  
  public void stop() {
    this.t.stop();
  }
  
  public void runCommand(Command c) {
    ArrayList temp;
    Command cmd;
    String debug = "DefaultCode:GUITIMER";
    switch (c.getType()) {
      case "drawOval":
        if (c.getParameters().size() > 6)
          debug = (String)c.getParameter(6); 
        this.painter.drawOval(((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), ((Double)c.getParameter(4)).doubleValue(), (Color)c.getParameter(5), debug);
        this.painter.getCustomCanvas().change();
        break;
      case "fillOval":
        if (c.getParameters().size() > 6)
          debug = (String)c.getParameter(6); 
        this.painter.fillOval(((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), ((Double)c.getParameter(4)).doubleValue(), (Color)c.getParameter(5), debug);
        this.painter.getCustomCanvas().change();
        break;
      case "drawLine":
        if (c.getParameters().size() > 6)
          debug = (String)c.getParameter(6); 
        this.painter.drawLine(((Double)c.getParameter(1)).doubleValue(), ((Double)c.getParameter(2)).doubleValue(), ((Double)c.getParameter(3)).doubleValue(), ((Double)c.getParameter(4)).doubleValue(), (Color)c.getParameter(5), debug);
        this.painter.getCustomCanvas().change();
        break;
      case "makeText":
        if (c.getParameters().size() > 6)
          debug = (String)c.getParameter(6); 
        this.painter.makeText((String)c.getParameter(1), (Font)c.getParameter(2), ((Double)c.getParameter(3)).doubleValue(), ((Double)c.getParameter(4)).doubleValue(), (Color)c.getParameter(5), debug);
        this.painter.getCustomCanvas().change();
        break;
      case "deleteCMD":
        this.painter.deleteCMD(((Integer)c.getParameter(1)).intValue());
        if (((Command)c.getParameter(1)).getParameters().size() >= 7);
        this.painter.getCustomCanvas().change();
        break;
      case "dCMDFromArray":
        temp = ((Command)c.getParameter(1)).getParameters();
        temp.remove(0);
        cmd = new Command(((Command)c.getParameter(1)).getType(), temp);
        if (c.getParameters().size() > 2)
          debug = (String)c.getParameter(2); 
        this.painter.dCMDFromArray(cmd, debug);
        if (((Command)c.getParameter(1)).getParameters().size() >= 7);
        this.painter.getCustomCanvas().change();
        break;
      case "changeCMDColor":
        if (c.getParameters().size() > 4)
          debug = (String)c.getParameter(4); 
        this.painter.changeCMDColor(((Integer)c.getParameter(1)).intValue(), (Color)c.getParameter(2), debug);
        this.painter.getCustomCanvas().change();
        break;
      case "mLB":
        if (c.getParameters().size() > 2)
          debug = (String)c.getParameter(2); 
        this.painter.mLB(((Integer)c.getParameter(1)).intValue(), debug);
        this.painter.getCustomCanvas().change();
        break;
      case "sendCMD":
        if (c.getParameters().size() > 1)
          debug = (String)c.getParameter(1); 
        this.painter.sendCMD((Command)c.getParameter(0), debug);
        break;
    } 
  }
  
  public void clearCommands() {
    this.commands.clear();
    this.commands = new ArrayList<>();
    this.continousTimes = new ArrayList<>();
  }
  
  public void addCommand(Command c) {
    this.commands.add(c);
  }
  
  public void removeCommand(int index) {
    this.commands.remove(index);
  }
  
  public void setPainter(GUIPainter p) {
    this.painter = p;
  }
  
  public GUIPainter getPainter() {
    return this.painter;
  }
  
  public void enable(Boolean b) {
    if (b.booleanValue()) {
      this.running = true;
    } else {
      this.running = false;
    } 
  }
  
  public void setContinuity(Boolean b) {
    if (b.booleanValue()) {
      this.continous = true;
    } else {
      this.continous = true;
    } 
  }
}
