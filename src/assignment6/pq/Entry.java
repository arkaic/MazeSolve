
package assignment6.pq;

import assignment6.Edge;
import assignment6.Vertex;

/**
 * Key: Best distance from the cloud
 * Value: VERTEX; EDGE (incident edge of best distance from cloud)
 * 
 * @author henry
 */
public class Entry
{
    private int keyCloudDist;
    private Vertex elemVertex;
    private Edge elemEdge;
    
    public Entry(int weight)
    {
        this(weight, null, null);
    }
    
    public Entry(int weight, Vertex elemVertex, Edge elemEdge)
    {
        this.keyCloudDist = weight;
        this.elemVertex = elemVertex;
        this.elemEdge = elemEdge;
    }
    
    /**Best cloud distance (edge weight from the cloud)*/
    public int getKey() 
    {
        return keyCloudDist;
    }
    
    /**Set key cloud distance*/
    public void setKey(int cloudDistance)
    {
        keyCloudDist = cloudDistance;
    }
    
    public Edge getEdge()
    {
        return elemEdge;
    }
    
    /**Set new edge for entry*/
    public void setEdge(Edge edge)
    {
        elemEdge = edge;
    }
    
    public Vertex getVertex()
    {
        return elemVertex;
    }
    
    @Override
    public String toString() 
    {
        String s = "[" + keyCloudDist + "]";
        return s;
    }
}
