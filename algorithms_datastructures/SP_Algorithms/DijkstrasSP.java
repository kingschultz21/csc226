/*
*	Based of implemenation from Robert Sedgewick and
*	Kevin Wayne in algs4. Modified to accept a weighted adjaceny matrix
*	as input. Uses an Index Minimum Priority Queue for effciency.
*
*	Modified by: Connor Schultz
*/
import edu.princeton.cs.algs4.*;

public class DijkstrasSP{
	/*
	*	Dijkstra SP Algorithm Class Variables
	*/
	private Edge[] edgeTo;		//shortest edges
	private double[] distTo;	//weight of edges
	private IndexMinPQ<Double> pq; //vertices
	/**
	*	Compute shortest path tree from vertex to s, to every other vertex in graph
	*	@param graph = weighted adjaceny matrix
	*	@param src = source vertex
	*/
	public DijkstrasSP(double[][] graph, int src){
		int num_vertices =  graph.length;
		edgeTo = new Edge[num_vertices];
		distTo = new double[num_vertices];
		/*
		*	Initialize all distances to Infinity and source vertex to zero
		*/
        for (int v = 0; v < num_vertices; v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[src] = 0.0;

		pq = new IndexMinPQ<Double>(num_vertices);
		pq.insert(src,distTo[src]);
		while(!pq.isEmpty()){
			int v = pq.delMin();
			/*
			*	Relax vertices in order
			*/
			for(int adj = 0; adj < num_vertices; adj++){
					if(graph[v][adj] != 0.0){
						Edge e = new Edge(v,adj, graph[v][adj]);
						/*
						*	Relax the edge and update the pq if neccessary
						*/
						int w = e.other(v);
						if(distTo[v] + e.getWeight() < distTo[w]){
							distTo[w] = distTo[v] + e.getWeight();
							edgeTo[w] = e;
							if(pq.contains(w)){
								pq.decreaseKey(w, distTo[w]);
							} else{
								pq.insert(w, distTo[w]);
							}
						}
					}
			}
		}
	}
	/**
	*	Returns true or false depending on if there is a path from src to v
	*	@return true if the distance from src to v is < Infinity
	*	@return false if the distance from src to v == Infinity
	*/
	public boolean hasPathTo(int v){
		return distTo[v] < Double.POSITIVE_INFINITY;
	}

	/**
	*	Returns the shortest path from src to v if it exists, null otherwise
	*	@return shortest path from src to v, null if no such path exists
	*/
	public Iterable<Edge> pathTo(int v){
		if(!hasPathTo(v))
			return null;
		Stack<Edge> path = new Stack<Edge>();
		int temp = v;
		for(Edge e = edgeTo[v]; e != null; e = edgeTo[temp]){
			path.push(e);
			temp = e.other(temp);
		}
		return path;
	} 
}