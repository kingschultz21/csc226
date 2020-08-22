# Various Algorithms and Data-Structures from CSC 226
    note: These algorithms were created as a study tool and have not been thouroughly tested
Algorithms and Data Structures modified and borrowed from Robert Sedgewick and Robert Wayne's algs4 java package.
## Minimum Spanning Tree Algorithms:
### 1. Prims MST Algorithm:
    PrimsMST.java
Returns a Minimum Spanning tree created using the Prims Algorithm and an input weighted adjaceny matrix <br />
Assumes undirected, non-negative integer weights. <br />
![Alt Text](https://upload.wikimedia.org/wikipedia/commons/9/9b/PrimAlgDemo.gif) <br />
###### gif from Prims's Algorithm page on Wikipedia
### 2. Kruskals MST Algorithm:
    KruskalsMST.java
Returns a Minimum Spanning tree created using Kruskals Algorithm and an input weighted adjaceny matrix <br />
Assumes undirected, non-negative integer weights. <br />
![Alt Text](https://upload.wikimedia.org/wikipedia/commons/b/bb/KruskalDemo.gif) <br />
###### gif from Kruskal's Algorithm page on Wikipedia
### 3. Boruvkas MST Algorithm:
    BoruvkasMST.java
Returns a Minimum Spanning tree created using Boruvkas Algorithm and an input weighted adjaceny matrix <br />
Assumes undirected, non-negative integer weights. <br />
![Alt Text](https://upload.wikimedia.org/wikipedia/commons/7/74/Boruvka%27s-algorithm-example.gif) <br />
###### gif from Boruvka's Algorithm page on Wikipedia
## Shortest Path Algorithms:
### 1. Dijkstras Shortest Path Algorithm:
    DijskstrasSP.java
Returns the Shortest-Paths tree from a source vertex to every other vertex in the graph using Dijkstras Algorithm, if such a path exists. <br />
Assumes undirected, non-negative integer weights. <br />
![Alt Text](https://upload.wikimedia.org/wikipedia/commons/5/57/Dijkstra_Animation.gif) <br />
###### gif from Dijkstra's Algorithm page on Wikipedia
### 2. Bellman-Fords Shortest Path Algorithm:
    BellmanFordsSP.java
Returns the Shortest-Paths tree from a source vertex to every other vertex in the graph using Bellman-Fords Algorithm, if such a path exists. <br />
Assumes undirected, non-negative integer weights. <br />
![Alt Text](https://upload.wikimedia.org/wikipedia/commons/thumb/2/2e/Shortest_path_Dijkstra_vs_BellmanFord.gif/560px-Shortest_path_Dijkstra_vs_BellmanFord.gif) <br />
###### gif from Wikipedia Commons: Dijkstra (top) vs. Bellman-Ford (Bottom)
## Network Flow Algorithms:

##
## Compiling and Running:
### Running Minimum Spanning Tree Algorithm Test File:
    javac -cp .:algs4.jar MSTAlgorithmTesting.java
    java -cp .:algs4.jar MSTAlgorithmTesting ./../test_files/ewg_<vertex number>vertices_<file number>.txt
### Running Shortest Path Algorithm Test File:
    javac -cp .:algs4.jar SPAlgorithmTesting.java
    java -cp .:algs4.jar SPAlgorithmTesting ./../test_files/ewg_<vertex number>vertices_<file number>.txt
   
