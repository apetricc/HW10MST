/**
 * Andrew Petriccione
 * Csci 333 Fall 2015
 * Professor Whitley
 * Homework 9: Breadth First Search
 * The point of this assignment is to implement Graph class
 * that we can perform a breadth first search on.
 */
//package hw9bfs;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 45 points: Define a class named Graph to represent a graph, in a fashion
 * conducive to breadth-first searching. Private Data fields will be: "n", an
 * int for the number of vertices in the graph. "edges", a 2D boolean array of
 * size n-by-n, to store an adjacency matrix. "vertices", a length-n array of
 * Vertex, to store information about each vertex. "queue", a LinkedList<Vertex>
 * to be used only for queue operations. (The LinkedList class conveniently
 * implements the Queue Java Interface.) You'll want to make a public getter
 * method for n. You'll also want a public getter method for a Vertex, with a
 * parameter index i indicating the Vertex label to return. e.g. getVertex(3)
 * would return the Vertex handle whose label is 3. The constructor will take a
 * 2D boolean square array (that means the same length in both dimensions), as a
 * parameter. You may assume the matrix is square. The constructor should
 * construct an empty LinkedList for your queue, initialize the n data field,
 * copy the entire parameter 2D array into a brand new copy for your edges data
 * field, and init and construct your array of vertices with labels 0 through
 * n-1. Define a printGraph class method to print n, and then a nicely formatted
 * table of the adjacency matrix. Define a breadthFirstSearch class method, to
 * perform a breadth-first search. The only parameter sourceLabel is a number
 * between 0 and n-1, the label of the source vertex. You will want to create a
 * local Vertex handle s, set to element sourceLabel of your Vertex array, right
 * away. You will also want to use the Queue Java interface methods offer
 * (that's your ENQUEUE), poll (that's your DEQUEUE), and peek, on your queue.
 */

/**
 * The Graph class creates an undirected graph from an adjacency matrix
 * and has methods to get the number of vertices, retrieve a given vertex,
 * print the graph, and perform a breadth first search of the graph.
 */
public class Graph {

    private int n;
    private boolean[][] edges;
    private Vertex[] vertices;
    private Queue<Vertex> queue;

    /**
     * The constructor for a new Graph uses a queue for the vertices,
     * and a boolean 2D array for the edges.
     * @param edges the adjacency matrix of the graph
     */
    public Graph(boolean[][] edges) {
        this.n = edges.length;
        queue = new LinkedList<Vertex>();
        this.edges = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.edges[i][j] = edges[i][j];
            }
        }
        vertices = new Vertex[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new Vertex(i);
        }
    }//Graph constructor

    /**
     * The getN method returns the number of vertices, n.
     * @return the number of vertices
     */
    public int getN() {
        return n;
    }

    /**
     * getVertex returns the vertex with the key given
     * @param i the key of the vertex to return
     * @return the vertex with the key requested
     */
    public Vertex getVertex(int i) {
        return vertices[i];
    }

    /**
     * printGraph prints the number of vertices
     * then prints the boolean adjacency matrix using 1 for true
     * and 0 for false.
     */
    public void printGraph() {
        System.out.println("The graph has " + n + " vertices.");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (edges[i][j])
                    System.out.print(1 + " ");
                if (!edges[i][j])
                    System.out.print(0 + " ");
                if (j == (n - 1)) System.out.println();

            }
        }
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
     * breadthFirstSearch performs a breadth first search of the graph
     * it is called upon, updating the Vertex objects in the graph as
     * it goes.
     * @param sourceLabel the label of the vertex to use as the source of the search
     * @throws Exception in the case of a non-existent vertex, exception is thrown
     */
    public void breadthFirstSearch(int sourceLabel) throws Exception {
        Vertex s = null;
        for (int i = 0; i < n; i++) {
            getVertex(i).setColor(Vertex.WHITE);
            //getVertex(i).setDistance(Vertex.INFINITY);
            getVertex(i).setParent(null);
            if (getVertex(i).getLabel() == sourceLabel) {
                s = getVertex(i);
            }
        }
        if (s == null) {
            System.out.println("Label not a vaild key, unable to perform breadth first search...");
            return;
        }
        s.setColor(Vertex.GRAY);
        s.setDistance(0);
        s.setParent(null);
        //Queue<Vertex> Q = new LinkedList<Vertex>();
        queue.offer(s);  //enqueue or add

        while (queue.size() != 0) {
            Vertex u = queue.poll();//Q.remove();
            int label = u.getLabel();
            u.setColor(Vertex.BLACK);
            for (int i = 0; i < n; i++) {  //need use edges array to determine if adjacent first
                //for (int j = 0; j < n; j++) {
                if (edges[label][i] && i != label) {
                    if (getVertex(i).getColor() == Vertex.WHITE) {
                        getVertex(i).setColor(Vertex.GRAY);
                        getVertex(i).setDistance(u.getDistance() + 1);
                        getVertex(i).setParent(u);
                        queue.offer(getVertex(i));
                    }
                }
            }
        }

    }
}
