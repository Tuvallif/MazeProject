package controller;

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
import algorithms.search.Solution;
import model.Model;
import view.View;
/**
 * This class is in charge of displaying the Solution for the maze-the path to goal 
 * @author Tuval Lifshitz
 *
 */
public class DisplaySolutionCommand implements Command {

	
	View v;
	Model m;
	Solution pathToWalk;
	Maze3d MyMaze;
	Searchable mysrcbl;
	Search mySrc;
	/**
	 * The constructor just initialize the parameters
	 * @param v view as the user chooses to use
	 * @param m Model as the user chooses to use
	 */
	public DisplaySolutionCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	
	@Override
	public void doCommand() {
		//finding the path
		pathToWalk = mySrc.FindPath();
		//getting the board according to the path painter
		int[][][] toPrint= paintPath(MyMaze.getBoard(), pathToWalk.getMySolution());
		//sending it to be printed
		v.PrintMazeOnScreen(toPrint);
		//bringing everything back to null
		pathToWalk = null;
		MyMaze = null ;
		mysrcbl = null;
		mySrc = null;
	}
	
	/**
	 * This method returns the path to walk in
	 * @param board the board of the maze to find the path in
	 * @param toWalk the Solution of the maze3d
	 * @return a painted board with the path
	 */
	private int[][][] paintPath(int[][][] board, List<Position> toWalk){
		//creating a copy of the board
		int[][][] toRtrn = new int[board.length][board[0].length][board[0][0].length];
		//going over all the board
		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[i].length; ++j) {
				for (int k = 0; k < board[i][j].length; ++k) {
					if(toWalk.contains(new MyPosition(i,j,k))){
						//it is in the Solution
						toRtrn[i][j][k] = 2;
					}else{
						//it is not in the Solution
						toRtrn[i][j][k] = board[i][j][k];
					}					
				}
			}
			
		}

		return toRtrn;
	}

	@Override
	public void setParams(String[] params) {
		//getting the maze
		MyMaze = m.getMazeByName(params[1]);
		//creating a Searchable
		mysrcbl = new mySearchable(MyMaze);
		//creating a searcher
		mySrc = new BestFirstSearch(new BFS(mysrcbl, AbstractSearch.getComperator("best")));
		
	}
}
