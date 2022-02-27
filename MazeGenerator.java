import java.util.Random;

public class MazeGenerator {
  private long seed;
  
  private Random rng;
  
  private BigNode[][] grid;
  
  private static int maxDivs = 5;
  
  private static MazeGraphics gui;
  
  public MazeGenerator(int rows, int cols) {
    this.grid = new BigNode[rows][cols];
    this.rng = new Random();
  }
  
  public void setGUI(MazeGraphics g) {
    gui = g;
  }
  
  public MazeGraphics getGUI() {
    return gui;
  }
  
  public BigNode[][] genMaze(long s, int m) {
    maxDivs = m;
    this.seed = s;
    int id = 0;
    this.rng.setSeed(this.seed);
    for (int row = 0; row < this.grid.length; row++) {
      for (int col = 0; col < (this.grid[row]).length; col++) {
        BigNode temp = new BigNode(id++, row, col, gui);
        this.grid[row][col] = temp;
        if (row > 0)
          temp.setAdjacentNode("left", this.grid[row - 1][col]); 
        if (col > 0)
          temp.setAdjacentNode("up", this.grid[row][col - 1]); 
      } 
    } 
    divide(0, 0, this.grid.length, (this.grid[0]).length, grid.length, grid[0].length);
    this.grid[0][0].specialize(1);
    this.grid[this.grid.length - 1][(this.grid[0]).length - 1].specialize(2);
    return this.grid;
  }
  

  // Maze Generation via Recursive Division Algorithm
  // 1) Begin w/ empty field
  // 2) Bisect the field w/ either vertical or horiozontal orientation at random value. Add a single door in the wall
  // 3) Repeat step 2 w/ areas on both side of the wall
  // 4) Continue recursively until not possible to bisect or maximum number of divisions reached.

  public void divide(int x0, int y0, int w, int h, int wMax, int hMax) {
    // wMax indicates the maximum width allowed
    // hMax indicates the maximum height allowed
    // minimum width and height implied to be 0
    maxDivs--;
    // Enforce wMax and hMax
    if (x0 + w > wMax) {
      w = wMax - x0;
    }
    if (y0 + h > hMax) {
      h = hMax - y0;
    }
    // Can't bisect - return immediately
    if (w <= 1 || h <= 1 || maxDivs < 0) {
      return;
    }
    
    boolean horizontal;
    // Decide what type of wall to make
    if (w < h) {
      horizontal = true;
    } else if (h < w) {
      horizontal = false;
    } else {
      horizontal = this.rng.nextBoolean();
    }


    if (horizontal) {
      // Create a horizontal wall
      // Decide the y value of the horizontal wall
      int y = y0 + this.rng.nextInt(h - 1);
      // Decide on the location of the door
      int door = x0 + this.rng.nextInt(w);
      // System.out.println("Making horizontal wall at y = " + y + " and door at x = " + door);
      // Make the wall
      for (int x = x0; x < x0 + w; x++) {
        if (x != door) {
          this.grid[x][y].createWalls("down");
        }
      }
      
      // Need to decide how to divide
      // Create wall below
      // divide(x0, y0, w, y + 1 - y0, wMax, hMax);
      divide(x0, y0, w, y - y0 + 1, wMax, hMax);
      // Create wall above
      // divide(x0, y + 1, w, h - y + 1 - y0, wMax, hMax);
      divide(x0, y + 1, w, y0 + h - y - 1, wMax, hMax);

    } else {
      // Create a vertical wall
      // Decide the x value of the vertical wall
      int x = x0 + this.rng.nextInt(w - 1);
      // Decide on the location of the door
      int door = y0 + this.rng.nextInt(h);
      // System.out.println("Making vertical wall at x = " + x + " and door at y = " + door);
      // Make the wall
      for (int y = y0; y < y0 + h; y++) {
        if (y != door) {
          this.grid[x][y].createWalls("right");
        }
      }
      
      // Need to decide how to divide
      // Create wall to the left
      // divide(x0, y0, x + 1 - x0, h, wMax, hMax);
      divide(x0, y0, x - x0 + 1, h, wMax, hMax);
      // Create wall to the right
      // divide(x + 1, y0, w - x + 1 - x0, h, wMax, hMax);
      divide(x + 1, y0, x0 + w - x - 1, h, wMax, hMax);
    }
  }

  /*public void divide(int x0, int y0, int w, int h) {
    maxDivs--;
    // Don't divide if width is less than 2 or height is less than 2
    // Also don't divide if maxDivs is 0 or less
    if ((w <= 1 || h <= 1) || maxDivs <= 0)
      return; 
    if ((w < h || this.rng.nextBoolean()) && h > 1) {
      int y = y0 + this.rng.nextInt(h - 1);
      int door = x0 + this.rng.nextInt(w);
      for (int x = x0; x < x0 + w; x++) {
        if (x != door)
          this.grid[x][y].createWalls("down"); 
      } 
      divide(x0, y0, w, y + 1 - y0);
      divide(x0, y + 1, w, h - y + 1 - y0);
    } else if (h < w) {
      int x = x0 + this.rng.nextInt(w - 1);
      int door = y0 + this.rng.nextInt(h);
      for (int y = y0; y < y0 + h; y++) {
        if (y != door)
          this.grid[x][y].createWalls("right"); 
      } 
      divide(x0, y0, x + 1 - x0, h);
      divide(x + 1, y0, w - x + 1 - x0, h);
    } 
  }*/
}
