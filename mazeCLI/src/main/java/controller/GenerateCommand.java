package controller;

import model.Model;
import view.View;

public class GenerateCommand implements Command, Runnable {
	
	Model m;
	View v;
	int height, width, depth;
	String name;
	
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
		name = params[1];
		height = Integer.parseInt(params[2]);
		width = Integer.parseInt(params[3]);
		depth = Integer.parseInt(params[4]);
		}catch(NumberFormatException nfe){
			v.printLineOnScreen("Please try again with Legit numbers next time.");
		}
		
	}

	@Override
	public void run() {
		doCommand();		
	}


	

}

