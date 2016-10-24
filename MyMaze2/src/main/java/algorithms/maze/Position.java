package algorithms.maze;

import java.io.Serializable;

/**
 * @author Tuval Lifshitz
 *
 */
public interface Position extends Comparable<Position>, Serializable {
	/**
	 * @return -the height of the Position
	 */
	int getHeight();

	/**
	 * @return - the width of the Position
	 */
	int getWidth();

	/**
	 * @return - the depth of the Position
	 */
	int getDepth();
	
	void setHeight(int height);
	void setWidth(int width);
	void setDepth(int depth);

	/**
	 * @return - a string of information about the Position values like this:
	 * Position: Height *x*, Width: *y*, Depth: *z*.
	 */
	String printPos();

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	int compareTo(Position p);
}
