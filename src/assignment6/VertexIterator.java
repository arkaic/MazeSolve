
package assignment6;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * Should accept only square 2D arrays for constructor.
 * This is an iterator for a 2D array of vertices.
 * @author henry
 */
public class VertexIterator implements Iterator
{
    private final Vertex[][] vertices;
    private final int numRows;
    private final int numCols;
    private int cursorRow;
    private int cursorCol;

    public VertexIterator(Vertex[][] vertices)
    {
        this.vertices = vertices;
        numRows = vertices.length;
        numCols = vertices[0].length;
        cursorRow = 0;
        cursorCol = 0;
    }
    
    /** 
     * If the cursor is a valid index, returns true, otherwise false. Cursor 
     * will never be pointing at a valid position AND say there is no 
     * next*/
    @Override
    public boolean hasNext()
    {
        boolean bool = (hasNextColumn() || hasNextRow());
        return bool;
    }
    
    @Override
    public Vertex next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException("No next element");
        }
        else
        {
            Vertex toReturn = vertices[cursorRow][cursorCol];
            advanceCursor();
            return toReturn;
        }
    }
    
    private void advanceCursor()
    {
        cursorCol++;
        if (!hasNextColumn())
        {
            cursorRow++;
            if (hasNextRow()) 
                cursorCol = 0;
        }
    }
    
    private boolean hasNextColumn()
    {
        return (cursorCol  < numCols);
    }

    private boolean hasNextRow()
    {        
        return (cursorRow < numRows);
    }    

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
