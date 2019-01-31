import edu.princeton.cs.algs4.Edge;

import java.util.Objects;

public class IndexedMinPQ
{
  private final PQ<IndexedEdge> minPq;

  public IndexedMinPQ()
  {
    minPq = new PQ<>();
  }

  public void insert(int index, Edge edge)
  {
    IndexedEdge indexedEdge = new IndexedEdge(index, edge);
    minPq.insert(indexedEdge);
  }

  public boolean contains(int index)
  {
    IndexedEdge indexedEdge = new IndexedEdge(index, null);
    return (minPq.contains(indexedEdge) != -1);
  }

  // Used to set the edge, for a given inde
  public void change(int index, Edge edge)
  {
    IndexedEdge indexedEdge = new IndexedEdge(index, null);
    minPq.del(indexedEdge);
    minPq.insert(new IndexedEdge(index, edge));
  }

  public boolean del(int index)
  {
    IndexedEdge indexedEdge = new IndexedEdge(index, null);
    return minPq.del(indexedEdge);
  }

  public IndexedEdge delTop()
  {
    return minPq.delTop();
  }

  public boolean isEmpty()
  {
    return minPq.isEmpty();
  }

  public static void main(String[] args)
  {
    IndexedMinPQ indexedMinPQ = new IndexedMinPQ();
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
