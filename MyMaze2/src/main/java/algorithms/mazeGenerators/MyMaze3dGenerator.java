package algorithms.mazeGenerators;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algorithms.maze.Maze3d;
import algorithms.maze.MyMaze3d;
import algorithms.maze.MyPosition;
import algorithms.maze.Position;

/**
 * @author Tuval Lifshitz
 * This class is extending Maze3dGenerator, and creating a maze3d using search algorithm
 *
 */
public class MyMaze3dGenerator extends AbstractMaze3dGenerator implements Maze3dGenerator {
	/**
	 * 
	 */
	//static final Logger logger = LoggerFactory.getLogger(MyMaze3dGenerator.class);
	/**
	 * The next Positions to visit
	 */
	List<Position> nextPositions;
	/**
	 * the Positions that we already visited and decided what will be their case
	 */
	Set<Position> visited;
	/**
	 * a maze that will be returned
	 */
	Maze3d result;
	/**
	 * Random parameter to be used
	 */
	Random rand;
	/**
	 * param to use while debugging
	 */
	final int SIZE_OF_MAZE = 7;

	/**
	 * constructor to the class, initializes the members of the class.
	 */
	public MyMaze3dGenerator() {

		nextPositions = new LinkedList<Position>();
		//visited = new HashSet<Position>();
		rand = new Random(System.currentTimeMillis());
	}

	
	/* (non-Javadoc)
	 * @see algorithms.mazeGenerators.AbstractMaze3dGenerator#generate()
	 */
	public Maze3d generate() {
		visited = new HashSet<Position>();
		result = new MyMaze3d(new MyPosition(0, 0, 0),
				new MyPosition(SIZE_OF_MAZE - 1, SIZE_OF_MAZE - 1, SIZE_OF_MAZE - 1), SIZE_OF_MAZE, SIZE_OF_MAZE,
				SIZE_OF_MAZE, true);
		Position curPos = result.getGoalPosition();

		nextPositions.add(curPos);

		while (!nextPositions.isEmpty()) {
			curPos = nextPositions.remove(rand.nextInt(nextPositions.size()));

			//logger.debug("{}", curPos);
			visited.add(curPos);

			List<Position> neighbors = result.getNeighborPositions(curPos);
			if (isVertex(curPos)) {
				// nextPositions.add(getRandomPosition(curPos));
				result.setWall(curPos, false);
				for (Position pos : neighbors) {
					if (!visited.contains(pos) && (!isAlwaysWall(pos))) {
						nextPositions.add(pos);
					}
				}
			} else {
				int i = 0;
				for (Position pos : neighbors) {
					if (!visited.contains(pos) && (!isAlwaysWall(pos))) {
						nextPositions.add(pos);
					}
					if (visited.contains(pos) && (!isAlwaysWall(pos))) {
						i++;
					}

				}
				if (i == 2) {
					result.setWall(curPos, false);
				}
			}
			Position door = getRandomPosition(curPos);
			result.setWall(door, false);
		}
		printMaze();
		return result;
	}

	/**
	 * This method is giving a random Position of a neighbor that is not always a wall,
	 * and it will be used to generate the Maze3D
	 * @param p - The Position of we are looking for a neighbor to
	 * @return - a neighbor Position who is not always a wall.
	 */
	private Position getRandomPosition(Position p) {
		Position resultPos;
		List<Position> neighbors = result.getNeighborPositions(p);
		while (true) {
			int index = rand.nextInt(neighbors.size());
			resultPos = neighbors.get(index);
			if (!isAlwaysWall(resultPos)) {
				return resultPos;
			}
		}
	}

	/**
	 * This method prints the maze
	 */
	private void printMaze() {

		//System.out.println("PRINTING GENERATOR");
		for (int i = 0; i < this.result.getHeight(); ++i) {
			System.out.println(" Height =" + i);
			for (int j = 0; j < this.result.getWidth(); ++j) {
				for (int k = 0; k < this.result.getDepth(); ++k) {
					// System.out.print(" i ="+ i);
					// System.out.print(" j ="+ j);
					// System.out.print(" k ="+ k + "value is ");
					System.out.print(this.result.getValueAtPosition(new MyPosition(i, j, k)));
				}
				System.out.println();
			}
			System.out.println();
		}
	}



	/**
	 * This method checks if a Position is always a wall
	 * @param toCheck - Position to check
	 * @return - true if yes, false if not
	 */
	private boolean isAlwaysWall(Position toCheck) {
		int numberOfOdd = toCheck.getHeight() % 2 + toCheck.getWidth() % 2 + toCheck.getDepth() % 2;
		return numberOfOdd > 1;
	}


	/**
	 * This method checks if a Position is always a door(Vertex)
	 * @param toCheck - Position to check
	 * @return - true if yes, false if not
	 */
	private boolean isVertex(Position toCheck) {
		int numberOfOdd = toCheck.getHeight() % 2 + toCheck.getWidth() % 2 + toCheck.getDepth() % 2;
		return numberOfOdd == 0;
	}

}
