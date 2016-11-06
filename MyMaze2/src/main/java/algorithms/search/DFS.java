package algorithms.search;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import algorithms.demo.Searchable;
import algorithms.demo.Vertex;
import algorithms.maze.Position;

/**
 * DFS (Depth-first search)is an algorithm for traversing or searching tree or graph data structures. 
 * One starts at the root (selecting some arbitrary node as the root in the case of a graph) 
 * and explores as far as possible along each branch before backtracking.
 * @author Tuval Lifshitz
 *
 */
public class DFS extends AbstractSearch {


	/**
	 * 
	 */
	List<Vertex> vertexConections;

	/**
	 * This constructor receives a searrchable(an object to search in)
	 * it will initialize the vertex list and the Solution
	 * @param maze
	 */
	public DFS(Searchable maze) {
		this.srchbl = maze;
		vertexConections = new LinkedList<Vertex>();
		setMyPath(new Solution());
	}

	/* (non-Javadoc)
	 * @see algorithms.search.Search#FindPath()
	 */
	public Solution FindPath() {
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

		List<Position> toAdd = srchbl.CreatePositionPathFromVertex();
		getMyPath().setMySolution(toAdd);
		return getMyPath();
		// return result;
	}

}
