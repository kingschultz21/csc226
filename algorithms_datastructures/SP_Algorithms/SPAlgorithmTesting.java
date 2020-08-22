import java.util.Scanner;
import edu.princeton.cs.algs4.*;
import java.io.File;
import java.util.concurrent.TimeUnit;
/*
*	Test class used to test various Shortest-Path Algorithms
*/
public class SPAlgorithmTesting{
	public static void main(String[] args){
		/*
		*	Input Adjacency Matrix and Graph Information
		*/
		Scanner input;
		if (args.length > 0){
			try{
				input = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			input = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		//Adjacency Matrix from output
		double[][] graph = (new AdjMatrix(input)).getMatrix();
		//allPairsPrint(graph);
		shortestPathsDijk(graph,0);
		shortestPathsBF(graph,0);
	}
	/**
	*	Format all-pairs shortest paths output.
	*	Trivially run Dijkstras on all vertices
	*	@param graph = weighted adjacency matrix
	*/
	public static void allPairsDijk(double[][] graph){
		System.out.println("Dijsktra Paths:");
		System.out.println("________________________"+"\n");
		for(int i = 0; i < graph.length; i++){
			DijkstrasSP dk = new DijkstrasSP(graph,i);
			for(int j = 0; j < graph.length; j++){
				System.out.printf("Path from %d to %d \n",i,j);

				int path_length = 0;
				if(dk.hasPathTo(j)){
					for(Edge e : dk.pathTo(j)){
						System.out.println(e);
						path_length++;
					}
				}else
					System.out.println("No Path.");
				if(i == j)
					System.out.println("  N/A");
				System.out.println("------------------------");
			}
		}
	}
	/**
	*	Format all-pairs shortest paths output.
	*	Trivially run Bellman-Ford on all vertices
	*	@param graph = weighted adjacency matrix
	*/
	public static void allPairsBF(double[][] graph, int src){
		System.out.println("Bellman-Ford Paths:");
		System.out.println("________________________"+"\n");
		for(int i = 0; i < graph.length; i++){
			BellmanFordsSP bf = new BellmanFordsSP(graph,i);
			for(int j = 0; j < graph.length; j++){
				System.out.printf("Path from %d to %d \n",i,j);

				int path_length = 0;
				if(bf.hasPathTo(j)){
					for(Edge e : bf.pathTo(graph,j,src)){
						System.out.println(e);
						path_length++;
					}
				}else
					System.out.println("No Path.");
				if(i == j)
					System.out.println("  N/A");
				System.out.println("------------------------");
			}
		}
	}
	/**
	*	Format shortest path output from src to all other vertices using Dijkstras
	*	@param graph = weighted adjacency matrix
	*	@param src = source vertex
	*/
	public static void shortestPathsDijk(double[][] graph, int src){
		System.out.println("Dijsktra Paths:");
		System.out.println("________________________"+"\n");
		DijkstrasSP dk = new DijkstrasSP(graph,src);
		for(int i = 0; i < graph.length; i++){
			System.out.printf("Path from source (%d) to %d\n",src,i);
			int path_weight = 0;
			if(i == src){
				System.out.println("N/A");
			}else if(dk.hasPathTo(i)){
				for(Edge e : dk.pathTo(i)){
					System.out.println(e);
					path_weight += e.getWeight();
				}
			}else{
				System.out.println("No Path.");
			}
			System.out.println("Total path weight: "+path_weight);
			System.out.println("________________________"+"\n");
		}
	}
	/**
	*	Format shortest path output from src to all other vertices using Bellman-Ford
	*	@param graph = weighted adjacency matrix
	*	@param src = source vertex
	*/
	public static void shortestPathsBF(double[][] graph, int src){
		System.out.println("Bellman-Ford Paths:");
		System.out.println("________________________"+"\n");
		BellmanFordsSP bf = new BellmanFordsSP(graph,src);
		for(int i = 0; i < graph.length; i++){
			System.out.printf("Path from source (%d) to %d\n",src,i);
			int path_weight = 0;
			if(i == src){
				System.out.println("N/A");
			}else if(bf.hasPathTo(i)){
				for(Edge e : bf.pathTo(graph,i,src)){
					System.out.println(e);
					path_weight += e.getWeight();
				}
			}else{
				System.out.println("No Path.");
			}
			System.out.println("Total path weight: "+path_weight);
			System.out.println("________________________"+"\n");
		}
	}
}