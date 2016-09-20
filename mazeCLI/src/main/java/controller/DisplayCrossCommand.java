package controller;

import algorithms.maze.Maze3d;
import model.Model;
import view.View;

public class DisplayCrossCommand implements Command {

	View v;
	Model m;
	Maze3d myMaze;
	String section;
	int index;

	public DisplayCrossCommand(View v, Model m){
		this.v = v;
		this.m = m;
		myMaze = null;
		section  = null;
	}
	
	@Override
	public void doCommand(){	
		int[][] toPrnt;
		if(myMaze == null || section == null){
			v.printLineOnScreen("The requested maze was not found - please try again later with correct name.");
		}
		else if(index == -1){
			v.printLineOnScreen("could not convert your index, please try again");
		}
		else{
		
			if(section.toLowerCase() == "x"){
				toPrnt = myMaze.getCrossSectionByX(index);
			}
			else if( section.toLowerCase() == "y"){
				toPrnt = myMaze.getCrossSectionByY(index);
			}
			else if(section.toLowerCase() == "z"){
				toPrnt = myMaze.getCrossSectionByZ(index);
			}else{
				v.printLineOnScreen("Please eneter a valid dimention(X/Y/Z) or index next time.");
				toPrnt = null;
			}

			//just making sure
			if(toPrnt != null){
				v.PrintMazeCross(toPrnt);
			}
		}
		myMaze = null;
		section  = null;
	}

	@Override
	public void setParams(String[] params) {
		myMaze = m.getMazeByName(params[3]);
		section = params[1];
		try{
			index = Integer.parseInt(params[2]);
		}catch(NumberFormatException nfe){
			index = -1;
		}
	}




}
