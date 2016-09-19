package algorithms.demo;

import java.util.LinkedList;
import java.util.List;

import algorithms.maze.Maze3d;
import algorithms.maze.Position;

/**
 * @author Tuval Lifshitz
 * This class implements Searcable as an object that cat be created and used.
 * It is a class that has methods to work with a searchable 
 * This class is an object adapter as it's design pattern with maze3D
 *
 */
public class mySearchable implements Searchable {

	/**
	 * a maze that a searchable holds in it position as a class adapter
	 */
	private Maze3d myMaze;
	/**
	 * a list of vertex that the class uses to create a path between the begining and goal of the maze
	 */
	private List<Vertex> myVertexes;


	/**
	 * @param maze - a maze that is received as the object of the class adapter
	 */
	public mySearchable(Maze3d maze) {
		this.myMaze = maze;
		//initializing the list
		myVertexes = new LinkedList<Vertex>();
	}

	/* (non-Javadoc)
	 * @see algorithms.demo.Searchable#getGoalPosition()
	 */
	public Position getGoalPosition() {
		return myMaze.getGoalPosition();
	}

	/* (non-Javadoc)
	 * @see algorithms.demo.Searchable#getPossibleMovesPositions(algorithms.maze.Position)
	 */
	public List<Position> getPossibleMovesPositions(Position pos) {
		return myMaze.getPossibleMovesPositions(pos);
	}

	/* (non-Javadoc)
	 * @see algorithms.demo.Searchable#getStartPosition()
	 */
	public Position getStartPosition() {
		return myMaze.getStartPosition();
	}

	/* (non-Javadoc)
	 * @see algorithms.demo.Searchable#addToVertexList(algorithms.maze.Position, algorithms.demo.Vertex)
	 */
	public void addToVertexList(Position toAdd, Vertex father) {
		myVertexes.add(new Vertex(toAdd, father));
	}

	/* (non-Javadoc)
	 * @see algorithms.demo.Searchable#CreatePositionPathFromVertex()
	 */
	public List<Position> CreatePositionPathFromVertex() {
		List<Position> toReturn = new LinkedList<Position>();
		Vertex ver = findVerInPath(myMaze.getStartPosition());
		;
		while (ver != null) {
			toReturn.add(ver.getP());
			ver = ver.getFather();
		}
		myVertexes.clear();
		return toReturn;
	}

	/* (non-Javadoc)
	 * @see algorithms.demo.Searchable#addToVertexList(algorithms.demo.Vertex)
	 */
	public void addToVertexList(Vertex toAdd) {
		myVertexes.add(toAdd);

	}

	/* (non-Javadoc)
	 * @see algorithms.demo.Searchable#findVerInPath(algorithms.maze.Position)
	 */
	public Vertex findVerInPath(Position p) {
		for (int i = 0; i < myVertexes.size(); ++i) {
			if (myVertexes.get(i).getP().equals(p)) {
				return myVertexes.get(i);
			}
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see algorithms.demo.Searchable#addToVertexList(algorithms.maze.Position, algorithms.maze.Position)
	 */
	public void addToVertexList(Position toAdd, Position father) {
		Vertex f = findVerInPath(father);
		addToVertexList(toAdd, f);

	}

	/* (non-Javadoc)
	 * @see algorithms.demo.Searchable#getSize()
	 */
	public int getNumberOfVertexes() {
		return (myMaze.getHeight() * myMaze.getDepth() * myMaze.getWidth());
	}

}
