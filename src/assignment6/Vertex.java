/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment6;

import assignment6.dlist.DLinkedList;
import assignment6.dlist.DNode;
import assignment6.pq.Entry;

/**
 * 
 * @author henry
 */
public class Vertex
{
    private DLinkedList<Edge> incidentEdges = new DLinkedList<>();
    protected final int row, col;
    private Entry entry;
    private int entryIndex; //...in the priority queue
    private int status = UNVISITED;
    
    public Vertex()
    {
        this(0,0);
    }
    
    public Vertex(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    
    public void setEntry(Entry entry)
    {
        this.entry = entry;
    }
    
    public void setEntryIndex(int entryIndex)
    {
        this.entryIndex = entryIndex;
    }
    
    public Entry getEntry()
    {
        return entry;
    }
    
    public int getEntryIndex()
    {
        return entryIndex;
    }
    
    public int charRow()
    {
        return row + 1;
    }
    
    public int charCol()
    {
        return (2 * col) + 1;
    }
    
    public int col()
    {
        return col;
    }
    
    public int row()
    {
        return row;
    }
    
    public void addEdge(Edge edge)
    {
        incidentEdges.addLast(edge);
    }
    
    public void addEdge(DNode<Edge> node)
    {
        incidentEdges.addLast(node);
    }
    
    public void removeEdge(DNode<Edge> node)
    {
        incidentEdges.remove(node);
    }
    
    public DLinkedList<Edge> getEdges()
    {
        return incidentEdges;
    }   
    
    @Override
    public String toString()
    {
        return "(" + row + "," + col + ")";
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
}
