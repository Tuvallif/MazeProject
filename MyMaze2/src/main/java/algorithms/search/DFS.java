package algorithms.search;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import algorithms.demo.Searchable;
import algorithms.demo.Vertex;
import algorithms.maze.Position;

/**
 * @author Tuval Lifshitz
 *
 */
public class DFS extends AbstractSearch {


	/**
	 * 
	 */
	List<Vertex> vertexConections;

	/**
	 * @param maze
	 */
	public DFS(Searchable maze) {
		this.srchbl = maze;
		vertexConections = new LinkedList<Vertex>();
	}

	/* (non-Javadoc)
	 * @see algorithms.search.Search#FindPath()
	 */
	public List<Position> FindPath() {
		List<Position> result = new LinkedList<Position>();
		Stack<Position> toVisit = new Stack<Position>();
		// creating the Position from the goal position
		Position current = srchbl.getGoalPosition();
		// adding it to the list toVisit
		toVisit.push(current);
		// adding it to the vertexList
		Vertex start = new Vertex(current, null);
		srchbl.addToVertexList(start);

		while (!toVisit.isEmpty() && !current.equals(srchbl.getStartPosition())) {
			current = toVisit.pop();
			result.add(current);
			List<Position> possibleMoves = srchbl.getPossibleMovesPositions(current);
			for (Position p : possibleMoves) {
				if (!result.contains(p)) {
					toVisit.push(p);
					srchbl.addToVertexList(p, current);
				}
			}
		}

		myPath = srchbl.CreatePositionPathFromVertex();
		return myPath;
		// return result;
	}

}
