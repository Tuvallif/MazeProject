package controller;

import model.Model;
import view.View;

public class ExitCommand implements Command {

	View v;
	Model m;
	/**
	 * The constructor just initialize the parameters
	 * @param v view as the user chooses to use
	 * @param m Model as the user chooses to use
	 */
	public ExitCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	
	@Override
	public void doCommand() {
		//saving the Solutions
		//m.saveSolutionToFile();
		
	}
	@Override
	public void setParams(String[] params) {
		// NO NEED
	}


}
