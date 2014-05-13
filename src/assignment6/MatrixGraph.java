
package assignment6;

import assignment6.dlist.DLinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  Graph class that represents a 2-D array of vertices. It stores a 2-D array 
 *  that points to these vertices according to their coordinates. 
 * @author henry
 */
public class MatrixGraph implements Iterable
{
    protected final int length; //length of side
    private Vertex[][] vertices; //2d array of vertices
    private DLinkedList<Edge> edges = new DLinkedList<>();
    private int maxWeight = 0; //For Cloud's primjarnik algorithm
    
    public MatrixGraph(int length)
    {
        this.length = length;
        vertices = new Vertex[length][length];
    }

    public int length()
    {
        return length;
    }
    
    public int maxWeight()
    {
        return maxWeight;
    }
    
    /**Inserts a new vertex to each and every cell of the 2D array*/
    public void buildEmptyVertices()
    {
        for (int r = 0; r < length; r++)
        {
            for (int c = 0; c < length; c++)
            {
                insertNewVertex(r, c);
            }
        }
    }
    
    public Vertex[][] vertices() 
    {
        return vertices;
    }
    
    public DLinkedList<Edge> edges()
    {
        return edges;
    }
    
    public void insertNewVertex(int row, int col)
    {
        vertices[row][col] = new Vertex(row, col);
    }
    
    public void insertVertex(Vertex vertex)
    {
        vertices[vertex.row][vertex.col] = vertex;
    }
    
    public void insertNewEdge(int weight, Vertex v1, Vertex v2, String wallDirection)
    {
        edges.addLast(new Edge(weight, v1, v2, wallDirection));
        if (weight > maxWeight)
            maxWeight = weight;
    }
    
    public void insertEdge(Edge edge)
    {
        edges.addLast(edge);
        if (edge.weight > maxWeight)
            maxWeight = edge.weight;
    }

    public Vertex opposite(Vertex v, Edge edge)
    {
        if (v == edge.vertex1)
        {
            return edge.vertex2;
        }
        else if (v == edge.vertex2)
        {
            return edge.vertex1;
        }
        else
        {
            throw new NoSuchElementException("Edge is not incident to vertex");
        }
    }

    public boolean areAdjacent(Vertex v, Vertex w)
    {
        Iterable<Edge> vEdges = v.getEdges();
        boolean toReturn = false;
        for (Edge edge : vEdges)
        {
            if (opposite(v, edge) == w)
            {
                toReturn = true;
                break;
            }
        }
        return toReturn;
    }
    
    public Vertex[] endVertices(Edge edge)
    {
        Vertex[] toReturn = new Vertex[2];
        toReturn[0] = edge.vertex1;
        toReturn[1] = edge.vertex2;
        return toReturn;
    }
    
    public void printEdgeList()
    {
        Iterable<Edge> edges = this.edges;
        for (Edge edge : edges)
        {
            System.out.println(edge.vertex1.row + " " + edge.vertex1.col + " " +
                    edge.vertex2.row + " " + edge.vertex2.col);
        }
    }
    
    @Override
    public String toString() 
    {
        String s = "";
        for (int r = 0; r < length; r++)
        {
            for (int c = 0; c < length; c++)
            {
                s += "[" + vertices[r][c] + "]";
            }
            s += "\n";
        }
        return s;
    }

    /**Will iterate over its array of vertices*/
    @Override
    public Iterator iterator()
    {
        return new VertexIterator(vertices);
    }
    
}
