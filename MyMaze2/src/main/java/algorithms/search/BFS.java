package algorithms.search;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import algorithms.demo.Searchable;
import algorithms.demo.Vertex;
import algorithms.maze.MyPosition;
import algorithms.maze.Position;

/**
 * @author Tuval Lifshitz
 *Breadth-first search (BFS) is an algorithm for traversing or searching tree or graph data structures. 
 *It starts at the tree root 
 *(or some arbitrary node of a graph, sometimes referred to as a 'search key'[1]) 
 *and explores the neighbor nodes first, before moving to the next level neighbors.
 */
public class BFS extends AbstractSearch {



	/**
	 * this is the contractor.
	 * @param srcbrd - a searchable type to search in
	 * @param myComparator - the comparator to use, it uses different comparators in bfs and bestfs
	 */
	public BFS(Searchable srcbrd, Comparator<Position> myComparator) {
		this.srchbl = srcbrd;
		this.myComp = myComparator;
		myComp.compare(new MyPosition(1,1,1), new MyPosition(2,2,2));
		setMyPath(new Solution());
	}

	/* (non-Javadoc)
	 * @see algorithms.search.Search#FindPath()
	 */
	public Solution FindPath() {
		List<Position> result = new LinkedList<Position>();
		PriorityQueue<Position> toVisit = new PriorityQueue<Position>(myComp);
		// creating the Position from the goal position
		Position current = srchbl.getGoalPosition();
		// adding it to the list toVisit
		toVisit.add(current);
		// adding it to the vertexList
		Vertex start = new Vertex(current, null);
		srchbl.addToVertexList(start);

		while (!toVisit.isEmpty() && !current.equals(srchbl.getStartPosition())) {

			current = toVisit.poll();
			result.add(current);
			List<Position> possibleMoves = srchbl.getPossibleMovesPositions(current);
			for (Position p : possibleMoves) {
				if (!result.contains(p)) {
					toVisit.add(p);
					srchbl.addToVertexList(p, current);
				}
			}
		}
		getMyPath().setMySolution(srchbl.CreatePositionPathFromVertex());
		return getMyPath();
		// return result;
	}

}
