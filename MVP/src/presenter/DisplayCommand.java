package presenter;

import algorithms.maze.Maze3d;
import model.Model;
import view.CLIGameView;
import view.View;
/**
 * This class is about displaying the maze on the screen-it show the whole maze on the screen for the user
 * @author Tuval Lifshitz
 *
 */

public class DisplayCommand implements Command {

	View v;
	Model m;
	Maze3d mazeToPrnt;
	String name;
	
	/**
	 * The constructor just initialize the parameters
	 * @param v view as the user chooses to use
	 * @param m Model as the user chooses to use
	 */
	public DisplayCommand(View v, Model m){
		this.v = v;
		this.m = m;
	}
	
	@Override
	public void doCommand() {
		//if there is maze toPrint
		if(mazeToPrnt != null){
			//Determines the type of View
			if(v.getClass().equals(CLIGameView.class)){
				v.PrintMazeOnScreen(mazeToPrnt.getBoard());	
			}
			else{
				v.PrintMazeOnScreen(m.getMazeWithAllOptions(name));
			}
			//there is no maze toPrint
		}else{
			v.printLineOnScreen("The requested maze was not found- please try again with a correct name.");
		}
		//bringing it back to null
		mazeToPrnt = null;
	}

	@Override
	public void setParams(String[] params) {
		//getting the maze name
		name = params[1];
		//initializing the maze
		mazeToPrnt = m.getMazeByName(name);		
	}
	
	

}
