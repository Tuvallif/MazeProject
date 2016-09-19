package algorithms.maze;

/**
 * @author Tuval Lifshitz
 *
 */
public class MyPosition implements Position {
	/**
	 * the height of the Position
	 */
	private int height;
	/**
	 * the Width of the Position
	 */
	private int width;
	/**
	 * the depth of the Position 
	 */
	private int depth;

	/**
	 * constractor for a new Position
	 * @param height - the user input for Height
	 * @param width - the user input for Width
	 * @param depth - the user input for Depth
	 */
	public MyPosition(int height, int width, int depth) {
		super();
		this.height = height;
		this.width = width;
		this.depth = depth;
	}

	/* (non-Javadoc)
	 * @see algorithms.maze.Position#getHeight()
	 */
	public int getHeight() {
		return height;
	}

	/* (non-Javadoc)
	 * @see algorithms.maze.Position#getWidth()
	 */
	public int getWidth() {
		return width;
	}

	/* (non-Javadoc)
	 * @see algorithms.maze.Position#getDepth()
	 */
	public int getDepth() {
		return depth;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Position [height=" + height + ", width=" + width + ", depth=" + depth + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + depth;
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyPosition other = (MyPosition) obj;
		if (depth != other.depth)
			return false;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see algorithms.maze.Position#printPos()
	 */
	public String printPos() {
		String S = new String("Position: Height " + this.getHeight() + ", Width: " + this.getWidth() + ", Depth: "
				+ this.getDepth() + ".");
		return S;
	}

	/* (non-Javadoc)
	 * @see algorithms.maze.Position#compareTo(algorithms.maze.Position)
	 */
	public int compareTo(Position p) {
		return ((this.getHeight() - p.getHeight()) + (this.getWidth() - p.getWidth())
				+ (this.getDepth() - p.getDepth()));
	}

}
