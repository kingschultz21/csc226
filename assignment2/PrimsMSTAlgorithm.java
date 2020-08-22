import java.util.Scanner;
import java.io.File;
import edu.princeton.cs.algs4.*;

public class PrimsMSTAlgorithm{
	//Prims Class variables
	private Edge[] edgeTo;		//shortest edges
	private double[] distTo;	//weight of edges
	private boolean[] marked;	//Determines if vertex is alrady in mst
	private IndexMinPQ<Double> pq;

	/*
	*	Compute MST or Forest of weighted adjaceny matrix
	*	@param graph = weighted adjaceny matrix
	*/
	public PrimsMSTAlgorithm(double[][] graph){
		int num_vertices = graph.length;
		edgeTo = new Edge[num_vertices];
        distTo = new double[num_vertices];
        marked = new boolean[num_vertices];
		pq = new IndexMinPQ<Double>(num_vertices);
		
        for (int v = 0; v < num_vertices; v++)
            distTo[v] = Double.POSITIVE_INFINITY;	//Init weights to infinity

        for (int v = 0; v < num_vertices; v++)      //run prim from each vertex that 
            if (!marked[v]) prim(graph, v);			//Isnt already in the tree
	}

	/*
	*	Run Prims Algorithim in adjaceny matrix graph from source vertex src
	*/
    private void prim(double[][] graph, int src) {
        distTo[src] = 0;
        pq.insert(src, distTo[src]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(graph, v);
        }
	}

	/*
	*	Scan vertex and invetigate each adjacent vertex
	*	Add to MST if appropriate
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
				if(e.weight() < distTo[w]){
					distTo[w] = e.weight();
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
	
	/*
	*	Returns the Edges of the Prims MST or Forest
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
	/*
	*	Returns the Total Weight of the Prims MST or Forest
	*/
	public double weight() {
        double weight = 0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }
	
	/*
	*	TESTING FUNCTIONS
	*/
	public static void main(String[] args) {
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int n = s.nextInt();
		double [][] G = new double [n][n];
		int valuesRead = 0;
		for (int i = 0; i < n && s.hasNextDouble(); i++){
			for (int j = 0; j < n && s.hasNextDouble(); j++){
				G[i][j] = s.nextDouble();
				if (i == j && G[i][j] != 0.0) {
					System.out.printf("Adjacency matrix contains self-loops.\n");
					return;
				}
				if (G[i][j] < 0.0) {
					System.out.printf("Adjacency matrix contains negative values.\n");
					return;
				}
				if (j < i && G[i][j] != G[j][i]) {
					System.out.printf("Adjacency matrix is not symmetric.\n");
					return;
				}
				valuesRead++;
			}
		}
		
		if (valuesRead < n*n){
			System.out.printf("Adjacency matrix for the graph contains too few values.\n");
			return;
		}	
		
		PrimsMSTAlgorithim mst = new PrimsMSTAlgorithim(G);
		for (Edge e : mst.edges()) {
            StdOut.println(e);
		}
		StdOut.println(mst.weight());
    }

}