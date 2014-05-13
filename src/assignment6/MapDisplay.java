
package assignment6;

import assignment6.dlist.DLinkedList;

/**
 * Takes in a graph as input and constructs a character map that represents
 * the maze.
 * @author henry
 */
public class MapDisplay
{
    protected final MatrixGraph graph;
    protected final int startRow;
    protected final int startCol;
    protected final int endRow;
    protected final int endCol;
    protected char[][] charMap;
    
    public MapDisplay(MatrixGraph graph, int[] startEndCoords)
    {
        this.graph = graph;
//        charMap    = new char[graph.length + 1][(2 * graph.length) + 1];
        charMap    = new char[graph.length + 2][(2 * graph.length) + 1];
        startRow   = startEndCoords[0];
        startCol   = startEndCoords[1];
        endRow     = startEndCoords[2];
        endCol     = startEndCoords[3];
    }
    
    public void displayFullMap()
    {
        buildFullMap();
        System.out.println(toMapString());
    }
    
    public void displayMinSpanMap()
    {
        buildFullMap();
        buildMinSpanMap();
        System.out.println("\nMinimum spanning map");
        System.out.println(toMapString());
    }
    
    public void displayDFSMap(DLinkedList<Vertex> vertices)
    {
        buildFullMap();
        buildMinSpanMap();
        displayMinSpanMap();
        int counter = 0; //to identify first vertex
        for (Vertex vertex : vertices)
        {
            counter++;
            if ((vertex.charRow() == charMap.length-2) && (counter != 1))
            {
                charMap[vertex.charRow() + 1][vertex.charCol()] = '‾';
            }
            charMap[vertex.charRow()][vertex.charCol()] = '*';
        }
        System.out.println("\nBest path from start to end");
        System.out.println(toMapString());
    }  
    
    private String toMapString()
    {
        String map = "";
        for (int i = 0; i < charMap.length; i++)
            map += new String(charMap[i]) + "\n";
        return map;       
    }
    
    /**Builds map based on the edge list in the graph field.
     * Assumes there is the required amount of edges in the list to make the 
     * full map with respect to the stated side length.
     */
    private void buildMinSpanMap()
    {
        Iterable<Edge> edges = graph.edges();
        for (Edge edge : edges)
        {
            switch (edge.direction) {
                case "vertical":
                    charMap[edge.charRow()][edge.charCol()] = ' ';
                    break;
                case "horizontal":
                    charMap[edge.charRow()][edge.charCol()] = ' ';
                    break;
            }
        }
    }
    
    private void buildFullMap()
    {
        constructTopAndBottomExternalWalls();
        constructLeftAndRightExternalWalls();
        fillSkeletonMapWithWalls();
        removeStartWall();
        removeEndWall();
    }
    
    private void constructTopAndBottomExternalWalls()
    {
        int numRows = charMap.length-1;
        int numCols = charMap[0].length;
        for (int j = 1; j < (numCols - 1); j++)
        {
            if ((j % 2) == 0)
            {
                charMap[0][j] = ' ';
                charMap[numRows - 1][j] = ' ';
            }
            else
            {
                charMap[0][j] = '_';
                charMap[numRows - 1][j] = '_';
            }
        }     
        for (int j = 0; j < (numCols); j++)
        {
            charMap[numRows][j] = ' ';
        }
    }
    
    private void constructLeftAndRightExternalWalls()
    {
        int numRows = charMap.length - 1;
        int numCols = charMap[0].length;
        for (int i = 0; i < numRows; i++)
        {
            if (i == 0)
            {
                charMap[i][0] = ' ';
                charMap[i][numCols - 1] = ' ';
            }
            else
            {
                charMap[i][0] = '|';
                charMap[i][numCols - 1] = '|';
            }
        }  
    }
    
    private void fillSkeletonMapWithWalls()
    {
        int numRows = charMap.length - 1;
        int numCols = charMap[0].length;
        for (int i = 1; i < (numRows); i++)
        {
            for (int j = 1; j < (numCols - 1); j++)
            {
                if ((j % 2) == 0)
                    charMap[i][j] = '|';
                else
                    charMap[i][j] = '_';
            }
        }
    }
    
    private void removeStartWall() 
    {
        if (startCol == 0)
        {
            charMap[startRow+1][0] = ' ';
        }
        else if (startCol == (graph.length-1))
        {
            charMap[startRow+1][charMap[0].length-1] = ' ';
        }
        else
        {
            if (startRow == 0)
                charMap[0][(startCol*2)+1] = ' ';
            else
                charMap[charMap.length-1][(startCol*2)+1] = ' ';
        }           
    }
    
    private void removeEndWall()
            
    {
        if (endCol == 0)
        {
            charMap[endRow+1][0] = ' ';
        }
        else if (endCol == (graph.length-1))
        {
            charMap[endRow+1][charMap[0].length-1] = ' ';
        }
        else
        {
            if (endRow == 0)
                charMap[0][(endCol*2) + 1] = ' ';
            else
                charMap[charMap.length-1][(endCol*2) + 1] = ' ';
        }          
    }    
}

