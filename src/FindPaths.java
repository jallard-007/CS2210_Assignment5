import java.util.*;
import java.io.*;

/**
 * Driver program that reads in a graph and prompts user for shortests paths in the graph.
 * (Intentionally without comments.  Read through the code to understand what it does.)
 */

public class FindPaths {
	public static void main(String[] args) {
		if (args.length == 0) {
			args = new String[2];
			args[0] = "./src/vertex.txt";
			args[1] = "./src/other.txt";
		}
		if(args.length != 2) {
			System.err.println("USAGE: java Paths <vertex_file> <edge_file>");
			System.exit(1);
		}
		MyGraph g = null;
		try {
   		g = readGraph(args[0],args[1]);
		} catch (Exception e) {
			System.exit(1);
		}

		@SuppressWarnings("resource")
		Scanner console = new Scanner(System.in);
		Collection<Vertex> v = g.vertices();
		Collection<Edge> e = g.edges();
		System.out.println("Vertices are :");
		for (Vertex vertex : v) {
			if (vertex != null)
			  System.out.print(vertex.getLabel() + ", ");
		}
		System.out.println("\nEdges are :");
		for (Edge edge : e) {
			System.out.print(edge.toString() + ", ");
		}

		while(true) {
			System.out.print("\nStart vertex? ");
			Vertex a = new Vertex(console.nextLine());
			if (!v.contains(a)) {
				System.out.println("no such vertex");
				System.exit(1);
			}

			System.out.print("Destination vertex? ");
			Vertex b = new Vertex(console.nextLine());
			if (!v.contains(b)) {
				System.out.println("no such vertex");
				System.exit(1);
			}

			Path p = g.shortestPath(a, b);
			if (p == null) {
				System.out.println("No path exists");
			} else {
				for (Vertex u : p.vertices) {
					System.out.print(u.toString() + ", ");
				}
				System.out.println("\nCost: " + p.cost);
		  }
		}
	}

	public static MyGraph readGraph(String f1, String f2) throws Exception {
		Scanner s = null;
		try {
			s = new Scanner(new File(f1));
		} catch(FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: "+f1);
			System.exit(2);
		}

		Collection<Vertex> v = new ArrayList<Vertex>();
		while(s.hasNext())
			v.add(new Vertex(s.next()));

		try {
			s = new Scanner(new File(f2));
		} catch(FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: "+f2);
			System.exit(2);
		}

		Collection<Edge> e = new ArrayList<Edge>();
		while(s.hasNext()) {
			try {
				Vertex a = new Vertex(s.next());
				Vertex b = new Vertex(s.next());
				int w = s.nextInt();
				e.add(new Edge(a,b,w));
			} catch (NoSuchElementException e2) {
				System.err.println("EDGE FILE FORMAT INCORRECT");
				System.exit(3);
			}
		}

		return new MyGraph(v,e);
	}
}
