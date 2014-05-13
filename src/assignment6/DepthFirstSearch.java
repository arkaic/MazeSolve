
package assignment6;

import assignment6.dlist.*;


/**
 *
 * @author henrymm
 */
public class DepthFirstSearch
{
    
    /**
     * Creates a list of traversal vertices from start to finish. Then creates
     * a best traversal list that removes all dead end vertices.
     * @param maze
     * @param startEndCoords
     * @return 
     */
    public DLinkedList<Vertex> finalResult(MatrixGraph maze, int[] startEndCoords)
    {
        Vertex start = maze.vertices()[startEndCoords[0]][startEndCoords[1]];
        DLinkedList<Vertex> traversalList = new DLinkedList<>();
        dfsAlgorithm(maze, start, traversalList);
        displayTraversalList(traversalList, startEndCoords);
        System.out.println("");
        
        DLinkedList<Vertex> bestTraversalList = displayBestTraversal(traversalList, 
                startEndCoords, maze);
        return bestTraversalList;
    }
    
    private void dfsAlgorithm(MatrixGraph maze, Vertex vertex,
            DLinkedList<Vertex> traversalList)
    {
        vertex.setStatus(VISITED);
        traversalList.addLast(vertex);
        Iterable<Edge> edges = vertex.getEdges();
        for (Edge edge : edges)
        {
            if (edge.getStatus() == UNVISITED)
            {
                Vertex oppositeVertex = maze.opposite(vertex, edge);
                if (oppositeVertex.getStatus() == UNVISITED)
                {
                    edge.setStatus(VISITED);
                    dfsAlgorithm(maze, oppositeVertex, traversalList);
                }
                else
                {
                    edge.setStatus(BACK);
                }
            }
        }
    }
    
    /**Traversal list of vertices to the end coordinates, including deadends*/
    private void displayTraversalList(DLinkedList<Vertex> traversalList,
            int[] startEndCoords)
    {
        Iterable<Vertex> vertices = traversalList;
        System.out.println("All traversal vertices, including dead ends, from "
                + "start to finish");
        for (Vertex vertex : vertices)
        {
            System.out.println(vertex);
            if (isEndVertex(vertex, startEndCoords))
                break;
        }        
    }
    
    /**
     * Best traversal list of vertices. A reverse list of all traversal vertices
     * is created for purposes of iteration. An empty stack is also created, 
     * simulated by the DLinkedList. 
     * -For each vertex from the reverse list, which starts with the vertex that's
     * at the endpoint, they are to be added to the stack, BUT ONLY IN THE 
     * FOLLOWING CONDITIONS
     *   -If it's the first vertex to be added into the stack, just stick it in.
     *   -Otherwise, check to see if the vertex on top of the stack is incident to
     *    the one waiting to be pushed in. If it is, push. If not, do nothing.
     * -In the end, the resulting stack will have all vertices that go from 
     *  start to finish in a single line with no forking or dead ends.
     */
    private DLinkedList<Vertex> displayBestTraversal(DLinkedList<Vertex> traversalList,
            int[] startEndCoords, MatrixGraph maze)
    {
        Iterable<Vertex> vertices = traversalList;
        DLinkedList<Vertex> reverseList = new DLinkedList<>();
        for (Vertex vertex : vertices)
        {
            reverseList.addFirst(vertex);
            if (isEndVertex(vertex, startEndCoords))
                break;
        }
        DLinkedList<Vertex> stack = new DLinkedList<>();
        for (Vertex vertex : reverseList)
        {
            if (stack.size() > 0)
            {
                Iterable<DNode<Vertex>> stackNodes = stack.nodes();
                boolean isIncidentToVerticesInStack = false;
                for (DNode<Vertex> stackNode : stackNodes)
                {
                    Vertex stackVertex = stackNode.getElement();
                    if (maze.areAdjacent(vertex, stackVertex))
                    {
                        isIncidentToVerticesInStack = true;
                        break;
                    }
                }
                if (isIncidentToVerticesInStack)
                {
//                    for (DNode<Vertex> stackNode : stackNodes)
//                    {
//                        Vertex stackVertex = stackNode.getElement();
//                        if (maze.areAdjacent(vertex, stackVertex))
//                        {
                            stack.addFirst(vertex);
//                            break;
//                        }
//                        else
//                        {
//                            stack.remove(stackNode);
//                        }
//                    }
                }
            }
            else
            {
                stack.addFirst(vertex);
            }
        }
        System.out.println("Best traversal vertices from start to finish");
        for (Vertex vertex : stack)
        {
            System.out.println(vertex);
        }
        return stack;
    }
    
    private boolean isEndVertex(Vertex v, int[] startEndCoords)
    {
        return ((v.row == startEndCoords[2]) && (v.col == startEndCoords[3]));
    }
    
    public static final int UNVISITED = 0;
    public static final int VISITED   = 1;
    public static final int BACK      = 2;
}
