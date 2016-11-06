package controller;

import algorithms.demo.Searchable;
import algorithms.demo.mySearchable;
import algorithms.maze.Maze3d;
import algorithms.maze.Position;
import java.util.List;
import algorithms.search.AbstractSearch;
import algorithms.search.BFS;
import algorithms.search.BestFirstSearch;
import algorithms.search.DFS;
import algorithms.search.Search;
import algorithms.search.Solution;
import model.Model;
import view.View;
/**
 * This class is to solve the maze and have a Sulotion for it
 * @author Tuval Lifshitz
 *
 */
public class SolveCommand implements Command {
	
	View v;
	Model m;
	String mazeName, srcType;
	
	/** 
	 * The constructor just initialize the parameters
	 * @param v view as the user chooses to use
	 * @param m Model as the user chooses to use
	 */
	public SolveCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	
	
	@Override
	public void doCommand() {
		//getting the solution
		m.getSlForMaze(mazeName, srcType);
	}


	@Override
	public void setParams(String[] params) {
		//getting name and search type
		mazeName = params[1];
		srcType = params[2];
	}

}