import exceptions.RepeatEdgeException;

public class MyLinkedList extends java.util.LinkedList<Edge> {
  public MyLinkedList() {
    super();
  }

  public boolean check(Edge edge) throws RepeatEdgeException {
    for (Edge currEdge : this) {
      if (currEdge.getSource().equals(edge.getSource()) && currEdge.getDestination().equals(edge.getDestination())) {
        if (currEdge.getWeight() == edge.getWeight()) {
          return false;
        }
        throw new RepeatEdgeException("repeat edge: " + edge);
      }
    }
    return true;
  }
}
