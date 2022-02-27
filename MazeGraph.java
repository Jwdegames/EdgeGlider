public class MazeGraph {
    private BigNode[][] grid;
    
    public MazeGraph() {}
    
    public MazeGraph(BigNode[][] g) {
      this.grid = g;
    }
    
    public void resize(int w, int h) {
      BigNode[][] temp = new BigNode[w][h];
      for (int x = 0; x < w && x < this.grid.length; x++) {
        for (int y = 0; y < h && y < (this.grid[x]).length; y++)
          temp[x][y] = this.grid[x][y]; 
      } 
      this.grid = temp;
    }
    
    public void setGrid(BigNode[][] g) {
      this.grid = g;
    }
    
    public BigNode[][] getGrid() {
      return this.grid;
    }
    
    public String getCoords(BigNode b) {
      int c = 0;
      boolean found = false;
      System.out.println("COORDS");
      int r;
      for (r = 0; r < this.grid.length && 
        !found; r++) {
        for (c = 0; c < (this.grid[0]).length; c++) {
          System.out.println("MAZE");
          if (this.grid[r][c] == b) {
            found = true;
            break;
          } 
        } 
      } 
      System.out.println("" + found + ": " + c + "," + r);
      if (!found)
        throw new IllegalArgumentException("OUT OF GRID!"); 
      String toRet = "" + r + "," + r;
      System.out.println("Node at " + toRet);
      return toRet;
    }
  }
  