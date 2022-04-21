import exceptions.InvalidDestinationVertexException;
import exceptions.InvalidSourceVertexException;

import java.util.*;

/**
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 */
public class MyGraph implements Graph {
  // you will need some private fields to represent the graph
  // you are also likely to want some private helper methods

  // YOUR CODE HERE

  private final HashTable<Vertex> vertices;
  private final HashTable<MyLinkedList> edges;

  /**
   * Creates a MyGraph object with the given collection of vertices and the
   * given collection of edges.
   *
   * @param v a collection of the vertices in this graph
   * @param e a collection of the edges in this graph
   */
  public MyGraph(Collection<Vertex> v, Collection<Edge> e) throws Exception {
    this.vertices = new HashTable<>(v.size());
    for (Vertex currVertex : v) {
      if (this.vertices._get(currVertex) != null) {
        continue;
      }
      this.vertices._insert(currVertex, currVertex);
    }
    this.edges = new HashTable<>(e.size());
    for (Edge edge : e) {
      Vertex source = this.vertices._get(edge.getSource());
      Vertex destination = this.vertices._get(edge.getDestination());
      if (source == null) {
        throw new InvalidSourceVertexException(edge.getSource().toString());
      }
      if (destination == null) {
        throw new InvalidDestinationVertexException(edge.getDestination().toString());
      }
      this.edges._insert(source, new MyLinkedList());
      MyLinkedList list = this.edges._get(source);
      Edge newEdge = new Edge(source, destination, edge.getWeight());
      if (list.check(newEdge)) {
        list.add(newEdge);
      }
    }
  }

  /**
   * Return the collection of vertices of this graph
   *
   * @return the vertices as a collection (which is anything iterable)
   */
  @Override
  public Collection<Vertex> vertices() {
    ArrayList<Vertex> vertexArrayList = new ArrayList<>();
    for (Vertex vertex : this.vertices) {
      vertexArrayList.add(vertex);
    }
    return vertexArrayList;
  }

  /**
   * Return the collection of edges of this graph
   *
   * @return the edges as a collection (which is anything iterable)
   */
  @Override
  public Collection<Edge> edges() {
    ArrayList<Edge> edgeArrayList = new ArrayList<>();
    for (MyLinkedList linkedList : this.edges) {
      edgeArrayList.addAll(linkedList);
    }
    return edgeArrayList;
  }

  /**
   * Return a collection of vertices adjacent to a given vertex v. i.e., the
   * set of all vertices w where edges v -> w exist in the graph. Return an
   * empty collection if there are no adjacent vertices.
   *
   * @param v one of the vertices in the graph
   * @return an iterable collection of vertices adjacent to v in the graph
   * @throws IllegalArgumentException if v does not exist.
   */
  @Override
  public Collection<Vertex> adjacentVertices(Vertex v) {
    if (vertices._get(v) == null) {
      throw new IllegalArgumentException("Invalid vertex");
    }
    ArrayList<Vertex> vertexArrayList = new ArrayList<>();
    MyLinkedList list = this.edges._get(v);
    for (Edge edge : list) {
      vertexArrayList.add(edge.getDestination());
    }
    return vertexArrayList;
  }

  /**
   * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed
   * graph. Assumes that we do not have negative cost edges in the graph.
   *
   * @param a one vertex
   * @param b another vertex
   * @return cost of edge if there is a directed edge from a to b in the
   * graph, return -1 otherwise.
   * @throws IllegalArgumentException if a or b do not exist.
   */
  @Override
  public int edgeCost(Vertex a, Vertex b) {
    if (vertices._get(a) == null) {
      throw new IllegalArgumentException("Invalid source vertex");
    }
    if (vertices._get(b) == null) {
      throw new IllegalArgumentException("Invalid destination vertex");
    }

    MyLinkedList list = this.edges._get(a);
    for (Edge e : list) {
      if (e.getDestination() == b) {
        return e.getWeight();
      }
    }
    return -1;
  }

  /**
   * Returns the shortest path from a to b in the graph, or null if there is
   * no such path. Assumes all edge weights are nonnegative. Uses Dijkstra's
   * algorithm.
   *
   * @param a the starting vertex
   * @param b the destination vertex
   * @return a Path where the vertices indicate the path from a to b in order
   * and contains a (first) and b (last) and the cost is the cost of
   * the path. Returns null if b is not reachable from a.
   * @throws IllegalArgumentException if a or b does not exist.
   */
  public Path shortestPath(Vertex a, Vertex b) {
    List<Vertex> v = new ArrayList<>();
    dijkstra(a);
    b = this.vertices._get(b);
    final int cost = b.d;
    while (true) {
      v.add(0, b);
      if (b.p == null) {
        if (!b.equals(a)) {
          return null;
        }
        return new Path(v, cost);
      }
      b = b.p;
    }
  }

  private void dijkstra(Vertex startVertex) {
    // Initialize all distances as
    // INFINITE and added[] as false
    for (Vertex v : this.vertices) {
      v.d = Integer.MAX_VALUE;
      v.p = null;
      v.marked = false;
    }

    // Distance of source vertex from
    // itself is always 0
    this.vertices._get(startVertex).d = 0;

    // Find shortest path for all
    // vertices
    for (Vertex i : this.vertices) {

      // Pick the minimum distance vertex
      // from the set of vertices not yet
      // processed. nearestVertex is
      // always equal to startNode in
      // first iteration.
      Vertex nearestVertex = null;

      int shortestDistance = Integer.MAX_VALUE;
      for (Vertex v : this.vertices) {
        if (!v.marked && v.d < shortestDistance) {
          nearestVertex = v;
          shortestDistance = v.d;
        }
      }
      if (nearestVertex == null) {
        return;
      }
      nearestVertex.marked = true;
      // Update dist value of the
      // adjacent vertices of the
      // picked vertex.
      MyLinkedList edges = this.edges._get(nearestVertex);
      if (edges != null) {
        for (Edge edge : edges) {
          int edgeDistance = edge.getWeight();
          Vertex destination = edge.getDestination();
          if ((shortestDistance + edgeDistance) < destination.d) {
            destination.p = nearestVertex;
            destination.d = shortestDistance + edgeDistance;
          }
        }
      }
    }
  }
}
