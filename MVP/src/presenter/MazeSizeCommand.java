package presenter;



import algorithms.maze.Maze3d;
import model.Model;
import view.View;

public class MazeSizeCommand implements Command {

	View v;
	Model m;
	String name;
	
	public MazeSizeCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	
	@Override
	public void doCommand() {
		Maze3d mazeToChck = m.getMazeByName(name);
		v.printLineOnScreen(Integer.toString(mazeToChck.getSize()));
		name = null;
	}

	@Override
	public void setParams(String[] params) {
		name = params[1];
		
	}
	



}
