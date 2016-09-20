package controller;




import algorithms.maze.Maze3d;
import model.Model;
import view.View;

public class MazeSizeCommand implements Command {

	View v;
	Model m;
	
	public MazeSizeCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	

	public void doCommand(String[] params) {
		String name = params[1];
		Maze3d mazeToChck = m.getMazeByName(name);
		v.printLineOnScreen(Integer.toString(mazeToChck.getSize()));
		
	}
	



}
