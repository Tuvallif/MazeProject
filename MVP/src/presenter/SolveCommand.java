package presenter;

import algorithms.demo.Searchable;
import algorithms.demo.mySearchable;
import algorithms.maze.Maze3d;
import algorithms.maze.Position;

import java.util.Comparator;
import java.util.List;

import algorithms.search.AbstractSearch;
import algorithms.search.BFS;
import algorithms.search.BestFirstSearch;
import algorithms.search.DFS;
import algorithms.search.Search;
import algorithms.search.Solution;
import model.Model;
import view.View;

public class SolveCommand implements Command {
	
	View v;
	Model m;
	String mazeName, srcType;
	
	public SolveCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	
	
	@Override
	public void doCommand() {
		m.getSlForMaze(mazeName, srcType);
	}


	@Override
	public void setParams(String[] params) {
		mazeName = params[1];
		srcType = params[2];
	}

}
