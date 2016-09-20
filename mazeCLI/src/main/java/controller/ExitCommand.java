package controller;

import model.Model;
import view.View;

public class ExitCommand implements Command {

	View v;
	Model m;
	
	public ExitCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}

	public void doCommand(String[] params) {
	//TODO
	}

}
