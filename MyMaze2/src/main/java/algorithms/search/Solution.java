package algorithms.search;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import algorithms.maze.Position;

public class Solution  implements Serializable{
	
	List<Position> mySolution;
	
	public Solution(){
		mySolution = new LinkedList<Position>();
	}

	public List<Position> getMySolution() {
		return mySolution;
	}

	public void setMySolution(List<Position> mySolution) {
		this.mySolution = mySolution;
	}
	
	

}
