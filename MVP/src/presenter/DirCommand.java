package presenter;

import java.io.File;

import model.Model;
import view.View;
/**
 * This class is in charge of returning the data that is saved in a specific file in the computer
 * @author Tuval Lifshitz
 *
 */
public class DirCommand implements Command {
	View v;
	Model m;
	//The requseted file to use in the priniting of the content
	File fileToSend;
	
	/**
	 * The constructor just initialize the parameters
	 * @param v view as the user chooses to use
	 * @param m Model as the user chooses to use
	 */
	public DirCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	
	public void doCommand() {
		//file does not exist
		if(fileToSend != null && !fileToSend.isDirectory()){
			v.printLineOnScreen("The requested file is not a directory, please try again later with directory.");
		}
		//file exists
		else{
			v.PrintDir(fileToSend);
		}
		//Bringing g it back to previous state
		fileToSend = null;

	}
	@Override
	public void setParams(String[] params) {
		try{
			//getting the path from params[1]- the path
		fileToSend = m.getFileFromPath(params[1]);
		//no such path
		}catch(NullPointerException npe){
			v.printLineOnScreen("The path was not found, please try again later with valid path.");
			return;
		}
	}
}
