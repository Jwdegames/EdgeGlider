package JWDEGUI;

import java.awt.Container;
import java.util.HashMap;

public class GUIObject {
  private Container obj;
  
  private HashMap<String, Container> children;
  
  private HashMap<String, GUIObject> childNodes;
  
  private GUIObject parentNode;
  
  private Container parent;
  
  public GUIObject(Container o) {
    this.obj = o;
    this.children = new HashMap<>();
    this.childNodes = new HashMap<>();
  }
  
  public Container getParent() {
    return this.parent;
  }
  
  public Container setParent(Container p) {
    Container temp = this.parent;
    this.parent = p;
    return temp;
  }
  
  public GUIObject getParentNode() {
    return this.parentNode;
  }
  
  public void setParentNode(GUIObject p) {
    this.parentNode = p;
  }
  
  public Container getObj() {
    return this.obj;
  }
  
  public Container setObj(Container o) {
    Container temp = this.obj;
    this.obj = o;
    return temp;
  }
  
  public void setChild(String s, Container child) {
    this.children.put(s, child);
  }
  
  public void setChildNode(String s, GUIObject child) {
    this.childNodes.put(s, child);
  }
  
  public Container getChild(String s) {
    return this.children.get(s);
  }
  
  public HashMap<String, Container> getChildren() {
    return this.children;
  }
  
  public GUIObject getChildNode(String s) {
    return this.childNodes.get(s);
  }
  
  public HashMap<String, GUIObject> getChildNodes() {
    return this.childNodes;
  }
}
