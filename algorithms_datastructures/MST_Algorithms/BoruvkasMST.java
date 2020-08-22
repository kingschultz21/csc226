/**
*	Based of implemenation from Robert Sedgewick and
*	Kevin Wayne in algs4. Modified to accept a weighted adjaceny matrix
*	as input. Uses a Union Find Data Structure for effciency.
*
*	Modified by: Connor Schultz
*/
import edu.princeton.cs.algs4.*;

public class BoruvkasMST{
	/*
	*	Boruvkas MST Algorithm Class Variables
	*/
	private Queue<Edge> mst = new Queue<Edge>(); //Edges in the MST
	private double weight; //Total weight of MST
	/**
	*	Compute MST or Forest of weighted adjaceny matrix
	*	@param graph = weighted adjaceny matrix
	*/
	public BoruvkasMST(double[][] graph){
		int num_vertices = graph.length;
		UF uf = new UF(num_vertices);

		//Repeat log v or v-1 edges times
		for(int i = 1; i < num_vertices && mst.size() < num_vertices-1; i = i+i){
			Edge[] closest = new Edge[num_vertices];
			/*
			*	For each tree in the forest, find the cheapest edge
			*/
			for(Edge e : getEdges(graph)){
				int v = e.either();
				int w = e.other(v);
				int p = uf.find(v);
				int q = uf.find(w);

				//Vertices are in the same tree
				if(p == q)
					continue;

				if(closest[p] == null || e.getWeight() < closest[p].getWeight())
					closest[p] = e;
				if(closest[q] == null || e.getWeight() < closest[q].getWeight())
					closest[q] = e;
			}
			/*
			*	Add the new edges to the MST
			*/
			for(int j = 0; j < num_vertices; j++){
				Edge e = closest[j];
				if(e!=null){
					int v = e.either();
					int w = e.other(v);
					
					//Check if already in MST
					if(!uf.connected(v,w)){
						mst.enqueue(e);
						weight += e.getWeight();
						uf.union(v,w);
					}
				}
			}
		}

	}
	/**
	*	Returns the Edges of the Boruvkas MST or Forest
	*	@return iterable of Edge objects
	*/
	public Iterable<Edge> edges() {
        return mst;
	}
	/**
	*	Returns the Total Weight of the Boruvkas MST or Forest
	*	@return floating point total weight of the MST
	*/
	public double weight() {
        return weight;
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
}
