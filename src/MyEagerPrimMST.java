import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;

import java.util.Comparator;

public class MyEagerPrimMST
{
  private final static double INFINITY = Double.POSITIVE_INFINITY;

  private final double[] disTo;
  private final Edge[] edgeTo;
  private final boolean[] onMST;
  private final IndexedPQ<Double> pq;

  private class SortByVertex implements Comparator<Edge>
  {
    @Override
    public int compare(Edge left, Edge right)
    {
      return Integer.compare(left.either(), right.either());
    }
  }

  public MyEagerPrimMST(EdgeWeightedGraph ewGraph)
  {
    disTo = new double[ewGraph.V()];
    pq = new IndexedPQ<>(ewGraph.V());
    edgeTo = new Edge[ewGraph.V()];
    onMST = new boolean[ewGraph.V()];

    for (int index = 0; index < ewGraph.V(); ++index)
    {
      disTo[index] = INFINITY;
    }

    pq.insert(0,0.0);
    disTo[0] = 0;
    while(!pq.isEmpty())
    {
      visit(ewGraph, pq.delTop());
    }
  }

  private void visit(EdgeWeightedGraph ewGraph, int v)
  {
    onMST[v] = true;

    for (Edge edge : ewGraph.adj(v))
    {
      int w = edge.other(v);
      if (onMST[w]) continue;

      // Is this the minimal weight edge to reach w?
      if (disTo[w] > edge.weight())
      {
        edgeTo[w] = edge;
        disTo[w]  = edge.weight();
        if (pq.contains(w)) pq.change(w, disTo[w]);
        else                pq.insert(w, disTo[w]);
      }
    }
  }

  public Iterable<Edge> edges()
  {
    // it is important to skip 0th element of edgeTo as it is null;
    //return Arrays.asList(edgeTo);

    PQ<Edge> edges = new PQ<>();
    for (int index = 1; index < edgeTo.length; ++index)
    {
      edges.insert(edgeTo[index]);
    }
    return edges;
  }

  public double getWeight()
  {
    double weight = 0.0;
    for (Edge edge : edges())
    {
      weight += edge.weight();
    }

    return weight;
  }

  public static void main(String[] args)
  {
    In in = new In("tinyEWG.txt");
    EdgeWeightedGraph ewg = new EdgeWeightedGraph(in);

    MyEagerPrimMST mst = new MyEagerPrimMST(ewg);
    System.out.println("Weight: " + mst.getWeight());

    in = new In("mediumEWG.txt");
    ewg = new EdgeWeightedGraph(in);

    mst = new MyEagerPrimMST(ewg);
    System.out.println("Weight: " + mst.getWeight());
  }
}
