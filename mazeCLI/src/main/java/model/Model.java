package model;

import java.io.File;
import algorithms.maze.Maze3d;
import algorithms.search.Search;
import algorithms.search.Solution;
/**
 * This Class is a part of the presenter design pattern
 * it "does the work" in the program
 * @author Tuval Lifshitz
 *
 */
public interface Model {
	/**
	 * This method returns the maze saves in the Model according to a given String
	 * The saving of the mazes is in the implementation 
	 * @param name String given to the method to find the maze3d
	 * @return a maze3d according to the name, Or if the name does not exist returns null
	 */
	 Maze3d getMazeByName(String name);
		/**
		 * This method generates a new maze according to the given parameters
		 * @param name the name of the maze to be saves in the implementation of Model
		 * @param height the height of the Maze3d
		 * @param width the width of the Maze3d
		 * @param depth the depth of the Maze3d
		 */	 
	 void generateMaze(String name, int height, int width, int depth);
		/**
		 * This Method returns one layer of the Maze3d according to the given parameters
		 * @param name The name of the maze that wants to be used
		 * @param XYZ Char that is X or Y or Z - 
		 * x-Height
		 * y- width
		 * z-depth
		 * 
		 *     D  E  P  T  H
		 *    W
		 *    I
		 *    D
		 *    T
		 *    H
		 *    
		 * @param index the index of X/Y/Z 
		 * @return int[][] that is the layer of the board of the maze
		 */	 
	 int[][] getCrossOfMaze(String name, char XYZ, int index);
		/**
		 * This method saves the requested maze3d into a file in a byte way, so can be opened later
		 * @param mazeName String that is the name of the maze to be saved in the file
		 * @param fileName String that is the name of the file to save the maze in
		 */
	 void SaveMazeToFile(String mazeName, String fileName);
		/**
		 * This method returns a file according to a given path, including the files name
		 * @param path String that is the path of the file in the PC
		 * @return a file that is in the path, if not existing returns null
		 */
	 File getFileFromPath(String path);
		/**
		 * This method generates a new maze according to the given parameters
		 * @param name the name of the maze to be saves in the implementation of Model
		 * @param mazeInByte an array of bytes to be used as the data of the maze, the way it is opened is explained in the Maze class
		 */
	void generateMaze(String name, byte[] mazeInByte);
	/**
	 * This method calculates the solution for a given maze
	 * @param myMaze String that is the name of the maze
	 * @param mySrc a String that represents the way of search bfs/dfs/best
	 * @return a Solution for the maze from the start to goal, if not found returns null
	 */
	Solution getSlForMaze(String myMaze, String mySrc);

}
