package algorithms.search;

import java.util.LinkedList;
import java.util.List;

import algorithms.maze.Position;

public class Solution {
	
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
