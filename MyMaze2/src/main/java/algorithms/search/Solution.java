package algorithms.search;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import algorithms.maze.Position;
/**
 * Solution is a class that represents the path between the start point and the goal point-
 * it is the Solution for a searchable - consists from a linkedList
 * @author Tuval Lifshitz
 *
 */
public class Solution  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Position> mySolution;
	
	/**
	 * initialize the LinkedList
	 */
	public Solution(){
		mySolution = new LinkedList<Position>();
	}

	/**
	 * return the Soultion of the maze
	 * @return the Solution for the maze
	 */
	public List<Position> getMySolution() {
		return mySolution;
	}

	/**
	 * Setting a Solution for the class
	 * @param mySolution the Solution to copy
	 */
	public void setMySolution(List<Position> mySolution) {
		this.mySolution = mySolution;
	}
	
	

}
