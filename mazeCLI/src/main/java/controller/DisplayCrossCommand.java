package controller;

import algorithms.maze.Maze3d;
import model.Model;
import view.View;

public class DisplayCrossCommand implements Command {

	View v;
	Model m;


	public DisplayCrossCommand(View v, Model m){
		this.v = v;
		this.m = m;
	}
	

	public void doCommand(String[] params){
		Maze3d myMaze = m.getMazeByName(params[3]);
		int[][] toPrnt;
		if(myMaze == null){
			v.printLineOnScreen("The requested maze was not found - please try again later with correct name.");
		}else{
			if(params[1] != "x" || params[1] != "X"){
				toPrnt = myMaze.getCrossSectionByX(Integer.parseInt(params[2]));
			}
			else if( params[1] != "y" ||params[1] != "Y"){
				toPrnt = myMaze.getCrossSectionByY(Integer.parseInt(params[2]));
			}
			else if(params[1] != "z" || params[1] != "Z"){
				toPrnt = myMaze.getCrossSectionByZ(Integer.parseInt(params[2]));
			}else{
				v.printLineOnScreen("Please eneter a valid dimention(X/Y/Z) or index next time.");
				toPrnt = null;
			}

			if(toPrnt != null){
				v.PrintMazeCross(toPrnt);
			}
		}
	}




}
