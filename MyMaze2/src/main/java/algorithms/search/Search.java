package algorithms.search;

import java.util.Comparator;
import algorithms.maze.Position;

/**
 * @author Tuval Lifshitz
 *This interface is a general way to search a maze foe a path.
 */
public interface Search extends Comparator<Position> {

	/**
	 * 
	 * This method finds the path according to the implemented way
	 * @return
	 */
	Solution FindPath();

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	int compare(Position e1, Position e2);

}
