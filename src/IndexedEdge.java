import edu.princeton.cs.algs4.Edge;

public class IndexedEdge implements Comparable<IndexedEdge>
{
  private final int index;

  public void setEdge(Edge edge)
  {
    this.edge = edge;
  }

  private Edge edge;

  IndexedEdge(int index, Edge edge)
  {
    this.index = index;
    this.edge  = edge;
  }

  public int getIndex()
  {
    return index;
  }

  public Edge getEdge()
  {
    return edge;
  }

  @Override
  public String toString()
  {
    return index + ": " + edge.toString();
  }

  @Override
  public int compareTo(IndexedEdge indexedEdge)
  {
    return this.edge.compareTo(indexedEdge.edge);
  }

  @Override
  public boolean equals(Object obj)
  {
    if (!(obj instanceof IndexedEdge))
    {
      throw new IllegalArgumentException("obj is not instanceof IndexedEdge");
    }

    IndexedEdge indexedEdge = (IndexedEdge) obj;
    return this.index == indexedEdge.index;
  }
}
