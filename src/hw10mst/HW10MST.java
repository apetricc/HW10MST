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

/**
 * Test your Graph class by performing Prim's MST algorithm on a Graph. Manually
 * initialize at least one 2D Edge array of the weighted adjacency matrix of a
 * fully connected weighted graph with at least 5 vertices. Construct a Graph
 * object, and perform Prim's MST algorithm. Afterward, call printGraph and also
 * print out all the vertices.
 */
public class HW10MST {

    public static void main(String[] args) {

        Edge[][] myEdges = {
            {new Edge(), new Edge(3), new Edge(7), new Edge(), new Edge(8)},
            {new Edge(3), new Edge(), new Edge(5), new Edge(), new Edge()},
            {new Edge(7), new Edge(5), new Edge(), new Edge(9), new Edge(2)},
            {new Edge(), new Edge(), new Edge(9), new Edge(), new Edge()},
            {new Edge(8), new Edge(), new Edge(2), new Edge(), new Edge()}
        };
        Graph myGraph = new Graph(myEdges);
        myGraph.printGraph();
        System.out.println();
        myGraph.printVertices();
        System.out.println();
        myGraph.primMST();
        System.out.println("Here are the edges: ");
        for (int i = 0; i < myEdges.length; i++) {
            for (int j = 0; j < myEdges.length; j++) {
                System.out.print(myEdges[i][j].toString() + "\t");
            }
            System.out.println();
        }

    }
}
