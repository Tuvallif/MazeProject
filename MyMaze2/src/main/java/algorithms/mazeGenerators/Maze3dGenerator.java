package algorithms.mazeGenerators;

import algorithms.maze.Maze3d;

/**
 * @author User
 * This interface uses us as a general way to create many kinds of generators.
 * it will create a maze3D according to the implementing class
 */
public interface Maze3dGenerator {

	/**
	 * This method measures how long does it take to create a maze3d
	 * @return the time it took in String
	 */
	public String measureAlgorithmTime();

	/**
	 * This method generates a maze3d
	 * @return a maze according t the class implementing
	 */
	public Maze3d generate();

}
