import edu.princeton.cs.algs4.*;

public class DijkstraSP
{
  private final double[] distTo;
  private final DirectedEdge[] edgeTo;
  private final IndexedPQ<Double> pq;

  public DijkstraSP(EdgeWeightedDigraph ewdg, int source)
  {
    distTo = new double[ewdg.V()];
    edgeTo = new DirectedEdge[ewdg.V()];
    pq = new IndexedPQ<>(ewdg.V());

    for (int index = 0; index < ewdg.V(); ++index)
    {
      distTo[index] = Double.POSITIVE_INFINITY;
    }

    pq.insert(source, 0.0);
    distTo[source] = 0;

    while (!pq.isEmpty())
    {
      visit(ewdg, pq.delTop());
    }
  }

  private void visit(EdgeWeightedDigraph ewdg, int v)
  {
    for (DirectedEdge edge : ewdg.adj(v))
    {
      int w = edge.to();

      // Is this the edge of minimal (till now) weight to reach w?
      if (distTo[w] > distTo[v] + edge.weight())
      {
        edgeTo[w] = edge;
        distTo[w] = distTo[v] + edge.weight();

        if (pq.contains(w)) pq.change(w, distTo[w]);
        else                pq.insert(w, distTo[w]);
      }
    }
  }

  public Iterable<DirectedEdge> pathTo(int v)
  {
    Stack<DirectedEdge> edges = new Stack<>();

    while (edgeTo[v] != null)
    {
      edges.push(edgeTo[v]);
      v = edgeTo[v].from(); // tricky!
    }

    return edges;
  }

  public boolean hasPathTo(int v) { return edgeTo[v] != null; }

  public double distTo(int v) { return distTo[v]; }

  public static void main(String[] args)
  {
    In in = new In("tinyEWD.txt");
    EdgeWeightedDigraph ewg = new EdgeWeightedDigraph(in);
    int source = 0;

    DijkstraSP sp = new DijkstraSP(ewg, source);

    for (int t = 0; t < ewg.V(); ++t)
    {
      StdOut.print(source + " to " + t);
      StdOut.printf("  (%4.2f): ", sp.distTo[t]);

      if (sp.hasPathTo(t))
      {
        for (DirectedEdge edge : sp.pathTo(t))
        {
          StdOut.print(edge + "  ");
        }
      }

      StdOut.println();
    }
  }

}
