package algorithms.maze;

import java.io.Serializable;
import java.util.List;

import algorithms.exceptions.MyPositionIsWallException;

/**
 * @author Tuval Lifshitz
 *This class interface is a Maze interface that can be implemented for all kinds of mazes
 *
 */
public interface Maze3d extends Serializable{

	/**
	 * This method returns the starting position of the maze
	 * @return the point where the player begins
	 */
	Position getStartPosition();

	/**
	 * This method returns an array of Strings of possible moves for a position p in the maze
	 * @param p - a Position that is checked for its possible moves
	 * @return - an Array of Strings of the possible Positions 
	 * @throws MyPositionIsWallException - 
	 */
	String[] getPossibleMoves(Position p) throws MyPositionIsWallException;

	/**
	 * This method returns a List of Positions of possible moves for a position p in the maze
	 * @param p - a Position that is checked for its possible moves
	 * @return - a List of Positions of the possible moves
	 * @throws MyPositionIsWallException - 
	 */
	List<Position> getPossibleMovesPositions(Position p) throws MyPositionIsWallException;

	/**
	 * This method returns the goal position of the maze
	 * @return the point where the player needs to get
	 */
	Position getGoalPosition();

	/**
	 * This method is cutting one of the dimensions from the 3d maze, according to the param given.
	 * this method gives the 2d maze according to the given Height.
	 * @param x - a given height to use
	 * @return - a two dimensions array of the maze at height x
	 */
	int[][] getCrossSectionByX(int x);

	/**
	 *This method is cutting one of the dimensions from the 3d maze, according to the param given.
	 * this method gives the 2d maze according to the given Width.
	 * @param y - a given width to use
	 * @return - a two dimensions array of the maze at width y
	 */
	int[][] getCrossSectionByY(int y);

	/**
	 * This method is cutting one of the dimensions from the 3d maze, according to the param given.
	 * this method gives the 2d maze according to the given Depth.
	 * @param z- a given depth to use
	 * @return- a two dimensions array of the maze at depth z
	 */
	int[][] getCrossSectionByZ(int z);

	/**
	 * this method returns the Height of the maze
	 * @return the max height of the maze
	 */
	int getHeight();

	/**
	 *  this method returns the Width of the maze
	 * @return the max width of the maze
	 */
	int getWidth();

	/**
	 * this method returns the Depth of the maze
	 * @return the max depth of the maze
	 */
	int getDepth();

	/**
	 * This method creates a wall or a door in Position p, according to boolean wall
	 * @param p - the Position to change
	 * @param wall - true will change to wall, false will change to door
	 */
	void setWall(Position p, boolean wall);

	/**
	 * This method returns a List of neighborers Positions of  for a position p in the maze
	 * @param p - a Position that is checked for its possible moves
	 * @return - - a List of neighborers Positions of p
	 */
	List<Position> getNeighborPositions(Position p);

	/**
	 * this method returns the value of Position p in the maze
	 * @param p the Position to work with
	 * @return 1 is wall, 0 is door
	 */
	int getValueAtPosition(Position p);

	/**
	 * this method prints the maze.
	 * each level is different Height.
	 * the depth is , and the width is 
	 */
	void printMaze();
	
	/**
	 * @return
	 */
	byte[] toByteArray();
	
	/**
	 * This method returns the board of the maze
	 * @return the board that we are using by value
	 */
	int[][][] getBoard();

	int getSize();
	
	boolean cellExists(int currHeight, int currWidth, int currDepth);
	
}
