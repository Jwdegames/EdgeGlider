package JWDEGUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileEditor {
  private File file;
  
  private BufferedWriter writer;
  
  private BufferedReader reader;
  
  private ArrayList<String> text;
  
  public FileEditor(File f) throws Throwable {
    this.file = f;
    this.reader = new BufferedReader(new FileReader(this.file));
    readFile();
    this.writer = new BufferedWriter(new FileWriter(this.file));
  }
  
  public void readFile() throws Throwable {
    String line = this.reader.readLine();
    this.text = new ArrayList<>();
    while (line != null) {
      this.text.add(line);
      line = this.reader.readLine();
    } 
  }
  
  public void write(String t) throws Throwable {
    this.text.add(t);
  }
  
  public void write(ArrayList<String> s) throws Throwable {
    for (int i = 0; i < s.size(); i++)
      this.text.add(this.text.size(), s.get(i)); 
  }
  
  public void write(int index, String s) throws Throwable {
    this.text.add(index, s);
  }
  
  public void write(int index, ArrayList<String> s) throws Throwable {
    for (int i = 0; i < s.size(); i++)
      this.text.add(index + i, s.get(i)); 
  }
  
  public String editLine(int index, String s) throws Throwable {
    return this.text.set(index, s);
  }
  
  public void setText(ArrayList<String> t) throws Throwable {
    this.text = t;
  }
  
  public String removeLine(int index) throws Throwable {
    return this.text.remove(index);
  }
  
  public ArrayList<String> getText() {
    return this.text;
  }
  
  public void save() throws Throwable {
    String output = "";
    if (this.text.size() == 0)
      return; 
    for (int i = 0; i < this.text.size() - 1; i++)
      output = output + (String)this.text.get(i) + "\n"; 
    output = output + (String)this.text.get(this.text.size() - 1);
    this.writer.write(output);
    this.writer.flush();
    System.out.println("Output is " + output);
    close();
  }
  
  public void restart() throws Throwable {
    this.reader = new BufferedReader(new FileReader(this.file));
    readFile();
    this.writer = new BufferedWriter(new FileWriter(this.file));
  }
  
  public File getFile() {
    return this.file;
  }
  
  public void close() throws Throwable {
    this.writer.close();
    this.reader.close();
  }
}
