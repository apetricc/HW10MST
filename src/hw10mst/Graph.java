/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw10mst;

import java.util.PriorityQueue;

/**
 *
 * @author awhitley
 */

/*
Your assignment is the following:

45 points: Define a class named Graph to represent a graph, in a fashion 
conducive to Prim's MST algorithm.
Private Data fields will be: n, an int for the number of vertices in the graph.
edges, a 2D array of Edge with size n-by-n, to store an adjacency matrix of Edge 
objects.  vertices, a length-n array of Vertex, to store information about each 
vertex.
You'll want to make a public getter method for n.  

You'll also want a public 
getter method for a Vertex, with a parameter index i indicating the Vertex label
to return.  e.g. getVertex(3) would return the Vertex handle whose label is 3. 
Also, make a getter method for an Edge, with two index parameters.

********
The constructor will take a square adjacency matrix parameter, as a 2D Edge 
array. You may assume the matrix is square. The constructor should initialize 
the n data field, assign the parameter Edge array to your edges data field, 
and init and construct your vertices data field of Vertex objects, with 
labels 0 through n-1.
**
***
Define a printGraph class method to print n, and then a nicely formatted table 
of the adjacency matrix's edges. The Edge class has a convenient toString 
method allowing you to insert an Edge object directly into a print statement.
**
Define a primMST class method with no parameters, to perform Prim's MST 
algorithm and calculate a min spanning tree. It will set the p data fields 
of your vertices to indicate the tree structure. Use your edges and vertices 
data fields. Use Vertex 0 as the source. You will want to create a local 
PriorityQueue<Vertex>.
15 points: In main, test your Graph class by performing Prim's MST algorithm 
on a Graph.
Manually initialize at least one 2D Edge array of the weighted adjacency 
matrix of a fully connected weighted graph with at least 5 vertices. 
You may want to draw the graph on paper first, then write down the weighted 
adjacency matrix, then write the code to initialize it.
Construct a Graph object, and perform Prim's MST algorithm. Afterward, 
you'll want to printGraph and also print out all the vertices.  
(By reading all the parent labels in the Vertex print statements, you could 
draw the breadth-first tree on paper to make sure it's working correctly.)
*/
public class Graph {
    private int n;
    
    private Vertex[] vertices; 
    private Edge[][] edges;
    // ----- Data Fields -----
    // DO NOT rename me without Refactor --> Rename, or you'll break decreaseKey.
    // You need to declare the other data fields, too!
    // You need to define all the constructor(s) and other class methods, too!
    // ----- Private Helper Methods -----
/*
    The constructor will take a square adjacency matrix parameter, as a 2D Edge 
array. You may assume the matrix is square. The constructor should initialize 
the n data field, assign the parameter Edge array to your edges data field, 
and init and construct your vertices data field of Vertex objects, with 
labels 0 through n-1.

    */
    public Graph(Edge[][] edges) {
        this.edges = edges;
        this.n = edges.length;
        this.vertices = new Vertex[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new Vertex(i, Edge.INFINITE_WEIGHT);
        }
    }
    
    /*
    Define a printGraph class method to print n, and then a nicely formatted table 
of the adjacency matrix's edges. The Edge class has a convenient toString 
method allowing you to insert an Edge object directly into a print statement.

    */
public void printGraph() {
    System.out.println("N is: "+n);
    for (int i = 0; i < n; i++) {
        
        for (int j = 0; j < n; j++) {
            System.out.print(edges[i][j] + "\t");            
        }
        System.out.println();
    }
    
}    
  /*
Define a primMST class method with no parameters, to perform Prim's MST 
algorithm and calculate a min spanning tree. It will set the p data fields 
of your vertices to indicate the tree structure. Use your edges and vertices 
data fields. Use Vertex 0 as the source. You will want to create a local 
PriorityQueue<Vertex>.
*/ 
/*
PRIM-MINIMUM-SPANNING-TREE(G, w, r) // graph G, weight function w, source vertex r
for each vertex u in G // init null parents and infinite keys
   u.key = INFINITY
   u.p = NULL
r.key = 0
Let Q be a new priority queue containing all vertices V of G
while Q is not empty
   u = EXTRACT-MIN(Q)
   for each vertex v adjacent to u
      if v is still stored in Q and w(u, v) < v.key // we can get to it cheaper
         v.p = u // (u, v) is cheapest edge known so far to connect v to the MST
         DECREASE-KEY(Q, v, w(u,v)) // key of v decreased to edge weight (u, v)

*/
public void primMST() {
//r is the source   
    Vertex u;
    PriorityQueue<Vertex> Q = new PriorityQueue<>();
    for(Vertex vertex : vertices) {
        vertex.setKey(Edge.INFINITE_WEIGHT);
        vertex.setParent(null);
        Q.add(vertex);
    }
    vertices[0].setKey(0);
    while(!Q.isEmpty()) {
        u = Q.poll();
        for (int i = 0; i < n;i++) {
            if(edges[u.getLabel()][i].getExists()) {
                if(isStillInQ(Q,vertices[i].getLabel()) && edges[u.getLabel()] [i].getWeight() < vertices[i].getKey()) {
                    vertices[i].setParent(u);
                    decreaseKey(Q,vertices[i].getLabel(),edges[u.getLabel()][i].getWeight());
                }
            }
        }
    }
    printVertices();
    System.out.println();
}
 

/**
     * printVertices prints all the Vertex objects and their contents
     * in the graph it is called upon.
     */
    public void printVertices() {
        for (int i = 0; i < n; i++) {
            System.out.println("Here is vertex " + i + " " + getVertex(i));
        }
    }
    
    
    
    /**
     * Tells you whether a Vertex with the provided label is still in the queue
     * q. This is needed because, since the PriorityQueue is keyed on key not
     * label, it will tell you whether a given key is still in it, but not
     * whether a given label is still in it.
     *
     * @param q a PriorityQueue<Vertex>
     * @param label The Vertex label to check for.
     * @return Returns true if a Vertex with matching label is in q. Returns
     * false if no Vertex with matching label is in q.
     */
    private boolean isStillInQ(PriorityQueue<Vertex> q, int label) {

        Vertex[] array = q.toArray(new Vertex[0]); // dump out an array of the elements

        // traverse the array of elements, searching for a matching label
        for (int i = 0; i < array.length; i++) {
            if ((array[i]).getLabel() == label) {
                return true;
            }
        }

        return false; // no matching label found

    }

    /**
     * Takes the Vertex with matching label in queue q, and reduces its key to
     * newKey. Will return false if Vertex is not in the queue, or newKey is
     * larger than old key. Will return true if it successfully reduced the key.
     *
     * @param q The priority queue of Vertex
     * @param label The label of the Vertex whose key you want to decrease
     * @param newKey
     * @return Returns false if the Vertex with the given label is not in the
     * queue. Returns false if the newKey is larger than the old key of Vertex
     * with given label. Returns true otherwise; the vertex with the given label
     * had its key changed to newKey.
     */
    private boolean decreaseKey(PriorityQueue<Vertex> q, int label, int newKey) {
        // PAY NO ATTENTION TO THE CODE BEHIND THAT CURTAIN! ;)
        // Don't worry about the code in this method body. Read the Javadoc above.

        int indexOfVertex = -1;
        Vertex[] array = q.toArray(new Vertex[0]);

        // check to see if Vertex with the given label is in the Priority queue.
        for (int i = 0; i < array.length; i++) {
            if ((array[i]).getLabel() == label) {
                indexOfVertex = i;
            }
        }

        // if Vertex with the given label is not in the queue, do nothing and return false
        // also returns false if the new key is larger than the old key.
        if (indexOfVertex == -1 || newKey > array[indexOfVertex].getKey()) {
            return false;
        }

        // Without decreaseKey already in the PriorityQueue class,
        // I must remove the vertex and add it again with a different key. 
        // Actually, I'm emptying the queue, then I am inserting all the other
        // vertices back in, except the one being decreased. Then I am reinserting
        // the decreased vertex, with the newKey key value.
        // I had to resort to this because technically you can't remove 
        // an element from a PriorityQueue by its label, since it is keyed on something else.
        Vertex vertexToDecrease = array[indexOfVertex];
        vertexToDecrease.setKey(newKey);

        // clear and rebuild the priority queue
        q.clear();
        for (int i = 0; i < array.length; i++) { // add everything else
            if (i != indexOfVertex) { // not including the old vertex to be reduced
                q.add(array[i]);
            }
        }
        q.add(vertexToDecrease); // insert the decreased vertex back in

        return true; // queue is now effectively identical to before, but with one Vertex's key reduced to newKey
    }//decreaseKey method
    
    public int getN() {
        return n;
    }
    public Vertex getVertex(int i) {
        return vertices[i];
    }
    public Edge getEdge(int u, int v) {
        return edges[u][v]; 
    }
}
