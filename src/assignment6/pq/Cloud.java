
package assignment6.pq;

import assignment6.Edge;
import assignment6.MatrixGraph;
import assignment6.Vertex;
import assignment6.dlist.DNode;

/**
 * This class takes a graph as an input and applies prim-jarnik's algorithm to
 * extract out the minimum edges to create a minimum spanning tree. 
 * This tree, which is a graph, will have a 2d matrix of vertices and ONLY
 * (length^2 - 1) edges.
 * The class is named after the visual representation of the algorithm.
 * @author henry
 */
public class Cloud
{
    protected MyPriorityQueue pjpQueue; //Prim-Jarnik priority queue
    protected final MatrixGraph graph;
    protected final MatrixGraph minSpanTree; //(the resulting maze)
    protected final int infinityWeight; //Initial weight for all entries in priority queue
    protected final int startRow;
    protected final int startCol;
    
    public Cloud(MatrixGraph graph)
    {
        this(graph, 0, 0);
    }
    
    public Cloud(MatrixGraph graph, int startRow, int startCol)
    {
        pjpQueue = new MyPriorityQueue(determinePQSize(graph.length()));
        this.graph = graph;
        minSpanTree = new MatrixGraph(graph.length());
        infinityWeight = graph.maxWeight() + 1;
        this.startRow = startRow;
        this.startCol = startCol;
        fillQueue();
    }

    private int determinePQSize(int length)
    {
        int numVertices = length * length;
        int logNumVertices = (int)(Math.log(numVertices)/Math.log(2));
        return pow(2, logNumVertices + 1) - 1;
    }
    
    /**Power function for ints*/
    private int pow(int value, int power)
    {
        if (power > 0)
            return value * pow(value, power - 1);
        else 
            return 1;
    }
    
    private void fillQueue()
    {
        Iterable iterableVertices = graph;
        for (Object v : iterableVertices)
        {
            Vertex vertex = (Vertex) v;
            if ((vertex.row() == startRow) && (vertex.col() == startCol))
                pjpQueue.insert(0, vertex, null);
            else
                pjpQueue.insert(infinityWeight, vertex, null);
        }
    }
    
    /**
     * Builds and returns a minimum spanning tree based on the edge weights of
     * the graph. 
     * 
     * -At each entry when it's pulled out, all entries with vertices
     * adjacent to that entry's vertex has their distance from the cloud (entry
     * keys) updated as well as the edge with that weight. 
     * -Then, all incident edges are erased from the vertex of the entry that is 
     * pulled out, as well as from all of its adjacent vertices.
     * -Lastly, the edge that's being held by the pulled out entry is reinserted
     * back into its incident edge list, as well as for its opposite vertex whose
     * cooresponding entry should have already been pulled out into the cloud
     * previously.
     * 
     * The point of the above three steps is for the MapDisplay class to display
     *  the maze correctly, as it depends upon iterating over each vertex's incident
     *  edges to determine the walls to remove from the character display.
     */
    public MatrixGraph buildMinSpanTree()
    {
        while (!pjpQueue.isEmpty())
        {
            Entry entry = pjpQueue.removeMin();
            recalculateCloudDistances(entry); //PrimJarnik algorithm
            addEntryEdgeBackToIncidentEdges(entry);
            addToMinSpanTree(entry);
        }
        return minSpanTree;
    }
    
    /**Re-updates the keys of all entries of vertices incident to parameter entry's 
     vertex*/
    private void recalculateCloudDistances(Entry entry)
    {
        Vertex vertex = entry.getVertex();
        Iterable<Edge> incidentEdges = vertex.getEdges();
        for (Edge edge : incidentEdges)
        {
            Vertex oppositeVertex = graph.opposite(vertex, edge);
            int bestCloudDistance = oppositeVertex.getEntry().getKey();
            if (edge.weight() < bestCloudDistance)
            {
                oppositeVertex.getEntry().setEdge(edge);
                oppositeVertex.getEntry().setKey(edge.weight());
                pjpQueue.reBubble(oppositeVertex.getEntryIndex());
            }
            removeFromIncidentEdges(edge);
        }
    }
    
    /**Removes edge from incident edge list of both vertex endpoints*/
    private void removeFromIncidentEdges(Edge edge)
    {
        Vertex[] endVertices = graph.endVertices(edge);
        Vertex vertex1 = endVertices[0];
        Vertex vertex2 = endVertices[1];
        vertex1.removeEdge(edge.incidentNode1);
        vertex2.removeEdge(edge.incidentNode2);
    }
    
    /**Adds the edge that was held in the entry BACK into the incident edge lists,
     which should have been emptied out, of its respective vertex endpoints. */
    private void addEntryEdgeBackToIncidentEdges(Entry entry)
    {
        Edge edge = entry.getEdge();
        if (edge != null) //if it is not the very first entry for the cloud
        {
            Vertex entryVertex = entry.getVertex();
            Vertex oppositeVertex = graph.opposite(entryVertex, edge);
            entryVertex.addEdge(edge);
            oppositeVertex.addEdge(edge);
        }
    }
    
    /**Add in the edge from the pulled out entry into the new, minimum spanning
     tree (the maze)*/
    private void addToMinSpanTree(Entry entry)
    {
        minSpanTree.insertVertex(entry.getVertex());
        if (entry.getEdge() != null)
            minSpanTree.insertEdge(entry.getEdge());
    }
}
