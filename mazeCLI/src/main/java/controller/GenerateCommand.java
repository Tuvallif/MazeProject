package controller;


import model.Model;
import view.View;

public class GenerateCommand implements Command {
	
	Model m;
	View v;
	
	public GenerateCommand(View v, Model m){
		this.v = v;
		this.m = m;
	}
	
	@Override
	public void doCommand(String[] params) {
		int height, width, depth;
		try{
		height = Integer.parseInt(params[2]);
		width = Integer.parseInt(params[3]);
		depth = Integer.parseInt(params[4]);
		m.generateMaze(params[1],height, width, depth);
		}catch(NumberFormatException nfe){
			v.printLineOnScreen("Please try again with Legit numbers next time.");
		}
	}


	

}

