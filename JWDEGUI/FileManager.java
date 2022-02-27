package JWDEGUI;

import java.io.File;
import java.util.ArrayList;

public class FileManager {
  private ArrayList<File> files = new ArrayList<>();
  
  private ArrayList<FileEditor> editors = new ArrayList<>();
  
  public FileEditor writeNewFile(String name) throws Throwable {
    File f = addFile(name);
    return addEditor(f);
  }
  
  public File addFile(String name) throws Throwable {
    File file = new File("Data\\" + name);
    file.createNewFile();
    this.files.add(file);
    return file;
  }
  
  public FileEditor addEditor(File file) throws Throwable {
    FileEditor editor = null;
    editor = new FileEditor(file);
    this.editors.add(editor);
    return editor;
  }
  
  public void retrieveFiles() throws Throwable {
    File folder = new File(System.getProperty("user.dir") + "\\Data");
    for (File f : folder.listFiles()) {
      this.files.add(f);
      this.editors.add(new FileEditor(f));
    } 
  }
  
  public ArrayList<File> getFiles() {
    return this.files;
  }
  
  public File getFile(int index) {
    return this.files.get(index);
  }
  
  public ArrayList<FileEditor> getEditors() {
    return this.editors;
  }
  
  public FileEditor getEditor(int index) {
    return this.editors.get(index);
  }
  
  public File findFile(String name) {
    for (File f : this.files) {
      System.out.println(f.getName());
      if (f.getName().equals(name))
        return f; 
    } 
    return null;
  }
  
  public FileEditor findEditor(String name) {
    for (FileEditor e : this.editors) {
      if (e.getFile().getName().equals(name))
        return e; 
    } 
    return null;
  }
}
