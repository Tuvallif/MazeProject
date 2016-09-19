package algorithms.search;

import java.util.Comparator;
import java.util.List;

import algorithms.demo.Searchable;
import algorithms.maze.Position;

/**
 * @author Tuval Lifshitz
 *
 */
public abstract class AbstractSearch implements Search {

	/**
	 * the path that was created from the search algorithem
	 */
	List<Position> myPath;
	
	/**
	 * the searchable type to search in
	 */
	Searchable srchbl;
	
	/**
	 * a comparator to check which one is bigger
	 */
	Comparator<Position> myComp;

	/* (non-Javadoc)
	 * @see algorithms.search.Search#compare(algorithms.maze.Position, algorithms.maze.Position)
	 */
	public int compare(Position e1, Position e2) {
		return 0;
	}

	/**
	 * the general comparator to use
	 */
	static Comparator<Position> c = new Comparator<Position>() {
		public int compare(Position p1, Position p2) {
			return 0;
		}
	};
	
	/**
	 * best comparator
	 */
	static Comparator<Position> bestComp = new Comparator<Position>() {
		public int compare(Position p1, Position p2) {
			return (Math.abs(p1.getHeight() - p2.getHeight()) + Math.abs(p1.getWidth() - p2.getWidth())
					+ Math.abs(p1.getDepth() - p2.getDepth()));
		}
	};
	
	public static Comparator<Position> getComperator(String cmprtrType){
		
		if(cmprtrType.toLowerCase().equals("best")){
			return bestComp;
		}
		else{
			return c;
		}
	}

}
