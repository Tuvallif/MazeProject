package presenter;

import java.io.File;

import model.Model;
import view.View;

public class FileSizeCommand implements Command {

	Model m;
	View v;
	File myFile;
	
	public FileSizeCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	@Override
	public void doCommand() {
		if(myFile != null){
			v.printLineOnScreen(Long.toString(myFile.length()));
		}
		else{
			v.printLineOnScreen("There was a problem with opnening the file or reading the data.");
		}
		myFile = null;
	}
	@Override
	public void setParams(String[] params) {
		myFile = m.getFileFromPath(params[1]);
		
	}

}
