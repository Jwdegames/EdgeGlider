package JWDEGUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicArrowButton;

public class GUITemplate {
  private String windowName;
  
  private JFrame mainJFrame = null;
  
  private ArrayList<Container> contentPanes = new ArrayList<>();
  
  private ArrayList<JPanel> panels = new ArrayList<>();
  
  private ArrayList<JScrollPane> scrollPanes = new ArrayList<>();
  
  private ArrayList<JTextArea> textAreas = new ArrayList<>();
  
  private ArrayList<JButton> jButtons = new ArrayList<>();
  
  private ArrayList<BasicArrowButton> baButtons = new ArrayList<>();
  
  private ArrayList<JTextField> jtFields = new ArrayList<>();
  
  private ArrayList<JLabel> jLabels = new ArrayList<>();
  
  private ArrayList<BufferedImage> bImages = new ArrayList<>();

  private ArrayList<JScrollBar> jsBars = new ArrayList<JScrollBar>();  
  public GUITemplate() {
    this.windowName = null;
  }
  
  public GUITemplate(String name) {
    this.windowName = name;
    createMainJFrame(name);
  }
  
  public void createMainJFrame(String windowName) {
    this.mainJFrame = new JFrame(windowName);
    this.mainJFrame.setDefaultCloseOperation(3);
    this.mainJFrame.setVisible(true);
    this.mainJFrame.pack();
  }
  
  public void setJFrameSize(JFrame jframe, int x, int y, int width, int height) {
    this.mainJFrame.setBounds(x, y, width, height);
    this.mainJFrame.setVisible(true);
  }
  
  public void pack(JFrame jframe, Container contentPane) {
    int x = (contentPane.getBounds()).x;
    int y = (contentPane.getBounds()).x;
    int width = (contentPane.getBounds()).width;
    int height = (contentPane.getBounds()).height;
    jframe.pack();
    contentPane.setLayout((LayoutManager)null);
    setJFrameSize(jframe, x, y, width, height);
  }
  
  public void setSameBounds(JComponent obj1, Container obj2) {
    obj1.setBounds((obj2.getBounds()).x, (obj2.getBounds()).y, (obj2.getBounds()).width, (obj2.getBounds()).height);
  }
  
  public void setSameBounds(Container obj1, Container obj2) {
    obj1.setBounds((obj2.getBounds()).x, (obj2.getBounds()).y, (obj2.getBounds()).width, (obj2.getBounds()).height);
  }
  
  public void offset(JComponent obj, int x, int y) {
    obj.setBounds((obj.getBounds()).x + x, (obj.getBounds()).y + y, (obj.getBounds()).width, (obj.getBounds()).height);
  }
  
  public void setSize(JComponent jObject, int width, int height) {
    jObject.setBounds((jObject.getBounds()).x, (jObject.getBounds()).y, width, height);
  }
  
  public void setPos(Container obj, int x, int y) {
    obj.setBounds(x, y, (obj.getBounds()).width, (obj.getBounds()).height);
  }
  
  public void setBounds(Container obj, int x, int y, int width, int height) {
    setSize((JComponent)obj, width, height);
    setPos(obj, x, y);
  }
  
  public void setSameSize(Container obj1, Container obj2) {
    obj1.setSize(obj2.getWidth(), obj2.getHeight());
  }
  
  public Container makeContainer(JFrame jFrame) {
    Container contentPane = jFrame.getContentPane();
    contentPane.setLayout(new FlowLayout());
    this.contentPanes.add(contentPane);
    return contentPane;
  }
  
  public JPanel makePanel() {
    JPanel panel = new JPanel();
    this.panels.add(panel);
    return panel;
  }
  
  public JTextArea makeTextArea(int width, int height, boolean editable) {
    JTextArea textArea = new JTextArea(width, height);
    textArea.setEditable(editable);
    this.textAreas.add(textArea);
    return textArea;
  }
  
  public JScrollPane makeScrollPane(JTextArea textArea, Container container) {
    JScrollPane scrollPane = new JScrollPane(textArea);
    container.add(scrollPane);
    this.scrollPanes.add(scrollPane);
    return scrollPane;
  }
  
  public JButton makeJButton(String text) {
    JButton jButton = new JButton(text);
    this.jButtons.add(jButton);
    return jButton;
  }
  
  public BasicArrowButton makeBAButton(int direction) {
    BasicArrowButton baButton = new BasicArrowButton(direction);
    this.baButtons.add(baButton);
    return baButton;
  }
  
  public JTextField makeJTField(int width) {
    JTextField jtField = new JTextField(width);
    this.jtFields.add(jtField);
    return jtField;
  }
  
  public JLabel makeJLabel(String text) {
    JLabel jLabel = new JLabel(text);
    this.jLabels.add(jLabel);
    return jLabel;
  }
  
  public BufferedImage makeBImage(int width, int height) {
    BufferedImage bImage = new BufferedImage(width, height, 2);
    this.bImages.add(bImage);
    return bImage;
  }

  // Makes a JScrollBar
  public JScrollBar makeJScrollBar(int status, int value, int extent, int min, int max) {
    // status is horizontal (0) or vertical (1) 
    JScrollBar jsBar = new JScrollBar(status);
    jsBars.add(jsBar);
    return jsBar;
  }
  
  public GUIObject createPane(Container c, GUIPainter painter, JPanel panel, int x, int y, int width, int height, boolean editable, int horizontalBarPolicy, int verticalBarPolicy) {
    Container container = c;
    JTextArea textArea = makeTextArea(width, height, editable);
    panel.add(textArea);
    JScrollPane treePane = makeScrollPane(textArea, container);
    panel.add(treePane);
    switch (verticalBarPolicy) {
      case 0:
        treePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        break;
      case 1:
        treePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        break;
      case 2:
        // Make scrollbar, but make it uneditable
        treePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        treePane.getVerticalScrollBar().setEnabled(false);
        break;      
    }
    switch (horizontalBarPolicy) {
      case 0:
        treePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        break;
      case 1:
        treePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        break;
      case 2: 
        // Make scrollbar, but make it uneditable
        treePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        treePane.getHorizontalScrollBar().setEnabled(false);
        break;
    } 
    textArea.add(painter.getCustomCanvas());
    GUIObject paneObj = new GUIObject(treePane);
    paneObj.setChild("TextArea", textArea);
    paneObj.setParent(panel);
    return paneObj;
  }
  
  public void append(Container c1, Container c2) {
    c1.add(c2);
  }
  
  public void addToFrame(Container c1) {
    this.mainJFrame.add(c1);
  }
  
  public void setPaneActualArea(GUIObject obj, int width, int height) {
    JTextArea textArea = (JTextArea)obj.getChild("TextArea");
    textArea.setRows(width);
    textArea.setColumns(height);
  }
  
  public void setPaneVisibleArea(GUIObject obj, int width, int height) {
    obj.getParent().setSize(width, height);
  }
  
  public void setPaneViewCoords(GUIObject obj, int x, int y) {
    JViewport port = ((JScrollPane)obj.getObj()).getViewport();
    JScrollPane pane = (JScrollPane)obj.getObj();
    JScrollBar horizBar = pane.getHorizontalScrollBar();
    JScrollBar vertBar = pane.getVerticalScrollBar();
    horizBar.setValue(x);
    vertBar.setValue(y);
  }
  
  public void setVisible(Component obj, boolean state) {
    obj.setVisible(state);
  }
  
  public ArrayList<Container> getContentPanes() {
    return this.contentPanes;
  }
  
  public Container getCertainContentPane(int index) {
    return this.contentPanes.get(index);
  }
  
  public ArrayList<JPanel> getPanels() {
    return this.panels;
  }
  
  public JPanel getPanel(int index) {
    return this.panels.get(index);
  }
  
  public ArrayList<JTextArea> getTextAreas() {
    return this.textAreas;
  }
  
  public JTextArea getTextArea(int index) {
    return this.textAreas.get(index);
  }
  
  public ArrayList<JScrollPane> getScrollPanes() {
    return this.scrollPanes;
  }
  
  public JScrollPane getScrollPane(int index) {
    return this.scrollPanes.get(index);
  }
  
  public ArrayList<JButton> getJButtons() {
    return this.jButtons;
  }
  
  public JButton getJButton(int index) {
    return this.jButtons.get(index);
  }
  
  public ArrayList<BasicArrowButton> getBAButtons() {
    return this.baButtons;
  }
  
  public BasicArrowButton getBAButton(int index) {
    return this.baButtons.get(index);
  }
  
  public ArrayList<JTextField> getJTFields() {
    return this.jtFields;
  }
  
  public JTextField getJTField(int index) {
    return this.jtFields.get(index);
  }
  
  public ArrayList<JLabel> getJLabels() {
    return this.jLabels;
  }
  
  public JLabel getJLabel(int index) {
    return this.jLabels.get(index);
  }
  
  public ArrayList<BufferedImage> getBImages() {
    return this.bImages;
  }
  
  public BufferedImage getBImage(int index) {
    return this.bImages.get(index);
  }

  public ArrayList<JScrollBar> getJSBars() {
    return jsBars;
  }

  public JScrollBar getJSBar(int index) {
    return jsBars.get(index);
  }

  public JFrame getMainJFrame() {
    return this.mainJFrame;
  }
}
