
package assignment6;

import assignment6.dlist.DLinkedList;
import assignment6.pq.*;
import java.io.IOException;
import java.util.Scanner;
/**
 * -For a randomized set of edge weights at ANY graph length (n), go to the 
 * InputManager class for details.
 * @author henry
 */
public class MazeSolve
{
    public static final InputManager INPUT_MANAGER = new InputManager();
    
    public static void main(String[] args) throws IOException
    {
        int[] initialSpecs   = inputInitialSpecs();
        int mazeLength       = initialSpecs[0];
        int[] startEndCoords = {initialSpecs[1], initialSpecs[2], initialSpecs[3], 
            initialSpecs[4]};
        
        MatrixGraph maze = constructMazeWithVertexEndpoints(mazeLength);
        
        System.out.println("Minimum spanning tree edges");
        maze.printEdgeList();
        MapDisplay mapDisplay = new MapDisplay(maze, startEndCoords);
        
        //The below code will solve the maze through the DFS algorithm, but I 
        //will also provide a separate zip file of classes for the MazeSolve portion
        //of the assignment as well. 
        solveMaze(maze, startEndCoords, mapDisplay);
    }
    
    /**
     * Input for initial specifications of the maze: 
     * length, start coordinates, end coordinates.
     * Returns an int array of these numbers.
     */
    public static int[] inputInitialSpecs()
    {
        int[] returnArray = new int[5];
        System.out.println("Input first line 'n r1 c1 r2 c2'");
        String initialLine = INPUT_MANAGER.inputInitialSpecsString();
        Scanner readout = new Scanner(initialLine);
        for (int i = 0; i < 5; i++)
            returnArray[i] = readout.nextInt();
        return returnArray;
    }
    
    /**Solves the maze by using DepthFirstSearch class, which utilizes the... 
     * depth first search algorithm to find a possible path from start point to
     * end point. Then utilizes an algorithm created by me to isolate out the best
     * path to the endpoint without the forks to deadends.
     * 
     * DepthFirstSearch.finalResult() returns a doubly linked list of vertices in
     * the order from start to finish with no forking paths or dead ends.
     * @param maze
     * @param startEndCoords
     * @param mapDisplay 
     */
    public static void solveMaze(MatrixGraph maze, int[] startEndCoords, 
            MapDisplay mapDisplay)
    {
        DepthFirstSearch dfs = new DepthFirstSearch();
        System.out.println("");
        DLinkedList<Vertex> verticesOfBestPath = dfs.finalResult(maze, startEndCoords);
        mapDisplay.displayDFSMap(verticesOfBestPath);
    }
    
    //This is for the MazeSolve portion of the assignment where the inputs are in
    //the form of vertex endpoints
    public static MatrixGraph constructMazeWithVertexEndpoints(int length) 
            throws IOException
    {
        MatrixGraph maze = new MatrixGraph(length);
        maze.buildEmptyVertices();
        INPUT_MANAGER.inputVertexEndpointsToGraph(maze);
        return maze;
    }
}
