public class MobHeuristics {
  public Mob mob;
  
  public TileNode tile;
  
  public int f = 0, g = 0, h = 0, x = 0, y = 0;
  
  public MobHeuristics(TileNode t) {
    this.tile = t;
    if (this.tile == null)
      return; 
    this.x = this.tile.x;
    this.y = this.tile.y;
  }
}
