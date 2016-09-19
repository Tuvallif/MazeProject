package model;

import java.io.File;
import algorithms.maze.Maze3d;

public interface Model {

	 Maze3d getMazeByName(String name);
	 
	 void generateMaze(String name, int height, int width, int depth);
	 
	 int[][] getCrossOfMaze(String name, char XYZ, int index);
	 
	 void SaveMazeToFile(String mazeName, String fileName);
	 
	 File getFileFromPath(String path);

	void generateMaze(String name, byte[] mazeInByte);

}
