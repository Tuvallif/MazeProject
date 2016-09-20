package presenter;

import java.util.List;
import algorithms.demo.Searchable;
import algorithms.demo.mySearchable;
import algorithms.maze.Maze3d;
import algorithms.maze.MyPosition;
import algorithms.maze.Position;
import algorithms.search.AbstractSearch;
import algorithms.search.BFS;
import algorithms.search.BestFirstSearch;
import algorithms.search.Search;
import model.Model;
import view.View;

public class DisplaySolutionCommand implements Command {

	
	View v;
	Model m;
	List<Position> pathToWalk;
	Maze3d MyMaze;
	Searchable mysrcbl;
	Search mySrc;
	
	public DisplaySolutionCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	@Override
	public void doCommand() {
		pathToWalk = mySrc.FindPath();
		int[][][] toPrint= paintPath(MyMaze.getBoard(), pathToWalk);
		v.PrintMazeOnScreen(toPrint);
		pathToWalk = null;
		MyMaze = null ;
		mysrcbl = null;
		mySrc = null;
	}
	
	private int[][][] paintPath(int[][][] board, List<Position> toWalk){
		int[][][] toRtrn = new int[board.length][board[0].length][board[0][0].length];
		//going over all the board
		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[i].length; ++j) {
				for (int k = 0; k < board[i][j].length; ++k) {
					if(toWalk.contains(new MyPosition(i,j,k))){
						toRtrn[i][j][k] = 2;
					}else{
						toRtrn[i][j][k] = board[i][j][k];
					}					
				}
			}
			
		}

		return toRtrn;
	}
	
	@Override
	public void setParams(String[] params) {
		MyMaze = m.getMazeByName(params[1]);
		mysrcbl = new mySearchable(MyMaze);
		mySrc = new BestFirstSearch(new BFS(mysrcbl, AbstractSearch.getComperator("best")));
		
	}
}
