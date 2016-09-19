package algorithms.mazeGenerators;

import algorithms.maze.Maze3d;

/**
 * @author Tuval Lifshitz
 * This class implements the interface to create the common characteristics between the generators, like masure time.
 */
public abstract class AbstractMaze3dGenerator implements Maze3dGenerator {

//	AbstractMaze3dGenerator() {
//		
//	}

	/* (non-Javadoc)
	 * @see algorithms.mazeGenerators.Maze3dGenerator#generate()
	 */
	public abstract Maze3d generate();

	/* (non-Javadoc)
	 * @see algorithms.mazeGenerators.Maze3dGenerator#measureAlgorithmTime()
	 */
	public String measureAlgorithmTime() {
		long timeAtBegginig = System.currentTimeMillis();
		generate();
		long timeAtEnd = System.currentTimeMillis();
		long totalTime = timeAtEnd - timeAtBegginig;

		return Long.toString(totalTime);
	}

}