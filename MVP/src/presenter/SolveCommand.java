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
import model.Model;
import view.View;

public class SolveCommand implements Command {
	
	View v;
	Model m;
	String mazeName, srcType;
	boolean valid;
	List<Position> toReturn;
	
	public SolveCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	
	
	@Override
	public void doCommand() {
		Maze3d myMaze;
		Searchable mySrchbl = null;
		Search mySrc = null;
		valid = true;
		List<Position> toReturn;
		try{
		if(mazeName != null){
			myMaze = m.getMazeByName(mazeName);
			mySrchbl = new mySearchable(myMaze);
		}
		else{
			v.printLineOnScreen("The system could not identify your maze, please try again later with a vaid maze name.");
			valid = false;
			return;
		}
		//checking the search type
		if(srcType.toLowerCase().equals("bfs") && valid == true){
			mySrc = new BestFirstSearch(new BFS(mySrchbl, AbstractSearch.getComperator("c")));
		}
		else if(srcType.toLowerCase().equals("best") && valid == true){
			mySrc = new BestFirstSearch(new BFS(mySrchbl, AbstractSearch.getComperator("best")));
		}
		else if(srcType.toLowerCase().equals("dfs") && valid == true){
			mySrc = new BestFirstSearch(new DFS(mySrchbl));
		}
		//not valid
		else{
			v.printLineOnScreen("The searchtype was not found, please try again later with BFS/DFS/BEST.");
			valid = false;
		}
		//doing the command if valid is true
		if(valid){
			toReturn = mySrc.FindPath();
			for(int i = 0; i <toReturn.size(); i++){
				v.printLineOnScreen(toReturn.get(i).toString());
			}
		}
		}catch(IndexOutOfBoundsException iobe){
			v.printOnScreen("There was an out of abounds exception, please try again.");
		}
		mazeName = null;
		srcType = null;
		toReturn = null;
		

	}


	@Override
	public void setParams(String[] params) {
		mazeName = params[1];
		srcType = params[2];
	}

}
