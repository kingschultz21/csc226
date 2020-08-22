/*
*	Based of simple implemenation from Robert Sedgewick and
*	Kevin Wayne in algs4.
*
*	Modified by: Connor Schultz
*/

public class Edge implements Comparable<Edge>{
	private int v;
	private int w;
	private double weight;

	//EDGE CONSTRUCTOR
	public Edge(int v, int w, double weight){
		if(v < 0 || w < 0){
			throw new IllegalArgumentException("Vertex must be a positive integer.");
		}
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	 /**
     * @return the weight of this edge
     */
	public double getWeight(){
		return this.weight;
	}
	 /**
     * @return either endpoint of this edge
     */
	public int either(){
		return v;
	}
	 /**
     * @return a string representing this edge
     */
    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight);
    }

	 /**
     * @param  vertex one endpoint of this edge
     * @return the other endpoint of this edge
     */
	public int other(int vertex){
		if(vertex == this.v)
			return w;
		else if(vertex == this.w)
			return v;
		else 
			throw new IllegalArgumentException("Illegal Vertex");
	}

	/**
     * Compares two edges by weight.
     * @param  e the other edge
     * @return a negative integer if weight of edge is less than e,
	 * 		   zero if the weights are equal
	 * 		   a positive integer if weight of edge is greater than e
     */
	@Override
	public int compareTo(Edge e){
		return Double.compare(this.weight, e.weight);
	}
}