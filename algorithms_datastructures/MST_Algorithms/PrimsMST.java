/*
*	Based of implemenation from Robert Sedgewick and
*	Kevin Wayne in algs4. Modified to accept a weighted adjaceny matrix
*	as input. Uses an Index Minimum Priority Queue for effciency.
*
*	Modified by: Connor Schultz
*/
import edu.princeton.cs.algs4.*;

public class PrimsMST{
	/*
	*	Prims MST Algorithm Class Variables
	*/
	private Edge[] edgeTo;		//shortest edges
	private double[] distTo;	//weight of edges
	private boolean[] marked;	//Determines if vertex is alrady in mst
	private IndexMinPQ<Double> pq;
	/**
	*	Compute MST or Forest of weighted adjaceny matrix
	*	@param graph = weighted adjaceny matrix
	*/
	public PrimsMST(double[][] graph){
		int num_vertices = graph.length;
		edgeTo = new Edge[num_vertices];
        distTo = new double[num_vertices];
        marked = new boolean[num_vertices];
		pq = new IndexMinPQ<Double>(num_vertices);
		/*
		*	Initialize all distances to Infinity
		*/
        for (int v = 0; v < num_vertices; v++)
            distTo[v] = Double.POSITIVE_INFINITY;
		/*
		*	Run Prims on all vertices not already in the MST
		*/
        for (int v = 0; v < num_vertices; v++)      
            if (!marked[v]) prim(graph, v);
	}
	/**
	*	Run Prims Algorithim in adjaceny matrix graph 
	*	from source vertex src
	*	@param graph = weighted adjacency matrix
	*	@param src = source vertex
	*/
    private void prim(double[][] graph, int src) {
        distTo[src] = 0;
        pq.insert(src, distTo[src]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(graph, v);
        }
	}
	/**
	*	Scan vertex and investigate each adjacent vertex
	*	Add to MST if appropriate
	*	@param graph = weighted adjacency matrix
	*	@param vertex = vertex under investigation
	*/
	private void scan(double[][] graph, int vertex){
		int num_vertices = graph.length;
		marked[vertex] = true;
		for(int adj = 0; adj < num_vertices; adj++){
			if(graph[vertex][adj] != 0.0){
				Edge e = new Edge(vertex,adj, graph[vertex][adj]);
				int w = e.other(vertex);
				if(marked[w]){ //Vertex has already been investigated
					continue;
				}
				if(e.getWeight() < distTo[w]){
					distTo[w] = e.getWeight();
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
	/**
	*	Returns the Edges of the Prims MST or Forest
	*	@return iterable of Edge objects
	*/
	public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<Edge>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
	}
	/**
	*	Returns the Total Weight of the Prims MST or Forest
	*	@return floating point total weight of the MST
	*/
	public double weight() {
        double weight = 0;
        for (Edge e : edges())
            weight += e.getWeight();
        return weight;
    }
}