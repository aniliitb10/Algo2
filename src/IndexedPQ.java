import edu.princeton.cs.algs4.Edge;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class IndexedPQ<Item extends Comparable<Item>>
{
  private static final int ABSENT_INDEX = -1;
  private static final int TOP_INDEX = 1;
  private final int maxSize;
  private final Item[] items;
  private final int[] pq;
  private final int[] qp;
  private int count;
  private Comparator<Item> comparator;

  public IndexedPQ(int maxSize)
  {
    this.maxSize = maxSize;
    pq = new int[this.maxSize + 1];

    items = (Item[]) new Comparable[this.maxSize];
    qp = new int[this.maxSize];

    count = 0;
    comparator = new DefaultComparator();

    for (int index = 0; index < this.maxSize; ++index)
    {
      qp[index] = ABSENT_INDEX;
    }
  }

  public IndexedPQ(int maxSize, Comparator<Item> comparator)
  {
    this(maxSize);
    this.comparator = comparator;
  }

  public void insert(int index, Item item)
  {
    validateIndex(index);
    if (contains(index)) throw new IllegalArgumentException(index + " is already in queue");
    count++; // this also takes care of the fact that pq, qp and item's 0th index remains empty

    items[index] = item;

    pq[count] = index;
    qp[pq[count]] = count;
    swim(count);
  }

  public boolean contains(int index)
  {
    validateIndex(index);
    return qp[index] != ABSENT_INDEX;
  }

  // Used to set the item, for a given index
  public void change(int index, Item item)
  {
    validateIndex(index);
    if (!contains(index)) throw new NoSuchElementException("index " + index + " doesn't exist");

    items[index] = item;
    swim(qp[index]);
    sink(qp[index]);
  }

  public void del(int index)
  {
    validateIndex(index);
    if (!contains(index)) throw new NoSuchElementException(index + " is not in the queue");

    int pqIndex = qp[index];
    exchange(pqIndex, count--);

    swim(pqIndex);
    sink(pqIndex);

    // to help the garbage collector
    items[index] = null;
  }

  public int delTop()
  {
    final int key = pq[TOP_INDEX];
    exchange(TOP_INDEX, count);

    // cleanup
    count--;
    qp[key] = ABSENT_INDEX;
    items[key] = null; //optional to help garbage collector

    sink(TOP_INDEX);

    return key;
  }

  private void swim(int node)
  {
    while (node > TOP_INDEX && less(node, node/2))
    {
      exchange(node, node/2);
      node = node/2;
    }
  }

  private void sink(int node)
  {
    while (2*node <= count)
    {
      int child = 2 * node;

      // find the least of two children
      if ((child+1 <= count) && less(child+1, child)) child++;

      // if node is less than the least of two children, no more processing is required
      if (less(node, child)) break;

      exchange(node, child);
      node = child;
    }
  }

  private boolean less(int left, int right)
  {
    return (comparator.compare(items[pq[left]], items[pq[right]]) < 0);
  }

  private class DefaultComparator implements Comparator<Item>
  {
    @Override
    public int compare(Item left, Item right)
    {
      return left.compareTo(right);
    }
  }

  // changes the order of indices in pq
  // hence, items[] remains unaffected
  private void exchange(int left, int right)
  {
    int leftPqBackUp = pq[left];
    pq[left] = pq[right];
    pq[right] = leftPqBackUp;

    qp[pq[left]] = left;
    qp[pq[right]] = right;
  }

  private void validateIndex(int index)
  {
    if (index < 0 || index >= maxSize)
    {
      throw new IllegalArgumentException("Invalid index: " + index + ", valid range: [0," + maxSize + ")");
    }
  }


  public boolean isEmpty()
  {
    return count == 0;
  }


  public static void main(String[] args)
  {
    IndexedPQ<Edge> indexedMinPQ = new IndexedPQ<>(20);
    indexedMinPQ.insert(0, new Edge(0, 3, 9.8));
    indexedMinPQ.insert(4, new Edge(3, 4, 6.7));
    indexedMinPQ.insert(3, new Edge(3, 1, 2.7));
    indexedMinPQ.insert(1, new Edge(5, 1, 1.7));

    if (indexedMinPQ.contains(3))
    {
      indexedMinPQ.del(3);
    }

    if (indexedMinPQ.contains(4))
    {
      indexedMinPQ.change(4, new Edge(1,4,0.0));
    }

    indexedMinPQ.insert(6, new Edge(6, 1, 0.7));
    while (!indexedMinPQ.isEmpty())
    {
      System.out.println(indexedMinPQ.delTop());
    }
  }
}
