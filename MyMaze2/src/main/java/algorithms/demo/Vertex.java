package algorithms.demo;

import algorithms.maze.Position;

/**
 * @author Tuval Lifshitz
 * This class is a Vertex class, 
 * each object has a value Position, and a father, 
 * that is his previous Vertex.
 *
 */
public class Vertex {

	/**
	 * the Position of the Vertex
	 */
	private Position p;

	/**
	 * The vertex that is the father of the Vertex
	 */
	private Vertex father;

	/**
	 * Contractor for Vertex
	 * @param self - the position of Vertex
	 * @param father - the father of the Vertex
	 */
	public Vertex(Position self, Vertex father) {
		this.p = self;
		this.father = father;
	}

	/**
	 * @return - the position of the Vertex
	 */
	public Position getP() {
		return p;
	}

	/**
	 * @param p - sets the Position of the Vertex
	 */
	public void setP(Position p) {
		this.p = p;
	}

	/**
	 * @returns the fatehr of the Vertex
	 */
	public Vertex getFather() {
		return father;
	}

	/**
	 * @param father - sets the father of the Vertex
	 */
	public void setFather(Vertex father) {
		this.father = father;
	}

}
