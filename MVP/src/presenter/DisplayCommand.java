package presenter;

import algorithms.maze.Maze3d;
import model.Model;
import view.CLIGameView;
import view.View;

public class DisplayCommand implements Command {

	View v;
	Model m;
	Maze3d mazeToPrnt;
	String name;
	
	public DisplayCommand(View v, Model m){
		this.v = v;
		this.m = m;
	}
	
	@Override
	public void doCommand() {	
		if(mazeToPrnt != null){
			if(v.getClass().equals(CLIGameView.class)){
				v.PrintMazeOnScreen(mazeToPrnt.getBoard());	
			}
			else{
				v.PrintMazeOnScreen(m.getMazeWithAllOptions(name));
			}				
		}else{
			v.printLineOnScreen("The requested maze was not found- please try again with a correct name.");
		}
		mazeToPrnt = null;
	}

	@Override
	public void setParams(String[] params) {
		name = params[1];
		mazeToPrnt = m.getMazeByName(name);		
	}
	
	

}
