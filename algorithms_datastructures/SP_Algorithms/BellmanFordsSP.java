import edu.princeton.cs.algs4.*;

public class BellmanFordsSP{
	double[] distances;
	double[] predecessor;
	public BellmanFordsSP(double[][] graph, int src){
		distances = new double[graph.length];
		predecessor = new double[graph.length];
		for(int i = 0; i < graph.length; i++){
			distances[i] = Double.POSITIVE_INFINITY;
			predecessor[i] = Double.POSITIVE_INFINITY;
		}
		distances[src] = 0.0;

		for(int j = 1; j < graph.length; j++){
			for(Edge e : getEdges(graph)){
				int v = e.either();
				int w = e.other(v);
				if(distances[v] + e.getWeight() < distances[w]){
					distances[v] = distances[w] + e.getWeight();
                	predecessor[w] = v;
				}
			}
		}
	}
	/**
	*	Returns the Edges of the input graph (greasy solution)
	*	@return iterable of Edge objects
	*/
	public static Iterable<Edge> getEdges(double[][] G){
		double[][] graph =  G;
		Queue<Edge> edges = new Queue<Edge>();
        for (int i = 0; i < graph.length; i++){
            for(int j = 0; j < graph.length; j++){
				if(graph[i][j] != 0){
					Edge e =  new Edge(i,j,graph[i][j]);
					edges.enqueue(e);
				}
			}
        }
        return edges;
	}
	/**
	*	Returns true or false depending on if there is a path from src to v
	*	@return true if the distance from src to v is < Infinity
	*	@return false if the distance from src to v == Infinity
	*/
	public boolean hasPathTo(int v){
		System.out.println(distances[v]);
		return distances[v] < Double.POSITIVE_INFINITY;
	}
	/**
	*	Returns the shortest path from src to v if it exists, null otherwise
	*	@return shortest path from src to v, null if no such path exists
	*/
	public Iterable<Edge> pathTo(double[][] adj, int v, int src){
		if(!hasPathTo(v))
			return null;
		Stack<Edge> path = new Stack<Edge>();
		int temp = v;
		for(Edge e = new Edge(src,v,adj[src][v]); e != null; e = new Edge(src,temp,adj[src][temp])){
			System.out.println("Here");
			path.push(e);
			temp = e.other(temp);
		}
		return path;
	} 
}