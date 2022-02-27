import java.util.Comparator;

public class TileNodeComparator implements Comparator<TileNode> {
  public int compare(TileNode t1, TileNode t2) {
    if (t1.f < t2.f)
      return -1; 
    if (t2.f < t1.f)
      return 1; 
    return 0;
  }
}
