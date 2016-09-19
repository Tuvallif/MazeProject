package algorithms.mazeGenerators;

import java.util.Random;

import algorithms.maze.Maze3d;
import algorithms.maze.MyMaze3d;
import algorithms.maze.MyPosition;

/**
 * @author Tuval Lifshitz
 * This class extends abstractMazeGenerator, and creates a aze in a random way.
 *
 */
public class SimpleMaze3dGenerator extends AbstractMaze3dGenerator implements Maze3dGenerator {

	/* (non-Javadoc)
	 * @see algorithms.mazeGenerators.AbstractMaze3dGenerator#generate()
	 */
	@Override
	public Maze3d generate() {
		Maze3d result = new MyMaze3d(new MyPosition(0, 0, 0), new MyPosition(9, 9, 9), 10, 10, 10, false);
		Random rand = new Random(System.currentTimeMillis());
		int randomDigit;
		for (int i = 0; i < result.getHeight(); i++) {
			for (int j = 0; j < result.getWidth(); j++) {
				for (int k = 0; k < result.getDepth(); k++) {
					randomDigit = rand.nextInt();
					// making a random condition
					if (randomDigit % 4 == 1) {
						// we change the color so can go throw
						result.setWall(new MyPosition(i, j, k), true);
					}
				}
			}
		}

		return result;

	}

}
