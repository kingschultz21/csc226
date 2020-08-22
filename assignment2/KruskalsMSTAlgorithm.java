import java.util.Scanner;
import java.io.File;
import edu.princeton.cs.algs4.*;

public class KruskalsMSTAlgorithm{
	//Kruskals Class variables
	private double weight;	//Total weight of the MST
	private Queue<Edge> mst = new Queue<Edge>();	//Edges in the MST

	/*
	*	Compute MST or Forest of weighted adjaceny matrix
	*	@param graph = weighted adjaceny matrix
	*/
	public KruskalsMSTAlgorithm(double[][] graph){
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

		//Kruskals Algorithm
		UF unionFind = new UF(num_vertices);
		while(!pq.isEmpty() && mst.size() < num_vertices-1){
			Edge e = pq.delMin();
			int v = e.either();
			int w = e.other(v);
			if(!unionFind.connected(v,w)){ //Disregard v-w if it creates a cycle
				unionFind.union(v,w);	//Union the components of v and w
				mst.enqueue(e);			//Add e to MST
				weight += e.weight();
			}
		}
	}
	//Returns all Edges and as interable of edges
	public Iterable<Edge> edges() {
        return mst;
	}
	//Returns the total weight of MST
	public double weight() {
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
		
		KruskalsMSTAlgorithm mst = new KruskalsMSTAlgorithm(G);
		for (Edge e : mst.edges()) {
            StdOut.println(e);
		}
		StdOut.println(mst.weight());
    }
}