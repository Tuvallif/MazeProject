package presenter;

import algorithms.maze.Maze3d;
import model.Model;
import view.CLIGameView;
import view.View;
/**
 * This class is about displaying the board of the maze 
 * According to one cross in a certain index
 * @author Tuval Lifshitz
 *
 */
public class DisplayCrossCommand implements Command {

	View v;
	Model m;
	Maze3d myMaze;
	String section;
	String name;
	int index;
	
	/**
	 * The constructor just initialize the parameters
	 * @param v view as the user chooses to use
	 * @param m Model as the user chooses to use
	 */
	public DisplayCrossCommand(View v, Model m){
		this.v = v;
		this.m = m;
		myMaze = null;
		section  = null;
	}
	
	@Override
	public void doCommand(){
		//board to print in the game
		int[][] toPrnt;
		//if not initialized
		if(myMaze == null || section == null){
			v.printLineOnScreen("The requested maze was not found - please try again later with correct name.");
		}
		//there was a problem with initializing
		else if(index == -1){
			v.printLineOnScreen("could not convert your index, please try again");
		}
		//everything is OK
		else{
			//DEBUG
			//System.out.println("index = " + index);
			//checking if x/y/z
			if(section.toLowerCase().equals("x")){
				toPrnt = myMaze.getCrossSectionByX(index);
			}
			else if( section.toLowerCase().equals("y")){
				toPrnt = myMaze.getCrossSectionByY(index);
			}
			else if(section.toLowerCase().equals("z")){
				toPrnt = myMaze.getCrossSectionByZ(index);
			}
			//there is no correct cross
			else{
				System.out.println("myMaze = " + myMaze + " section  =" + section + " index =" + index);
				v.printLineOnScreen("Please eneter a valid dimention(X/Y/Z) or index next time.");
				toPrnt = null;
			}

			//just making sure
			if(toPrnt != null){
				//Determines the type of View
				if(v.getClass().equals(CLIGameView.class)){
				v.PrintMazeCross(toPrnt);
				}
				else{
					v.PrintMazeCross(m.getMazeCrossByHeight(name, index));
				}
			}
		}
		//bringing everyrhing back to null
		myMaze = null;
		section  = null;
		//DEBUG
		//System.out.println("printed?");
	}

	@Override
	public void setParams(String[] params) {
		//getting the maze name
		name = params[3];
		//getting the maze by the name
		myMaze = m.getMazeByName(params[3]);
		//getting the section
		section = params[1];
		try{
			//getting the index
			index = Integer.parseInt(params[2]);
		}catch(NumberFormatException nfe){
			//telling the doCommand that there was a problem
			index = -1;
		}
	}




}
