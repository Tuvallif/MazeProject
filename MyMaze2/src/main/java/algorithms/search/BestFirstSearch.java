package algorithms.search;

import java.util.Comparator;
import java.util.List;

import algorithms.maze.MyPosition;
import algorithms.maze.Position;

/**
 * @author Tuval Lifshitz
 *This class contains another object search that by using it it knows how to work
 *this makes our project more Generic
 */
public class BestFirstSearch extends AbstractSearch {

	/**
	 * The search for this best search.
	 * this is also for bfs and dfs
	 */
	private Search src;

	/**The constractor receives the search type and makes it his type.
	 * @param searchType - a search type to be used in the class
	 */
	public BestFirstSearch(Search searchType) {
		this.src = searchType;
		myComp = this.c;
	}

	/* (non-Javadoc)
	 * @see algorithms.search.Search#FindPath()
	 */
	public Solution FindPath() {
		return src.FindPath();
	}
	


}
