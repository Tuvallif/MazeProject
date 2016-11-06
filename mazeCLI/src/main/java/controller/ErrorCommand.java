package controller;

import view.View;
/**
 * This class is n error case of command
 * @author Tuval Lifshitz
 *
 */
public class ErrorCommand implements Command {

	View v;
	String toPrint;
	/**
	 * The constructor just initialize the parameters
	 * @param v view as the user chooses to use
	 */
	public ErrorCommand(View v){
		this.v= v;
	}
	
	@Override
	public void doCommand() {
		try{
			//there was an error - printing it on the screen
		v.printLineOnScreen(toPrint);
		}catch(NullPointerException npe){
			//the message was not found
			v.printLineOnScreen("NO MESSAGE FOUND");
		}
		toPrint = null;
	}

	@Override
	public void setParams(String[] params) {
		//getting the messag
		toPrint = params[1];		
	}

}
