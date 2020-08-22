import java.util.Scanner;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.Queue;
/*
*	Create an Adjaceny Matrix from Standard Input Stream
*/
public class AdjMatrix{
	//Graph class variable (Adjaceny Matrix)
	private double[][] graph;
	private Scanner input;

	
	//Adjaceny Matrix Constructor
	public AdjMatrix(Scanner input){
		this.input = input;
		this.graph = createAdjMatrix(input);
	}

	 /**
     * 	@param  input input stream containg the adjaceny matrix
	 * 		   and graph details.
     * 	@return the adjaceny matrix contained in the input
     */
	private double[][] createAdjMatrix(Scanner input){
		System.out.println("Reading Values from Standard Input...");

		int n = input.nextInt();
		double[][] graph = new double[n][n];
		int valuesRead = 0;

		for (int i = 0; i < n && input.hasNextDouble(); i++){
			for (int j = 0; j < n && input.hasNextDouble(); j++){
				graph[i][j] = input.nextDouble();
				if (i == j && graph[i][j] != 0.0) {
					System.out.printf("Adjacency matrix contains self-loops.\n");
					break;
				}
				if (graph[i][j] < 0.0) {
					System.out.printf("Adjacency matrix contains negative values.\n");
					break;
				}
				if (j < i && graph[i][j] != graph[j][i]) {
					System.out.printf("Adjacency matrix is not symmetric.\n");
					break;
				}
				valuesRead++;
			}
		}
		if (valuesRead < n*n){
			System.out.printf("Adjacency matrix for the graph contains too few values.\n");
		}
		return graph;
	}
	/**
     * @return the adjacency matrix
     */
	public double[][] getMatrix(){
		return this.graph;
	}
}