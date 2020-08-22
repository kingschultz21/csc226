import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*
* Code modified by: Connor Schultz (V00872923)
* for CSC 226 Programming Assignment 1
* Due: 08/02/2019
*/

public class RedBlackBST {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    //Number of red nodes (links) in tree
    private float numRed = 0;
    //Number of black nodes (links) in tree
    private float numBlack = 0;

    private Node root;     // root of the BST

    // BST helper node data type
    private class Node {
        private int key;           // key
        private Node left, right;  // links to left and right subtrees
        private boolean color;     // color of parent link
        private int size;          // subtree count

        public Node(int key, boolean color, int size) {
            this.key = key;
            this.color = color;
            this.size = size;
        }
    }

	public RedBlackBST() {

	}

   /***************************************************************************
    *  Node helper methods.
    ***************************************************************************/
    // is node x red; false if x is null ?
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    } 


    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

   /**
     * Is this symbol table empty?
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

   /***************************************************************************
    *  Red-black tree insertion.
    ***************************************************************************/

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public void put(int key) {

        root = put(root, key);
        root.color = BLACK;
        numBlack++;
        
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node h, int key) { 
        if (h == null){
            return new Node(key, RED, 1);
        }

        int cmp = key - h.key;
        if      (cmp < 0) h.left  = put(h.left,  key); 
        else if (cmp > 0) h.right = put(h.right, key); 
        else              h.key   = key;

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;

        return h;
    }


   /***************************************************************************
    *  Red-black tree helper functions.
    ***************************************************************************/

    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        assert (h != null) && isRed(h.left);
        String var = null;
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
  		//Adjust count after right rotate
        adjustRotateCount(numRed, numBlack, x, "right");
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;

        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        //Adjust count after left rotate
        adjustRotateCount(numRed, numBlack, x, "left");
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        assert (h != null) && (h.left != null) && (h.right != null);
        assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        || (isRed(h)  && !isRed(h.left) && !isRed(h.right));

        //Flip node and its children and update counts
        h.color = !h.color;
        adjustFlipCount(numRed, numBlack, h);
        h.left.color = !h.left.color;
        adjustFlipCount(numRed, numBlack, h.left);
        h.right.color = !h.right.color;
        adjustFlipCount(numRed, numBlack, h.right);
    }

    /*Calculate final percentage
    * Returns -1 if tree is empty
    */
    public static int percentRed(RedBlackBST tree){
    	if(tree.isEmpty()){
    		return -1;
    	}else{
    		int percent = (int) Math.round(tree.numRed*100/tree.size());
    		return percent;
    	}
    	
    }

    /*Helper function to adjust counts of number of red nodes and number of black
    * Nodes after a flipColors(). Runs in constant time.
    */
    private void adjustFlipCount(float numRed,float numBlack, Node node){
    	if(isRed(node)){
    		this.numBlack --;
    		this.numRed++;
    	} else{
    		this.numBlack++;
    		this.numRed--;
    	}
    }

    /**Helper function to adjust counts of number of red nodes and number of black
    *Nodes after a left or right rotation. 
    * @param direction determines if it is a left or right rotation. Runs in constant time.
    */
    private void adjustRotateCount(float numRed, float numBlack, Node node, String direction){
    	if(isRed(node)){
            this.numBlack--;
            this.numRed++;
        }
        if(!isRed(node.right) && direction.equals("right")){
            this.numRed++;
            this.numBlack--;
        }
        if(!isRed(node.left) && direction.equals("left")){
        	this.numRed++;
            this.numBlack--;
        }
    }

    /**
     * Unit tests the {@code RedBlackBST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws IOException{ 
		RedBlackBST st = new RedBlackBST();

		/**
		*	Read in file as input and deal with IOExceptions
		*	@param args[0] stores file
		*/
		if(args.length != 0){
			//System.out.println("DEBUG");
	        try{
	            File file = new File(args[0]); 
	            Scanner scan = new Scanner(file); 
	            while (scan.hasNext())
	                st.put(scan.nextInt());
	        } catch(IOException ex){
	            System.out.println (ex.toString());
	        	System.out.println("Unable to Locate File.");
	        }
	    }

	    //Number Generation
        /*for(int i = 0; i < 1000000; i++){
        	int rand = (int) Math.random() * 100;
        	st.put(i);
        }*/

        //Print percent red
        int percent = percentRed(st);
        System.out.println("Percent of Red Nodes:"+percent);
       }
        
 
}

/******************************************************************************
 *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
