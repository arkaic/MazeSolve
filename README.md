MazeSolve
=========

A data structures assignment from a few years ago. I wrote a Java program to generate a randomized 2D maze of n x n square dimensions and then solving it.

I implemented a graph (which in turn was implemented with a priority queue) whose nodes represent the walls, and edges represent the square rooms.

I then wrote an implementation of Prim's Jarnik algorithm to calculate the minimum spanning tree of the graph, and hence creating the maze.

Lastly, I implemented depth first search to find a valid path from a startpoint to endpoint of the graph, both located at the graph's edges.

The maze's ascii representation is also printed to the console.
