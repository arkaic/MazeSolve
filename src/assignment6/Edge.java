
package assignment6;

import assignment6.dlist.DNode;

/**
 * Holds reference to vertices on both ends.
 * Vertex1 refers to top vertex (if wall is horizontal) or left vertex (if wall
 * is vertical).
 * 
 * Horizontal walls should have the same char[] coordinates as the cell 
 * (vertex), while vertical walls will be the cell's coordinates + 1.
 * @author henry
 */
public class Edge
{
    protected String direction; //vertical:l/R   horizontal:U/D
    protected int weight; 
    protected final Vertex vertex1;
    protected final Vertex vertex2;
    public final DNode<Edge> incidentNode1; //incident edge node for vertex 1
    public final DNode<Edge> incidentNode2; //incident edge node for vertex 2
    private int status = UNVISITED;
    
    public Edge(int weight, Vertex vertex1, Vertex vertex2, String wallDirection)
    {
        this.weight = weight;
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.direction = wallDirection;
        DNode<Edge> node1 = new DNode<>(this);
        DNode<Edge> node2 = new DNode<>(this);
        vertex1.addEdge(node1);
        vertex2.addEdge(node2);
        incidentNode1 = node1;
        incidentNode2 = node2;
    }
    
    public int charRow()
    {
        if (direction.equals("vertical") || direction.equals("horizontal"))
            return vertex1.charRow();
        else
            return 0;
    }
    
    public int charCol()
    {
        switch (direction) 
        {
            case "horizontal":
                return vertex1.charCol();
            case "vertical":
                return (vertex1.charCol() + 1);
        }
        return 0;
    }
    
    public String toString()
    {
        return "" + weight;
    }
    
    public int weight() 
    {
        return weight;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int i)
    {
        status = i;
    }
    
    public static final int UNVISITED = 0;
    public static final int VISITED   = 1;
    public static final int BACK      = 2;
}
