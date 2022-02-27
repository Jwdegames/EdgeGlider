package JWDEGUI;

import java.util.ArrayList;

public class Command {
  private String type;
  
  private ArrayList parameters = new ArrayList();
  
  public Command(String t, ArrayList p) {
    setType(t);
    setParameters(p);
  }
  
  public String getType() {
    return this.type;
  }
  
  public ArrayList getParameters() {
    return this.parameters;
  }
  
  public Object getParameter(int index) {
    return this.parameters.get(index);
  }
  
  public void setType(String t) {
    this.type = t;
  }
  
  public void setParameter(int i, Object o) {
    try {
      this.parameters.set(i, o);
    } catch (Exception e) {
      System.err.println("There was an exception trying to set Array Parameter!");
      System.err.println("Attempted to access index " + i + "/" + (this.parameters.size() - 1));
      System.err.println("Attempted to insert: " + o);
      System.err.println("Current Command is: " + toString());
      throw new IllegalArgumentException("Illegal Argument Exception!");
    } 
  }
  
  public void setParameters(ArrayList p) {
    this.parameters = p;
  }
  
  public void addParameter(Object o) {
    this.parameters.add(o);
  }
  
  public void addParameter(int index, Object o) {
    this.parameters.add(index, o);
  }
  
  public void removeParameter(int index) {
    this.parameters.remove(index);
  }
  
  public String toString() {
    return "Command with type: " + getType() + " with parameters: " + getParameters();
  }
  
  public int hashCode() {
    return toString().hashCode();
  }
  
  public boolean equals(Object obj) {
    if (!(obj instanceof Command))
      return false; 
    Command c = (Command)obj;
    boolean equal = false;
    if (!c.getType().equals(getType()))
      return false; 
    if (getParameters().size() != c.getParameters().size()) {
      System.out.println(toString() + " doesn't have the same length as " + c);
      return false;
    } 
    if (c.getParameters().size() != getParameters().size())
      return false; 
    for (int i = 0; i < c.getParameters().size(); i++) {
      if (c.getParameter(i) == null && getParameter(i) != null)
        return false; 
      if (c.getParameter(i) != null && getParameter(i) == null)
        return false; 
      if (c.getParameter(i) != null || getParameter(i) != null) {
        Class<?> c1 = c.getParameter(i).getClass();
        Class<?> c2 = getParameter(i).getClass();
        if (!c1.cast(c.getParameter(i)).equals(c2.cast(getParameter(i))))
          return false; 
      } 
    } 
    return true;
  }
}
