/**
 * Andrew Petriccione
 * CSCI 333 Fall 2015
 * Professor Whitley
 * Homework 10: Prim's Minimum Spanning Tree Algorithm
 * The point of this assignment is to create a graph
 * from which we can create a minimum spanning tree
 * using Prim's algorithm.
 */
package hw10mst;

import java.util.PriorityQueue;

/**
 * The Graph class has private data fields for the number of vertices called n,
 * an array of Vertices objects called vertices, and a 2D array of Edges called
 * edges. There are methods to print the graph, create a minimum spanning tree
 * using Prim's algorithm, print the vertices in the graph, and some helper
 * methods for these methods.
 */
public class Graph {

    private int n;

    private Vertex[] vertices;
    private Edge[][] edges;

    /**
     * The constructor takes a square adjacency matrix parameter, as a 2D Edge
     * array. It assumes the matrix is square. The constructor initializes the n
     * data field, assigns the parameter Edge array to the edges data field,
     * initializes and constructs the vertices data field of Vertex objects,
     * with labels 0 through n-1.
     *
     * @param edges
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
     printGraph prints n, and then a nicely formatted table 
     of the adjacency matrix's edges.
     */
    public void printGraph() {
        System.out.println("N is: " + n);
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {
                System.out.print(edges[i][j] + "\t");
            }
            System.out.println();
        }

    }

    /**
     * primMST creates a Minimum Spanning Tree using Prim's algorithm printing
     * the vertices at the end.
     */
    public void primMST() {
//r is the source   
        Vertex r;
        PriorityQueue<Vertex> Q = new PriorityQueue<>();
        for (Vertex vertex : vertices) {
            vertex.setKey(Edge.INFINITE_WEIGHT);
            vertex.setParent(null);
            Q.add(vertex);
        }
        vertices[0].setKey(0);
        while (!Q.isEmpty()) {
            r = Q.poll();
            for (int i = 0; i < n; i++) {
                if (edges[r.getLabel()][i].getExists()) {
                    if (isStillInQ(Q, vertices[i].getLabel()) && edges[r.getLabel()][i].getWeight() < vertices[i].getKey()) {
                        vertices[i].setParent(r);
                        decreaseKey(Q, vertices[i].getLabel(), edges[r.getLabel()][i].getWeight());
                    }
                }
            }
        }
        printVertices();
        System.out.println();
    }

    /**
     * printVertices prints all the Vertex objects and their contents from the
     * graph it is called upon.
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

    /**
     * getN returns the number of vertices in the Graph, n.
     *
     * @return the number of vertices
     */
    public int getN() {
        return n;
    }

    /**
     * getVertex returns the vertex with the given key
     *
     * @param i the key of the Vertex object to return
     * @return the Vertex with the given key
     */
    public Vertex getVertex(int i) {
        return vertices[i];
    }

    /**
     * getEdge returns the Edge object between two vertices
     *
     * @param u the first vertex
     * @param v the second vertex
     * @return the Edge object between the first and second vertex, if there is
     * no edge between the two vertices that information will still be contained
     * in an Edge object at the given point in the edges array.
     */
    public Edge getEdge(int u, int v) {
        return edges[u][v];
    }
}
