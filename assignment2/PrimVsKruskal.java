/* PrimVsKruskal.java
   CSC 226 - Spring 2019
   Assignment 2 - Prim MST versus Kruskal MST Template
   
   The file includes the "import edu.princeton.cs.algs4.*;" so that yo can use
   any of the code in the algs4.jar file. You should be able to compile your program
   with the command
   
	javac -cp .;algs4.jar PrimVsKruskal.java
	
   To conveniently test the algorithm with a large input, create a text file
   containing a test graphs (in the format described below) and run
   the program with
   
	java -cp .;algs4.jar PrimVsKruskal file.txt
	
   where file.txt is replaced by the name of the text file.
   
   The input consists of a graph (as an adjacency matrix) in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry G[i][j] >= 0.0 of the adjacency matrix gives the weight (as type double) of the edge from 
   vertex i to vertex j (if G[i][j] is 0.0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that G[i][j]
   is always equal to G[j][i].


   R. Little - 03/07/2019
*/

 import edu.princeton.cs.algs4.*;
 import java.util.Scanner;
 import java.io.File;
 import java.util.Arrays;
 import java.util.List;

//Do not change the name of the PrimVsKruskal class
public class PrimVsKruskal{

//==========================================================================================
//											PRIMS
//==========================================================================================
	static class PrimsMSTBuilder{
		//Prims Class variables
		private Edge[] edgeTo;		//shortest edges
		private double[] distTo;	//weight of edges
		private boolean[] marked;	//Determines if vertex is alrady in mst
		private IndexMinPQ<Double> pq;
	
		/*
		*	Compute MST or Forest of weighted adjaceny matrix
		*	@param graph = weighted adjaceny matrix
		*/
		public PrimsMSTBuilder(double[][] graph){
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
						/*
						*	Ensure that edges are added in ascending order
						*	(ie: 1-2 instead of 2-1) 
						*/
						if(e.other(vertex) > vertex)
							edgeTo[w] = e;
						else{
							int src = e.either();
							int dest = e.other(src);
							edgeTo[w] = new Edge(dest,src,e.weight());

						}
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
	}

//==========================================================================================
//											KRUSKALS
//==========================================================================================
	static class KruskalsMSTBuilder{
		//Kruskals Class variables
		private double weight;	//Total weight of the MST
		private Queue<Edge> mst = new Queue<Edge>();	//Edges in the MST
		/*
		*	Compute MST or Forest of weighted adjaceny matrix
		*	@param graph = weighted adjacency matrix
		*/
		public KruskalsMSTBuilder(double[][] graph){
			int num_vertices = graph.length;
			MinPQ<Edge> pq = new MinPQ<Edge>();
			for(int i = 0; i < num_vertices; i++){	//Iterate through all vertices
				for(int j =0; j < num_vertices; j++){
					if(graph[i][j] != 0.0){			//If Weight is 0, move on
						Edge e = new Edge(i,j,graph[i][j]);
						if(e.other(i) > i)
							pq.insert(e);
						//System.out.println(e.weight());
						//pq.insert(e);
					}
				}
			}
	
			//Kruskals Algorithm
			UF unionFind = new UF(num_vertices);
			while(!pq.isEmpty() && mst.size() < num_vertices){
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
		//Return amount of edges in MST
		public int size(){
			return mst.size();
		}
	}

//==========================================================================================
//										PRIM VS. KRUSKAL
//==========================================================================================
	/* PrimVsKruskal(G)
		Given an adjacency matrix for connected graph G, with no self-loops or parallel edges,
		determine if the minimum spanning tree of G found by Prim's algorithm is equal to 
		the minimum spanning tree of G found by Kruskal's algorithm.
		
		If G[i][j] == 0.0, there is no edge between vertex i and vertex j
		If G[i][j] > 0.0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static boolean PrimVsKruskal(double[][] G){
		PrimsMSTBuilder mst_p = new PrimsMSTBuilder(G);
		KruskalsMSTBuilder mst_k = new KruskalsMSTBuilder(G);
		boolean pvk = true;
		int num_edges_k = mst_k.size();
		int compare;
		//Simply compare each edge to see if the trees are equal
		for(Edge e_k : mst_k.edges()){
			for(Edge e_p : mst_p.edges()){
				compare = compareEdges(e_k, e_p);
				if(compare == 0){
					num_edges_k--;
				}
			}
			
			
		}
		//					DEBUG STATEMENTS
		if(num_edges_k != 0){
			//System.out.println("Edge Difference: "+num_edges_k);
			pvk = false;
		}
		if(mst_k.weight() != mst_p.weight()){
			System.out.println("Trees are not the same weight!");
			pvk = false;
		
		}
		return pvk;	
	}
	/*
	*	Compare Edges function used for comparing Edges
	*/
	public static int compareEdges(Edge edge1, Edge edge2){
		int result;
		int v1 = edge1.either();
		int v2 = edge2.either();
		int w1 = edge1.other(v1);
		int w2 = edge2.other(v2);
		if(v1 > v2)
			result = 1;
		else if(v1 == v2)
			if(w1 > w2)
				result =1;
			else if(w1 == w2)
				result = 0;
			else
				result = -1;
		else
			result = -1;
		return result;
	}
//==========================================================================================
//											TESTING
//==========================================================================================
	/* main()
	   Contains code to test the PrimVsKruskal function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below. 
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
		double[][] G = new double[n][n];
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
		/*
		PrimsMSTBuilder mst_P = new PrimsMSTBuilder(G);
		StdOut.println("Prim Tree:"+"\n---------------");
		for (Edge e : mst_P.edges()) {
            StdOut.println(e+"\n---------------");
		}
		StdOut.println("PrimMST weight = "+mst_P.weight()+"\n---------------");

		KruskalsMSTBuilder mst_K = new KruskalsMSTBuilder(G);
		StdOut.println("\nKruskal Tree:"+"\n---------------");
		for (Edge e : mst_K.edges()){
            StdOut.println(e+"\n---------------");
		}
		StdOut.println("KruskalMST weight = "+mst_K.weight()+"\n---------------");
		*/
        boolean pvk = PrimVsKruskal(G);
        System.out.printf("Does Prim MST = Kruskal MST? %b\n", pvk);
    }
}
