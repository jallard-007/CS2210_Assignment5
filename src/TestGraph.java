import exceptions.InvalidDestinationVertexException;
import exceptions.InvalidSourceVertexException;
import exceptions.RepeatEdgeException;

import java.util.Collection;

public class TestGraph {
  public static void main(String[] args) {

    // test 1. invalid source edge
    try {
      FindPaths.readGraph("./src/testFiles/test0Vertex.txt", "./src/testFiles/test1Edge.txt");
      System.out.println("test 1 fail");
    } catch (Exception e) {
      if (e instanceof InvalidSourceVertexException) {
        System.out.println("test 1 pass, " + e);
      } else {
        System.out.println("test 1 fail");

      }
    }

    // test 2. invalid destination edge.
    try {
      FindPaths.readGraph("./src/testFiles/test0Vertex.txt", "./src/testFiles/test2Edge.txt");
      System.out.println("test 2 fail");
    } catch (Exception e) {
      if (e instanceof InvalidDestinationVertexException) {
        System.out.println("test 2 pass, " + e);
      } else {
        System.out.println("test 2 fail");
      }
    }

    //test 3 repeat edge with different weight. SEB -> YAYA is repeated
    try {
      FindPaths.readGraph("./src/testFiles/test0Vertex.txt", "./src/testFiles/test3Edge.txt");
      System.out.println("test 3 fail");
    } catch (Exception e) {
      if (e instanceof RepeatEdgeException) {
        System.out.println("test 3 pass, " + e);
      } else {
        System.out.println("test 3 fail");
      }
    }

    //test 4 repeat edge with same weight. SEB -> YAYA is repeated. No exception should be thrown. Only once instance of the edge should be present
    try {
      Graph g = FindPaths.readGraph("./src/testFiles/test0Vertex.txt", "./src/testFiles/test4Edge.txt");
      System.out.print("test 4 pass, edges are: ");
      Collection<Edge> e = g.edges();
      for (Edge edge : e) {
        System.out.print(edge.toString() + ", ");
      }
    } catch (Exception e) {
      System.out.println("test 4 fail");
    }
  }
}
