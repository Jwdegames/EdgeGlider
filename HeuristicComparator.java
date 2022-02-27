import java.util.Comparator;

public class HeuristicComparator implements Comparator<MobHeuristics> {
  public int compare(MobHeuristics t1, MobHeuristics t2) {
    if (t1.f < t2.f)
      return -1; 
    if (t2.f < t1.f)
      return 1; 
    return 0;
  }
}
