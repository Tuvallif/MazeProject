package controller;

import model.Model;
import view.View;

/**
 * This class is to create a new maze3d 
 * @author Tuval Lifshitz
 *
 */
public class GenerateCommand implements Command, Runnable {

	Model m;
	View v;
	int height, width, depth;
	String name;

	/**
	 * The constructor just initialize the parameters
	 * @param v view as the user chooses to use
	 * @param m Model as the user chooses to use
	 */
	public GenerateCommand(View v, Model m){
		this.v = v;
		this.m = m;
		height = -1;
		depth = -1;
		width = -1;
	}

	@Override
	public void doCommand() {
		if(height > 0 && width > 0 && depth > 0){
			m.generateMaze(name,height, width, depth);
		}
		height = -1;
		depth = -1;
		width = -1;
		name = null;
	}

	@Override
	public void setParams(String[] params) {
		try{
			//getting all the information about the maze
			name = params[1];
			height = Integer.parseInt(params[2]);
			width = Integer.parseInt(params[3]);
			depth = Integer.parseInt(params[4]);
		}
		//PROBLEM IN THE PROCESS 
		catch(NumberFormatException nfe){
			v.printLineOnScreen("Please try again with Legit numbers next time.");
		}

	}

	@Override
	public void run() {
		doCommand();		
	}




}

