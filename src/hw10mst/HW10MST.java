/*
 * Header comment goes here.
 */
package hw10mst;


/*
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
public class HW10MST {

    public static void main(String[] args) {

        Edge[][] myEdges = {
            {new Edge(), new Edge(3), new Edge(7),new Edge(), new Edge(8)},
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
