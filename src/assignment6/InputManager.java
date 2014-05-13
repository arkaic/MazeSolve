
package assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * 
 * @author henry
 */
public class InputManager
{  
    private Scanner scanReader;       //common scanner for reading strings
    private Scanner scanIn;           //common scanner for input
    
    public String inputInitialSpecsString()
    {
        scanIn = new Scanner(System.in);
        String string = "";
        while (true)
        {
            string = scanIn.nextLine();
            if (isInitialLineValid(string))
                break;
        }
        return string;
    }
    
    public void inputVertexEndpointsToGraph(MatrixGraph graph) throws IOException
    {
        System.out.println("Copy and paste the entire set of edge connections"
                + "then press enter twice:");
        String edgeConnections = inputVertexEndpoints();
        parseAndApplyVertexEndpoints(graph, edgeConnections);
    }
    
    private String inputVertexEndpoints() throws IOException
    {
        String edgeConnections = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while (((line = br.readLine()) != null) && line.length() != 0) 
        {
            edgeConnections += line + "\n";
        }
        return edgeConnections;
    }
    
    private boolean isInitialLineValid(String string)
    {
        if (isEmptyString(string))
        {
            System.out.println("Empty string, try again");
            return false;
        }
        else if (!isCorrectLength(string, 5))
        {
            System.out.println("Incorrect length, try again");
            return false;
        }
        else if (!hasAllIntegers(string, 5))
        {
            System.out.println("Has non integers, try again");
            return false;
        }
        else if (!hasValidDimension(string))
        {
            System.out.println("Invalid dimension, try again");
            return false;
        }
        else if (!hasValidStartCoords(string))
        {
            System.out.println("Invalid start coordinates, try again");
            return false;
        }
        else if (!hasValidEndCoords(string))
        {
            System.out.println("Invalid end coordinates, try again");
            return false;
        }
        return true;
    }
    
    private boolean isEmptyString(String string)
    {
        scanReader = new Scanner(string);
        if (!scanReader.hasNext())
            return true;
        else
            return false;
    }
    
    /**Assumes that string is not empty*/
    private boolean hasAllIntegers(String string, int length)
    {
        scanReader = new Scanner(string);
        for (int i = 1; i <= length; i++)
        {
            if (scanReader.hasNextInt() == false)
                return false;
            else
                scanReader.nextInt();
        }
        return true;
    }
    
    /**
     * Checks if the first number, the length of the matrix, is a valid one.
     * Assumes string is not empty and is made up of all integers.
     */
    private boolean hasValidDimension(String string)
    {
        scanReader = new Scanner(string);
        if (scanReader.nextInt() > 1)
            return true;
        else
            return false;
    }
    
    private boolean isCorrectLength(String string, int length)
    {
        scanReader = new Scanner(string);
        for (int i = 1; i <= length; i++) 
        {
            scanReader.next();
            if ((i < length) && (scanReader.hasNext() == false))
                return false;
            if ((i == length) && (scanReader.hasNext() == true))
                return false;
        }
        return true;
    }
    
    private boolean hasValidStartCoords(String string)
    {
        scanReader = new Scanner(string);
        int minEnd = 0;
        int maxEnd = scanReader.nextInt() - 1;
        int row    = scanReader.nextInt();
        int col    = scanReader.nextInt();
        if (isWithinDimensions(row, col, minEnd, maxEnd))
        {
            if (isExternal(row, col, minEnd, maxEnd))
                return true;
            else
                return false;
        }
        else
        {
            return false;
        }
    }
    
    private boolean hasValidEndCoords(String string)
    {
        scanReader = new Scanner(string);
        int minEnd = 0;
        int maxEnd = scanReader.nextInt() - 1;
        scanReader.nextInt();
        scanReader.nextInt();
        int row    = scanReader.nextInt();
        int col    = scanReader.nextInt();
        if (isWithinDimensions(row, col, minEnd, maxEnd))
        {
            if (isExternal(row, col, minEnd, maxEnd))
                return true;
            else
                return false;
        }
        else
        {
            return false;
        }
    }
    
    /**Are coordinates within the dimensions of the graph*/
    private boolean isWithinDimensions(int row, int col, int minEnd, int maxEnd)
    {
        if ((row >= minEnd) && (row <= maxEnd) && (col >= minEnd) && (col <=maxEnd))
            return true;
        else
            return false;
    }
    
    /**
     * Are coordinates external in the graph. Assumes coordinates are within
     * dimensions.
     */
    private boolean isExternal(int row, int col, int minEnd, int maxEnd)
    {
        if ((row != minEnd) && (row != maxEnd)) 
        {
            //column needs to be either ends
            if ((col == minEnd) || (col == maxEnd))
                return true;
            else
                return false;
        } 
        else 
        {
            return true;
        }
    }
    
    private void parseAndApplyVertexEndpoints(MatrixGraph graph, 
            String edgeConnections)
    {
        Scanner sc = new Scanner(edgeConnections);
        while(sc.hasNextLine())
        {
            parseAndInsertWall(graph, sc.nextLine());
        }
    }
    
    /**
     * Assumes first two coordinates coorespond to top/left vertex field in edge
     */
    private void parseAndInsertWall(MatrixGraph graph, String line)
    {
        Scanner sc = new Scanner(line);
        Vertex[][] vertices = graph.vertices();
        int row1 = sc.nextInt();
        int col1 = sc.nextInt();
        int row2 = sc.nextInt();
        int col2 = sc.nextInt();
        String direction;
        if (row1 == row2)
            direction = "vertical";
        else
            direction = "horizontal";
        int weight = 0;
        graph.insertNewEdge(weight, vertices[row1][col1], vertices[row2][col2], direction);
    }
}
