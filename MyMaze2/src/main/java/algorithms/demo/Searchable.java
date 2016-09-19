package algorithms.demo;

import java.util.List;

import algorithms.maze.Position;

/**
 * @author Tuval Lifshitz
 * An interface to be used as a object adapter
 *
 */
public interface Searchable {

	/**
	 * This method returns the goal position of the maze
	 * @return the goal position of the maze3D
	 */
	Position getGoalPosition();

	/**
	 * This method returns the start position of the maze
	 * @return the start position of the maze3D
	 */
	Position getStartPosition();

	/**
	 * this method returns the number of vertex in the maze3d by multiplying Height, Width and Depth.
	 * @return an int of the max size
	 */
	int getNumberOfVertexes();

	/**
	 * This method returns the possible moves for a certain position of the maze3d
	 * @param pos the Position that we check its possible moves
	 * @return a list of Positions that are the possible moves for pos in the maze3D
	 */
	List<Position> getPossibleMovesPositions(Position pos);

	/**
	 * This method adds a Vertex to the list in the implemented classes, 
	 * the vertex list a path from each Position to the end Position.
	 * @param toAdd - A position to add to the list(each Vertex has a Position)
	 * @param father - A vertex that is the previous Vertex before the one we are adding
	 */
	void addToVertexList(Position toAdd, Vertex father);

	/**
	 * This method adds a Vertex to the list in the implemented classes, 
	 * the vertex list a path from start to end
	 * @param toAdd -  A vertex to add to the list
	 */
	void addToVertexList(Vertex toAdd);

	/**
	 * This method takes the Vertex list 
	 * and turns it into a position path using the father Vertex.
	 * @return a List of Position that is the path we looked for
	 */
	List<Position> CreatePositionPathFromVertex();

	/**
	 * this method helps us find a vertex in our Vertex List
	 * @param p - a position o find in the Vertex list
	 * @return the Vertex that we looked for that has that Position.
	 */
	public Vertex findVerInPath(Position p);

	/**
	 * This method adds a Vertex to the list in the implemented classes, 
	 * the vertex list a path from each Position to the end Position.
	 * @param toAdd - A position to add to the list(each Vertex has a Position)
	 * @param father - A Position that is the previous Vertex before the one we are adding
	 * We will create or find the father in the vertex list(because it is not a vertex)
	 */
	public void addToVertexList(Position toAdd, Position father);

}
