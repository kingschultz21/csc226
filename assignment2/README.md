# CSC 226 Data Structures and Algorithims Assignment 2

The assignment is to implement an algorithm to determine if the minimum weight spanning tree of an edge-weighted graph, connected graph 𝐺, with no self-loops or parallel edges, is the same when using Prim’s algorithm as it is when using Kruskal’s algorithm. The edge weights in G will be real numbers greater than 0 and will not necessarily be distinct. A Java template has been provided containing an empty method PrimVsKruskal, which takes a single argument consisting of a weighted adjacency matrix for an edge-weighted graph 𝐺 with real number edge weights all greater than 0. The expected behavior of the method is as follows: 

Input: An 𝑛 × 𝑛 array 𝐺, of type double, representing an edge-weighted graph. 

Output: A boolean value which is true if the Prim’s MST equals the Kruskal’s MST and false otherwise
