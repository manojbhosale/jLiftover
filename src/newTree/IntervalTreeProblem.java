package newTree;

import java.util.ArrayList;
import java.util.List;

public class IntervalTreeProblem{

public static void main(String args[])
  {
      IntervalTree<List<Object>> it = new IntervalTree<List<Object>>();
      List<Object> l = new ArrayList<Object>();
      
      l.add(1);
      l.add(1);
      l.add("testChr");
      
      it.addInterval(0L, 10L,l);
      it.addInterval(20L, 30L, l);
      it.addInterval(15L, 17L, l);
      it.addInterval(25L, 35L, l);

      List<List<Object>> result1 = it.get(5L);
      List<List<Object>> result2 = it.get(10L);
      List<List<Object>> result3 = it.get(29L);
      List<List<Object>> result4 = it.get(5L, 15L);

      System.out.print("\nIntervals that contain 5L:");
      for (List<Object> r : result1)
          System.out.print(r.get(2) + " ");

     /* System.out.print("\nIntervals that contain 10L:");
      for (int r : result2)
          System.out.print(r + " ");

      System.out.print("\nIntervals that contain 29L:");
      for (int r : result3)
          System.out.print(r + " ");

      System.out.print("\nIntervals that intersect (5L,15L):");
      for (int r : result4)
          System.out.print(r + " ");
*/  }
}
