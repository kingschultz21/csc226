
import java.util.Scanner;
import edu.princeton.cs.algs4.*;
import java.io.File;
import java.util.concurrent.TimeUnit;
/*
*	Test class used to test various Minimum Spanning Tree Algorithms
*/
public class MSTAlgorithmTesting{
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

		long p_s = System.nanoTime();
		PrimsMST p_mst = new PrimsMST(graph);
		long p_e = System.nanoTime();
		double p_t = (double) (p_e - p_s) / 1000000;

		long k_s = System.nanoTime();
		KruskalsMST k_mst = new KruskalsMST(graph);
		long k_e = System.nanoTime();
		double k_t = (double) (k_e - k_s) / 1000000;

		long b_s = System.nanoTime();
		BoruvkasMST b_mst = new BoruvkasMST(graph);
		long b_e = System.nanoTime();
		double b_t = (double) (b_e - b_s) / 1000000;

		prettyPrint(p_mst, p_t, k_mst, k_t, b_mst, b_t);

	}
	/*
	*	Format output in a reasonable manner
	*/
	public static void prettyPrint(PrimsMST prim, double prim_time,
								   KruskalsMST kruskal, double kruskal_time, 
								   BoruvkasMST boruvka, double boruvka_time){
		/*
		*	Prims MST details
		*/
		System.out.println("Prims MST:");
		System.out.println("____________");
		for (Edge p_e : prim.edges()) {
            StdOut.println(p_e);
		}
		System.out.println("Weight: "+prim.weight());
		System.out.println("Running Time (m.s.): "+prim_time);
		System.out.println("____________"+"\n");
		/*
		*	Kruskals MST details
		*/
		System.out.println("Kruskals MST:");
		System.out.println("____________");
		for (Edge k_e : kruskal.edges()) {
            StdOut.println(k_e);
		}
		System.out.println("Weight: "+kruskal.weight());
		System.out.println("Running Time (m.s.): "+kruskal_time);
		System.out.println("____________"+"\n");
		/*
		*	Boruvkas MST details
		*/
		System.out.println("Boruvkas MST:");
		System.out.println("____________");
		for (Edge b_e : boruvka.edges()) {
            StdOut.println(b_e);
		}
		System.out.println("Weight: "+boruvka.weight()+"\n");
		System.out.println("Running Time (m.s.): "+boruvka_time);
		System.out.println("____________"+"\n");
		
	}

}