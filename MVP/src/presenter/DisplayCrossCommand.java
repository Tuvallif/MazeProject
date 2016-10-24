package presenter;

import algorithms.maze.Maze3d;
import model.Model;
import view.CLIGameView;
import view.View;

public class DisplayCrossCommand implements Command {

	View v;
	Model m;
	Maze3d myMaze;
	String section;
	String name;
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
			System.out.println("index = " + index);
			
			if(section.toLowerCase().equals("x")){
				toPrnt = myMaze.getCrossSectionByX(index);
			}
			else if( section.toLowerCase().equals("y")){
				toPrnt = myMaze.getCrossSectionByY(index);
			}
			else if(section.toLowerCase().equals("z")){
				toPrnt = myMaze.getCrossSectionByZ(index);
			}else{
				System.out.println("myMaze = " + myMaze + " section  =" + section + " index =" + index);
				v.printLineOnScreen("Please eneter a valid dimention(X/Y/Z) or index next time.");
				toPrnt = null;
			}

			//just making sure
			if(toPrnt != null){
				if(v.getClass().equals(CLIGameView.class)){
				v.PrintMazeCross(toPrnt);
				}
				else{
					v.PrintMazeCross(m.getMazeCrossByHeight(name, index));
				}
			}
		}
		myMaze = null;
		section  = null;
		System.out.println("printed?");
	}

	@Override
	public void setParams(String[] params) {
		name = params[3];
		myMaze = m.getMazeByName(params[3]);
		section = params[1];
		try{
			index = Integer.parseInt(params[2]);
		}catch(NumberFormatException nfe){
			index = -1;
		}
	}




}
