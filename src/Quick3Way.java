public class Quick3Way
{
  public static void sort(Comparable[] comparables, int lo, int hi)
  {
    if (hi <= lo) return;

    int lt = lo, gt = hi, i = lo + 1;
    Comparable v = comparables[lo];

    while (i <= gt)
    {
      int cmp = comparables[i].compareTo(v);
      if (cmp < 0)       exchange(comparables, i++, lt++);
      else if (cmp > 0)  exchange(comparables, i, gt--);
      else                i++;
    }

    sort(comparables, lo, lt-1);
    sort(comparables, gt+1, hi);
  }

  private static void exchange(Comparable[] comparables, int i1, int i2)
  {

  }

}
