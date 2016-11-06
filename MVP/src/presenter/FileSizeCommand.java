package presenter;

import java.io.File;

import model.Model;
import view.View;
/**
 * This class is to see the file size and so the user could know it
 * @author Tuval Lifshitz
 *
 */
public class FileSizeCommand implements Command {

	Model m;
	View v;
	File myFile;
	
	/**
	 * The constructor just initialize the parameters
	 * @param v view as the user chooses to use
	 * @param m Model as the user chooses to use
	 */
	public FileSizeCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	@Override
	public void doCommand() {
		//there is a file
		if(myFile != null){
			//printing the file size
			v.printLineOnScreen(Long.toString(myFile.length()));
		}
		//there was a problem
		else{
			v.printLineOnScreen("There was a problem with opnening the file or reading the data.");
		}
		//bring it back to null
		myFile = null;
	}
	@Override
	public void setParams(String[] params) {
		//getting the file
		myFile = m.getFileFromPath(params[1]);
		
	}

}
