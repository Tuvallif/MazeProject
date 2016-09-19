package controller;

import view.View;

public class ErrorCommand implements Command {

	View v;

	public ErrorCommand(View v){
		this.v= v;
	}
	@Override
	public void doCommand(String [] params) {
		v.printLineOnScreen(params[1]);
	}

}
