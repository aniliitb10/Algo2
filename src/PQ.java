import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PQ<Key extends Comparable<? super Key>> implements Iterable<Key>
{
  private final static int TOP_INDEX = 1;
  private final ArrayList<Key> keys;
  private Comparator<Key> comparator = new DefaultComparator();

  public PQ()
  {
    keys = new ArrayList<>();
    keys.add(null); // just to ignore 0th index
  }

  public PQ(Comparator<Key> comparator)
  {
    this();
    this.comparator = Objects.requireNonNull(comparator);
  }

  public boolean isEmpty()
  {
    return (keys.size() <= TOP_INDEX);
  }

  public void insert(Key key)
  {
    keys.add(Objects.requireNonNull(key));
    swim(keys.size()-1);
  }

  public Key delTop()
  {
    if (isEmpty())
    {
      throw new NoSuchElementException();
    }

    return delIndex(TOP_INDEX);
  }

  /* Uses .equals for comparision of equality
  *  Does linear search to find the element
  *  returns -1 if not found, else returns the index of Key
  *  inside underlying array */
  public int contains(Key key)
  {
    if (isEmpty()) return -1;

    for (int index = TOP_INDEX; index < keys.size(); ++index)
    {
      if (keys.get(index).equals(key)) return index;
    }

    return -1;
  }

  /* Uses .equals for comparision of equality
   *  Does linear search to find (and then delete) the element */
  public boolean del(final Key key)
  {
    if (isEmpty()) return false;
    int index = contains(Objects.requireNonNull(key));
    if (index == -1) return false;

    return key == delIndex(index);
  }

  private Key delIndex(final int index)
  {
    if (isEmpty() || index >= keys.size() || index < TOP_INDEX)
    {
      throw new IllegalArgumentException();
    }

    Key key = keys.get(index);
    int lastIndex = keys.size() - 1;
    exchange(index, lastIndex);
    keys.remove(lastIndex);
    sink(index);

    return key;
  }

  private class DefaultComparator implements Comparator<Key>
  {
    @Override
    public int compare(Key left, Key right)
    {
      return left.compareTo(right);
    }
  }

  private void swim(int k)
  {
    while ((k > TOP_INDEX) && (compare(k, k/2)))
    {
      exchange(k/2, k);
      k = k/2;
    }
  }

  private void sink(int k)
  {
    while(2*k < keys.size())
    {
      int j = 2*k;
      if (j < (keys.size()-1) && compare(j+1, j)) j++; // find the minimum of two (if 2nd exists)
      if (compare(k, j)) break; // if node is less than the min-of-both-children, all done!

      exchange(k, j);
      k = j;
    }
  }

  private void exchange(int left, int right)
  {
    Key leftKey = keys.get(left);
    keys.set(left, keys.get(right));
    keys.set(right, leftKey);
  }

  private boolean compare(int left, int right)
  {
    return (comparator.compare(keys.get(left), keys.get(right)) < 0);
  }

  @Override
  public Iterator<Key> iterator()
  {
    ArrayList<Key> keysItr = new ArrayList<>();
    if (isEmpty())
    {
      return keysItr.iterator();
    }

    for (int index = TOP_INDEX; index < keys.size(); ++index)
    {
      keysItr.add(keys.get(index));
    }

    return keysItr.iterator();
  }

  public static void main(String[] args)
  {
    PQ<Integer> pq = new PQ<>(new Comparator<Integer>()
    {
      @Override
      public int compare(Integer integer, Integer t1)
      {
        return t1.compareTo(integer);
      }
    });

    pq.insert(10);
    pq.insert(100);
    pq.insert(-10);
    pq.insert(0);
    pq.insert(40);
    pq.insert(400);
    pq.insert(17);
    pq.insert(-42);
    pq.insert(42);

    System.out.println(pq.del(40));
    System.out.println(pq.del(17));
    System.out.println(pq.del(100));
    System.out.println(pq.del(656660));

    System.out.println("Printing numbers in descending order");

    while (!pq.isEmpty())
    {
      System.out.println(pq.delTop());
    }
  }
}
