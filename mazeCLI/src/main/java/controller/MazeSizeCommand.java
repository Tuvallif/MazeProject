package controller;

import algorithms.maze.Maze3d;
import model.Model;
import view.View;
/**
 * This method is to know the ,maze size and so the user could see it
 * @author Tuval Lifshitz
 *
 */
public class MazeSizeCommand implements Command {

	View v;
	Model m;
	String name;
	
	/**
	 * The constructor just initialize the parameters
	 * @param v view as the user chooses to use
	 * @param m Model as the user chooses to use
	 */	
	public MazeSizeCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	
	@Override
	public void doCommand() {
		//getting the maze
		Maze3d mazeToChck = m.getMazeByName(name);
		//printing on the screen
		v.printLineOnScreen(Integer.toString(mazeToChck.getSize()));
		//bringing back to null
		name = null;
	}

	@Override
	public void setParams(String[] params) {
		//getting the name
		name = params[1];
		
	}
	



}
