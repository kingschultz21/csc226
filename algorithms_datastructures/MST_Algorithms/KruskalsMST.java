/**
*	Based of implemenation from Robert Sedgewick and
*	Kevin Wayne in algs4. Modified to accept a weighted adjaceny matrix
*	as input. Uses a Union Find Data Structure for effciency.
*
*	Modified by: Connor Schultz
*/
import edu.princeton.cs.algs4.*;

public class KruskalsMST{
	/*
	*	Kruskals MST Algorithm Class Variables
	*/
	private double weight;	//Total weight of the MST
	private Queue<Edge> mst = new Queue<Edge>();   //Edges in the MST
	/**
	*	Compute MST or Forest of weighted adjaceny matrix
	*	@param graph = weighted adjaceny matrix
	*/
	public KruskalsMST(double[][] graph){
		int num_vertices = graph.length;
		MinPQ<Edge> pq = new MinPQ<Edge>();
		for(int i = 0; i < num_vertices; i++){	//Iterate through all vertices
			for(int j =0; j < num_vertices; j++){
				if(graph[i][j] != 0.0){			//If Weight is 0, move on
					Edge e = new Edge(i,j,graph[i][j]);
					pq.insert(e);
				}
			}
		}

		/*
		*	KRUSKALS ALGORITHM
		*/
		UF unionFind = new UF(num_vertices);
		while(!pq.isEmpty() && mst.size() < num_vertices-1){
			Edge e = pq.delMin();
			int v = e.either();
			int w = e.other(v);
			if(!unionFind.connected(v,w)){ //Disregard v-w if it creates a cycle
				unionFind.union(v,w);	//Union the components of v and w
				mst.enqueue(e);			//Add e to MST
				weight += e.getWeight();
			}
		}
	}
	/**
	*	Returns the Edges of the Kruskals MST or Forest
	*	@return iterable of Edge objects
	*/
	public Iterable<Edge> edges() {
        return mst;
	}
	/**
	*	Returns the Total Weight of the Kruskals MST or Forest
	*	@return floating point total weight of the MST
	*/
	public double weight() {
        return weight;
    }
}